package hu.isakots.martosgym.rest;

import hu.isakots.martosgym.exception.EmailAlreadyExistsException;
import hu.isakots.martosgym.rest.dto.JwtResponse;
import hu.isakots.martosgym.rest.dto.LoginVM;
import hu.isakots.martosgym.rest.dto.SignUpForm;
import hu.isakots.martosgym.service.AuthService;
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
    public ResponseEntity<JwtResponse> authorize(@Valid @RequestBody LoginVM loginVM) {
        String token = authService.authorize(loginVM);
        return new ResponseEntity<>(new JwtResponse(token), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<Object> registerUser(@RequestBody SignUpForm form) throws EmailAlreadyExistsException {
        authService.registerUser(form);
        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }
}
