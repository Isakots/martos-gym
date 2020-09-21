package hu.isakots.martosgym.service;

import hu.isakots.martosgym.configuration.security.TokenProvider;
import hu.isakots.martosgym.domain.Authority;
import hu.isakots.martosgym.domain.User;
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
import static hu.isakots.martosgym.service.util.ApplicationUtil.mapSubscriptions;

@Service
public class AuthService {
    private final AccountService accountService;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final ModelMapper modelMapper;
    private final MailService mailService;

    public AuthService(AccountService accountService, PasswordEncoder passwordEncoder, TokenProvider tokenProvider,
                       AuthenticationManagerBuilder authenticationManagerBuilder, ModelMapper modelMapper, MailService mailService) {
        this.accountService = accountService;
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
        return new LoginResponse(tokenProvider.createToken(authentication));
    }

    public void registerUser(SignUpForm form) {
        User user = modelMapper.map(form, User.class);
        user.setPassword(passwordEncoder.encode(form.getPassword()));

        Authority authority = new Authority();
        authority.setName(ROLE_USER);
        user.getAuthorities().add(authority);

        mapSubscriptions(form.getSubscriptions(), user);

        accountService.saveAccount(user);
        mailService.sendRegistrationEmail(user);
    }

    public UserWithRoles getIdentity() {
        User user = accountService.getAuthenticatedUserWithData();
        return modelMapper.map(user, UserWithRoles.class);
    }
}
