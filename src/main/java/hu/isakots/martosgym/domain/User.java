package hu.isakots.martosgym.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;


@Entity
@Table(name = "GYM_USER")
public class User implements Serializable {

    @Id
    @Column(name = "USER_ID")
    @GeneratedValue(generator = "SEQ_GYM_USER")
    @SequenceGenerator(name = "SEQ_GYM_USER", sequenceName = "SEQ_GYM_USER", allocationSize = 1)
    private Long id;

    @NotNull
    @Size(max = 64)
    @Column(name = "EMAIL", length = 254, unique = true, nullable = false)
    private String email;

    @JsonIgnore
    @NotNull
    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @Size(max = 50)
    @Column(name = "FIRSTNAME")
    private String firstName;

    @Size(max = 50)
    @Column(name = "LASTNAME")
    private String lastName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return id.equals(user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
