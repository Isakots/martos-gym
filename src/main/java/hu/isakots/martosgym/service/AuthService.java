package hu.isakots.martosgym.service;

import hu.isakots.martosgym.configuration.security.TokenProvider;
import hu.isakots.martosgym.domain.User;
import hu.isakots.martosgym.exception.DatabaseException;
import hu.isakots.martosgym.repository.AuthorityRepository;
import hu.isakots.martosgym.repository.UserRepository;
import hu.isakots.martosgym.rest.auth.model.LoginResponse;
import hu.isakots.martosgym.rest.auth.model.LoginVM;
import hu.isakots.martosgym.rest.auth.model.SignUpForm;
import hu.isakots.martosgym.rest.auth.model.UserWithRoles;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static hu.isakots.martosgym.configuration.util.AuthoritiesConstants.ROLE_USER;

@Service
public class AuthService {
    private final AccountService accountService;
    private final AuthorityRepository authorityRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final ModelMapper modelMapper;
    private final MailService mailService;

    public AuthService(AccountService accountService, AuthorityRepository authorityRepository,
                       PasswordEncoder passwordEncoder, TokenProvider tokenProvider,
                       AuthenticationManagerBuilder authenticationManagerBuilder, ModelMapper modelMapper, MailService mailService) {
        this.accountService = accountService;
        this.authorityRepository = authorityRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.modelMapper = modelMapper;
        this.mailService = mailService;
    }

    public LoginResponse authorize(LoginVM loginVM) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginVM.getUsername(), loginVM.getPassword());
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserWithRoles userWithRoles = modelMapper.map(accountService.getAuthenticatedUserWithData(), UserWithRoles.class);
        return new LoginResponse(tokenProvider.createToken(authentication), userWithRoles);
    }

    public void registerUser(SignUpForm form) throws DatabaseException {
        User user = modelMapper.map(form, User.class);
        user.setPassword(passwordEncoder.encode(form.getPassword()));
        user.getAuthorities().add(authorityRepository.findById(ROLE_USER).get());
        try {
            accountService.saveAccount(user);
        } catch (Exception e) {
            throw new DatabaseException("Exception occured while persisting User during registration", e);
        }
        mailService.sendRegistrationEmail(user);
    }
}
