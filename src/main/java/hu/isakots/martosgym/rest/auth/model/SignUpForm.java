package hu.isakots.martosgym.rest.auth.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class SignUpForm implements Serializable {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private boolean studentStatus;
    private String institution;
    private String faculty;
    private boolean isCollegian;
    private int roomNumber;
    private Set<String> subscriptions = new HashSet<>();

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

    public boolean isStudentStatus() {
        return studentStatus;
    }

    public void setStudentStatus(boolean studentStatus) {
        this.studentStatus = studentStatus;
    }

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public boolean isCollegian() {
        return isCollegian;
    }

    public void setCollegian(boolean collegian) {
        isCollegian = collegian;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public Set<String> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(Set<String> subscriptions) {
        this.subscriptions = subscriptions;
    }
}