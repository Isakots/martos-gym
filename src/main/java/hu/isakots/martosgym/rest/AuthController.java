package hu.isakots.martosgym.rest;

import hu.isakots.martosgym.configuration.security.JWTFilter;
import hu.isakots.martosgym.rest.dto.LoginVM;
import hu.isakots.martosgym.rest.dto.SignUpForm;
import hu.isakots.martosgym.service.AuthService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static hu.isakots.martosgym.rest.util.EndpointConstants.API_CONTEXT;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = API_CONTEXT)
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/auth")
    public ResponseEntity authorize(@Valid @RequestBody LoginVM loginVM) {
        String jwt = authService.authorize(loginVM);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JWTFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
        return new ResponseEntity<>(null, httpHeaders, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody SignUpForm form) {
        authService.registerUser(form);
        return new ResponseEntity<>("success", HttpStatus.CREATED);
    }
}
