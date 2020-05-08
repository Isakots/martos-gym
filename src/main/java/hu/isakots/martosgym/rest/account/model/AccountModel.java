package hu.isakots.martosgym.rest.account.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class AccountModel implements Serializable {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private boolean studentStatus;
    private String institution;
    private String faculty;
    private boolean isCollegian;
    private int roomNumber;
    private Set<String> subscriptions = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    @Override
    public String toString() {
        return "AccountModel{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", studentStatus=" + studentStatus +
                ", institution='" + institution + '\'' +
                ", faculty='" + faculty + '\'' +
                ", isCollegian=" + isCollegian +
                ", roomNumber=" + roomNumber +
                ", subscriptions=" + subscriptions +
                '}';
    }
}
