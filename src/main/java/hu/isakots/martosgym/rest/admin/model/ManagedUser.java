package hu.isakots.martosgym.rest.admin.model;

import java.io.Serializable;
import java.util.Set;

public class ManagedUser implements Serializable {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private boolean hasTicketForActivePeriod;
    private Set<String> authorities;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isHasTicketForActivePeriod() {
        return hasTicketForActivePeriod;
    }

    public void setHasTicketForActivePeriod(boolean hasTicketForActivePeriod) {
        this.hasTicketForActivePeriod = hasTicketForActivePeriod;
    }

    public Set<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<String> authorities) {
        this.authorities = authorities;
    }

    @Override
    public String toString() {
        return "ManagedUser{" +
                "id='" + id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", hasTicketForActivePeriod=" + hasTicketForActivePeriod +
                ", authorities=" + authorities +
                '}';
    }
}
