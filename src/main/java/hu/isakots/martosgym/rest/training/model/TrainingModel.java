package hu.isakots.martosgym.rest.training.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class TrainingModel implements Serializable {
    private Long id;
    private String name;
    private int maxParticipants;
    private int actualParticipants;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private boolean isSubscribed;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMaxParticipants() {
        return maxParticipants;
    }

    public void setMaxParticipants(int maxParticipants) {
        this.maxParticipants = maxParticipants;
    }

    public int getActualParticipants() {
        return actualParticipants;
    }

    public void setActualParticipants(int actualParticipants) {
        this.actualParticipants = actualParticipants;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public boolean isSubscribed() {
        return isSubscribed;
    }

    public void setSubscribed(boolean subscribed) {
        isSubscribed = subscribed;
    }
}
