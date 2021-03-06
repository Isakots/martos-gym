package hu.isakots.martosgym.configuration.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "martosgym")
public class MartosGymProperties {
    private long maxReservationDays;
    private long emailNotificationBeforeTrainingInDays;
    private String contextUrl;
    private Long showArticlesThresholdInMonths;

    public long getMaxReservationDays() {
        return maxReservationDays;
    }

    public void setMaxReservationDays(long maxReservationDays) {
        this.maxReservationDays = maxReservationDays;
    }

    public long getEmailNotificationBeforeTrainingInDays() {
        return emailNotificationBeforeTrainingInDays;
    }

    public void setEmailNotificationBeforeTrainingInDays(long emailNotificationBeforeTrainingInDays) {
        this.emailNotificationBeforeTrainingInDays = emailNotificationBeforeTrainingInDays;
    }

    public String getContextUrl() {
        return contextUrl;
    }

    public void setContextUrl(String contextUrl) {
        this.contextUrl = contextUrl;
    }

    public Long getShowArticlesThresholdInMonths() {
        return showArticlesThresholdInMonths;
    }

    public void setShowArticlesThresholdInMonths(Long showArticlesThresholdInMonths) {
        this.showArticlesThresholdInMonths = showArticlesThresholdInMonths;
    }
}

