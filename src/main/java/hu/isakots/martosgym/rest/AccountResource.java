package hu.isakots.martosgym.rest;

import hu.isakots.martosgym.configuration.util.SecurityUtils;
import hu.isakots.martosgym.domain.User;
import hu.isakots.martosgym.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static hu.isakots.martosgym.rest.util.EndpointConstants.API_CONTEXT;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = API_CONTEXT)
@PreAuthorize("hasRole('ROLE_USER')")
public class AccountResource {

    private final UserRepository userRepository;

    public AccountResource(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/profile")
    public ResponseEntity<User> getUserInformation() {
        Optional<User> user = userRepository.findOneWithAuthoritiesByEmailIgnoreCase(
                SecurityUtils.getCurrentUserLogin().orElseThrow(()->new UsernameNotFoundException("Not authenticated"))
        );
        return user.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }
}
