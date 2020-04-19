package hu.isakots.martosgym.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.*;


@Entity
@Table(name = "GYM_USER")
public class User implements Serializable {

    @Id
    @Column(name = "USER_ID")
    // FYI: Sequence cannot be created in MySQL.. TODO add UUID generator using Hibernate
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Column(name = "STUDENT_STATUS")
    private boolean studentStatus;

    @Column(name = "INSTITUTION", length = 5)
    private String institution;

    @Column(name = "FACULTY", length = 5)
    private String faculty;

    @Column(name = "IS_COLLEGIAN")
    private boolean isCollegian;

    @Column(name = "ROOM_NUMBER", length = 5)
    private int roomNumber;

    @Column(name = "IMAGE_PATH", length = 127)
    private String imagePath;

    @ManyToMany
    @JoinTable(name = "USER_AUTHORITY",
            joinColumns = {@JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID")},
            inverseJoinColumns = {@JoinColumn(name = "AUTHORITY_NAME", referencedColumnName = "NAME")})
    private Set<Authority> authorities = new HashSet<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Reservation> reservations = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "TICKETS",
            joinColumns = {@JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID")},
            inverseJoinColumns = {@JoinColumn(name = "PERIOD_ID", referencedColumnName = "PERIOD_ID")})
    private List<GymPeriod> tickets = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "TRAINING_PARTICIPANTS",
            joinColumns = {@JoinColumn(name = "PARTICIPANT_ID", referencedColumnName = "USER_ID")},
            inverseJoinColumns = {@JoinColumn(name = "TRAINING_ID", referencedColumnName = "TRAINING_ID")})
    @JsonIgnore
    private Set<Training> trainings = new HashSet<>();


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

    public Set<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    public List<GymPeriod> getTickets() {
        return tickets;
    }

    public void setTickets(List<GymPeriod> tickets) {
        this.tickets = tickets;
    }

    public Set<Training> getTrainings() {
        return trainings;
    }

    public void setTrainings(Set<Training> trainings) {
        this.trainings = trainings;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", studentStatus=" + studentStatus +
                ", institution='" + institution + '\'' +
                ", faculty='" + faculty + '\'' +
                ", isCollegian=" + isCollegian +
                ", roomNumber=" + roomNumber +
                ", imagePath='" + imagePath + '\'' +
                ", authorities=" + authorities +
                ", reservations=" + reservations +
                ", tickets=" + tickets +
                ", trainings=" + trainings +
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
