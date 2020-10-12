package hu.isakots.martosgym.rest.training.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

public class TrainingModel implements Serializable {
    private String id;
    private String name;
    private int maxParticipants;
    private int actualParticipants;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private boolean isSubscribed;
    private List<String> participantNames;

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public List<String> getParticipantNames() {
        return participantNames;
    }

    public void setParticipantNames(List<String> participantNames) {
        this.participantNames = participantNames;
    }

    @Override
    public String toString() {
        return "TrainingModel{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", maxParticipants=" + maxParticipants +
                ", actualParticipants=" + actualParticipants +
                ", description='" + description + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", isSubscribed=" + isSubscribed +
                ", participantNames=" + participantNames +
                '}';
    }
}
