package hu.isakots.martosgym.service;

import hu.isakots.martosgym.domain.Subscription;
import hu.isakots.martosgym.domain.Training;
import hu.isakots.martosgym.service.model.SubscriptionType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class EmailNotificationSchedulerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(EmailNotificationSchedulerService.class.getName());

    private final TrainingService trainingService;
    private final MailService mailService;

    public EmailNotificationSchedulerService(TrainingService trainingService, MailService mailService) {
        this.trainingService = trainingService;
        this.mailService = mailService;
    }

    @Scheduled(cron = "${scheduler.cron}")
    public void scheduledEmailNotificationsForTrainings() {
        LOGGER.debug("Starting scheduled email notification sending for trainings..");

        List<Training> trainingList = trainingService.findAllByStartDateIsBefore(LocalDateTime.now().plus(2L, ChronoUnit.DAYS));
        LOGGER.debug("Count of found trainings before due date: {}", trainingList.size());

        trainingList.forEach(training -> {
            LOGGER.debug("Looking for participants who subscribed for notification with trainingId: {}", training.getId());
            training.getParticipants().forEach(participant -> {
                if(participant.getSubscriptions().contains(new Subscription(SubscriptionType.ON_SUBSCRIBED_TRAININGS))) {
                    mailService.sendNotificationEmailOnSubscribedTraining(participant, training);
                }
            });
        });

        LOGGER.debug("Finishing scheduled email notification sending for trainings..");
    }

}
