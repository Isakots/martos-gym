package hu.isakots.martosgym.domain;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "RESERVATION")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RESERVATION_ID")
    private Long id;

    @NotNull
    @Column(name = "SUBJECT_NAME", length = 63, updatable = false, nullable = false)
    private String subjectName;

    @NotNull
    @Column(name = "START_DATE", updatable = false, nullable = false)
    private LocalDateTime startDate;

    @NotNull
    @Column(name = "END_DATE", updatable = false, nullable = false)
    private LocalDateTime endDate;

    @Column(name = "IS_RETURNED")
    private boolean isReturned;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public boolean isReturned() {
        return isReturned;
    }

    public void setReturned(boolean returned) {
        isReturned = returned;
    }
}
