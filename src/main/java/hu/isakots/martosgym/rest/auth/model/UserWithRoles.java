package hu.isakots.martosgym.rest.auth.model;

import java.util.Set;

public class UserWithRoles {
    private String username;
    private Set<String> authorities;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Set<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<String> authorities) {
        this.authorities = authorities;
    }
}
