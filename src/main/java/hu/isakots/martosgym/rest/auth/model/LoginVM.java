package hu.isakots.martosgym.rest.auth.model;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * View Model object for storing a user's credentials.
 */
public class LoginVM implements Serializable {

    @NotNull
    private String username;

    @NotNull
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}