package hu.isakots.martosgym.service;

import hu.isakots.martosgym.configuration.properties.MartosGymProperties;
import hu.isakots.martosgym.domain.Subscription;
import hu.isakots.martosgym.domain.Training;
import hu.isakots.martosgym.domain.User;
import hu.isakots.martosgym.service.model.SubscriptionType;
import org.assertj.core.util.Sets;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class EmailNotificationSchedulerServiceTest {
    private static final Long MINUS_DAYS = 2L;

    @Mock
    private TrainingService trainingService;

    @Mock
    private MailService mailService;

    @Mock
    private MartosGymProperties martosGymProperties;

    @InjectMocks
    private EmailNotificationSchedulerService schedulerService;

    @Test
    public void scheduledEmailNotificationsForTrainings_whenNoTrainingsFound_emailIsNotSent() {
        when(martosGymProperties.getEmailNotificationBeforeTrainingInDays()).thenReturn(MINUS_DAYS);
        when(trainingService.findAllByStartDateIsBefore(any())).thenReturn(Collections.emptyList());

        schedulerService.scheduledEmailNotificationsForTrainings();

        verifyNoInteractions(mailService);
    }

    @Test
    public void scheduledEmailNotificationsForTrainings_whenParticipantHasNoSubscription_emailIsNotSent() {
        when(martosGymProperties.getEmailNotificationBeforeTrainingInDays()).thenReturn(MINUS_DAYS);
        Training mockTraining = new Training();
        User mockUser = new User();
        mockUser.setId(UUID.randomUUID().toString());
        mockTraining.setParticipants(Sets.newHashSet(Collections.singletonList(mockUser)));
        when(trainingService.findAllByStartDateIsBefore(any())).thenReturn(Collections.singletonList(mockTraining));

        schedulerService.scheduledEmailNotificationsForTrainings();

        verifyNoInteractions(mailService);
    }

    @Test
    public void scheduledEmailNotificationsForTrainings_whenparticipantHasSubscription_emailIsSent() {
        when(martosGymProperties.getEmailNotificationBeforeTrainingInDays()).thenReturn(MINUS_DAYS);
        Training mockTraining = new Training();
        User mockUser = new User();
        mockUser.setId(UUID.randomUUID().toString());
        mockUser.setSubscriptions(Sets.newHashSet(Collections.singletonList(new Subscription(SubscriptionType.ON_SUBSCRIBED_TRAININGS))));
        mockTraining.setParticipants(Sets.newHashSet(Collections.singletonList(mockUser)));
        when(trainingService.findAllByStartDateIsBefore(any())).thenReturn(Collections.singletonList(mockTraining));

        schedulerService.scheduledEmailNotificationsForTrainings();

        verify(mailService).sendNotificationEmailOnSubscribedTraining(eq(mockUser), eq(mockTraining));
    }

}