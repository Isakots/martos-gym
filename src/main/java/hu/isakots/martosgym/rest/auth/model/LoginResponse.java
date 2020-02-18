package hu.isakots.martosgym.rest.auth.model;

public class LoginResponse {
    private final String token;
    private final UserWithRoles userWithRoles;

    public LoginResponse(String token, UserWithRoles userWithRoles) {
        this.token = token;
        this.userWithRoles = userWithRoles;
    }

    public String getToken() {
        return token;
    }

    public UserWithRoles getUserWithRoles() {
        return userWithRoles;
    }
}
