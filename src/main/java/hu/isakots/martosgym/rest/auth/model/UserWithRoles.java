package hu.isakots.martosgym.rest.auth.model;

import hu.isakots.martosgym.domain.Authority;

import java.util.Set;

public class UserWithRoles {
    private String username;
    private Set<Authority> authorities;

    public String getUsername() {
        return username;
    }

    public Set<Authority> getAuthorities() {
        return authorities;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }
}
