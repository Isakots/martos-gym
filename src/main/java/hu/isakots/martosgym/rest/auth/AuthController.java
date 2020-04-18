package hu.isakots.martosgym.rest.auth;

import hu.isakots.martosgym.rest.auth.model.LoginResponse;
import hu.isakots.martosgym.rest.auth.model.LoginVM;
import hu.isakots.martosgym.rest.auth.model.SignUpForm;
import hu.isakots.martosgym.rest.auth.model.UserWithRoles;
import hu.isakots.martosgym.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static hu.isakots.martosgym.rest.util.EndpointConstants.API_CONTEXT;

@RestController
@RequestMapping(value = API_CONTEXT)
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/identity")
    public ResponseEntity<UserWithRoles> getIdentity() {
        UserWithRoles userWithRoles = authService.getIdentity();
        return new ResponseEntity<>(userWithRoles, HttpStatus.OK);
    }

    @PostMapping("/auth")
    public ResponseEntity<LoginResponse> authorize(@Valid @RequestBody LoginVM loginVM) {
        LoginResponse loginResponse = authService.authorize(loginVM);
        return new ResponseEntity<>(loginResponse, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<Object> registerUser(@RequestBody SignUpForm form) {
        authService.registerUser(form);
        return ResponseEntity.ok().build();
    }
}
