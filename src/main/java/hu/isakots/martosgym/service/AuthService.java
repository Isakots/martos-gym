package hu.isakots.martosgym.service;

import hu.isakots.martosgym.configuration.security.TokenProvider;
import hu.isakots.martosgym.domain.User;
import hu.isakots.martosgym.repository.AuthorityRepository;
import hu.isakots.martosgym.repository.UserRepository;
import hu.isakots.martosgym.rest.dto.LoginVM;
import hu.isakots.martosgym.rest.dto.SignUpForm;
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

    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final ModelMapper modelMapper;

    public AuthService(UserRepository userRepository, AuthorityRepository authorityRepository,
                       PasswordEncoder passwordEncoder, TokenProvider tokenProvider,
                       AuthenticationManagerBuilder authenticationManagerBuilder, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.modelMapper = modelMapper;
    }

    public String authorize(LoginVM loginVM) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginVM.getUsername(), loginVM.getPassword());
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return tokenProvider.createToken(authentication);
    }

    public void registerUser(SignUpForm form) {
        User user = modelMapper.map(form, User.class);
        user.setPassword(passwordEncoder.encode(form.getPassword()));
        user.getAuthorities().add(authorityRepository.findById(ROLE_USER).get());
        userRepository.save(user);
    }
}
