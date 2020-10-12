package hu.isakots.martosgym.service;

import hu.isakots.martosgym.configuration.ModelMapperConfiguration;
import hu.isakots.martosgym.domain.Training;
import hu.isakots.martosgym.domain.User;
import hu.isakots.martosgym.exception.ResourceNotFoundException;
import hu.isakots.martosgym.exception.TrainingValidationException;
import hu.isakots.martosgym.repository.TrainingRepository;
import hu.isakots.martosgym.rest.training.model.TrainingModel;
import org.assertj.core.util.Sets;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class TrainingServiceTest {

    private static final String MOCK_ID = UUID.randomUUID().toString();
    private static final String MOCK_USER_ID = UUID.randomUUID().toString();

    @Spy
    private ModelMapper modelMapper = new ModelMapperConfiguration().getModelMapper();

    @Mock
    private AccountService accountService;

    @Mock
    private MailService mailService;

    @Mock
    private TrainingRepository repository;

    @InjectMocks
    private TrainingService service;

    @Test
    public void findAll() {
        Training training = new Training();
        training.setId(MOCK_ID);
        final String trainingName = "trainingName";
        training.setName(trainingName);
        final String description = "description";
        training.setDescription(description);
        final LocalDateTime startDate = LocalDateTime.now();
        training.setStartDate(startDate);
        final LocalDateTime endDate = LocalDateTime.now().plus(2L, ChronoUnit.HOURS);
        training.setEndDate(endDate);
        final int maxParticipants = 20;
        training.setMaxParticipants(maxParticipants);

        User mockUser = new User();
        mockUser.setId(MOCK_USER_ID);
        mockUser.setTrainings(Sets.newHashSet(Collections.singletonList(training)));
        training.setParticipants(Sets.newHashSet(Collections.singletonList(mockUser)));

        when(repository.findAll()).thenReturn(Collections.singletonList(training));
        when(accountService.getAuthenticatedUserWithData()).thenReturn(mockUser);

        List<TrainingModel> result = service.findAll();

        assertEquals(1, result.size());
        TrainingModel model = result.get(0);
        assertEquals(MOCK_ID, model.getId());
        assertEquals(trainingName, model.getName());
        assertEquals(description, model.getDescription());
        assertEquals(startDate, model.getStartDate());
        assertEquals(endDate, model.getEndDate());
        assertEquals(maxParticipants, model.getMaxParticipants());
        assertEquals(1, model.getActualParticipants());
        assertTrue(model.isSubscribed());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void findById_whenNotFound() throws ResourceNotFoundException {
        when(repository.findById(any())).thenReturn(Optional.empty());
        service.findById(MOCK_ID);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void findTrainingById_whenNotFound() throws ResourceNotFoundException {
        when(repository.findById(any())).thenReturn(Optional.empty());
        service.findTrainingById(MOCK_ID);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createTraining_whenModelHasId_thenIllegalArgumentExceptionIsThrown() throws TrainingValidationException {
        TrainingModel model = new TrainingModel();
        model.setId(MOCK_ID);
        service.createTraining(model);
    }

    @Test(expected = TrainingValidationException.class)
    public void createTraining_whenStartDateIsBeforeNow_thenTrainingValidationExceptionIsThrown() throws TrainingValidationException {
        TrainingModel model = new TrainingModel();
        model.setStartDate(LocalDateTime.now().minus(1L, ChronoUnit.HOURS));
        service.createTraining(model);
    }

    @Test(expected = TrainingValidationException.class)
    public void createTraining_whenEndDateIsBeforeStartDate_thenTrainingValidationExceptionIsThrown() throws TrainingValidationException {
        TrainingModel model = new TrainingModel();
        model.setStartDate(LocalDateTime.now().plus(1L, ChronoUnit.DAYS).plus(1L, ChronoUnit.HOURS));
        model.setEndDate(LocalDateTime.now().plus(1L, ChronoUnit.DAYS));
        service.createTraining(model);
    }

    @Test
    public void createTraining_whenModelIsValid_thenTrainingIsPersisted() throws TrainingValidationException {
        TrainingModel model = new TrainingModel();
        model.setStartDate(LocalDateTime.now().plus(1L, ChronoUnit.DAYS));
        model.setEndDate(LocalDateTime.now().plus(1L, ChronoUnit.DAYS).plus(1L, ChronoUnit.HOURS));
        when(accountService.getAuthenticatedUserWithData()).thenReturn(new User());
        when(repository.save(any())).thenReturn(new Training());
        ArgumentCaptor<Training> trainingArgumentCaptor = ArgumentCaptor.forClass(Training.class);

        service.createTraining(model);

        verify(repository).save(trainingArgumentCaptor.capture());
        assertEquals(1, trainingArgumentCaptor.getValue().getParticipants().size());
        verify(mailService).startEmailNotificationAsyncTaskOnNewTraining();
    }

    @Test(expected = IllegalArgumentException.class)
    public void modifyTraining_whenModelDoesNotHaveId_thenIllegalArgumentExceptionIsThrown() throws ResourceNotFoundException, TrainingValidationException {
        TrainingModel model = new TrainingModel();
        service.modifyTraining(model);
    }

    @Test(expected = TrainingValidationException.class)
    public void modifyTraining_whenMaxParticipantsIsSetBelowActualParticipants_thenTrainingValidationExceptionIsThrown() throws ResourceNotFoundException, TrainingValidationException {
        TrainingModel model = new TrainingModel();
        model.setId(MOCK_ID);
        model.setMaxParticipants(1);
        model.setStartDate(LocalDateTime.now().plus(1L, ChronoUnit.DAYS));
        model.setEndDate(LocalDateTime.now().plus(1L, ChronoUnit.DAYS).plus(1L, ChronoUnit.HOURS));

        User participant1 = new User();
        participant1.setId(MOCK_USER_ID);
        User participant2 = new User();
        participant2.setId(UUID.randomUUID().toString());
        Training persistedTraining = new Training();
        persistedTraining.setParticipants(Sets.newHashSet(Arrays.asList(participant1, participant2)));
        when(repository.findById(any())).thenReturn(Optional.of(persistedTraining));

        service.modifyTraining(model);
    }

    @Test
    public void deleteTraining() throws ResourceNotFoundException {
        User participant1 = new User();
        participant1.setId(MOCK_ID);
        Training persistedTraining = new Training();
        persistedTraining.setParticipants(Sets.newHashSet(Arrays.asList(participant1)));
        when(repository.findById(eq(MOCK_ID))).thenReturn(Optional.of(persistedTraining));

        ArgumentCaptor<Training> trainingArgumentCaptor = ArgumentCaptor.forClass(Training.class);

        service.deleteTraining(MOCK_ID);

        verify(repository).save(trainingArgumentCaptor.capture());
        assertEquals(0, trainingArgumentCaptor.getValue().getParticipants().size());
        verify(repository).deleteById(eq(MOCK_ID));
    }

    @Test
    public void subscribeToTraining() throws ResourceNotFoundException {
        boolean subscription = true;

        User mockUser = new User();
        Training persistedTraining = new Training();
        when(repository.findById(eq(MOCK_ID))).thenReturn(Optional.of(persistedTraining));
        when(accountService.getAuthenticatedUserWithData()).thenReturn(mockUser);
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);

        service.subscribeToTraining(MOCK_ID, subscription);

        verify(accountService).saveAccount(userArgumentCaptor.capture());
        assertEquals(1, userArgumentCaptor.getValue().getTrainings().size());
    }

    @Test
    public void unSubscribeToTraining() throws ResourceNotFoundException {
        boolean subscription = false;

        Training persistedTraining = new Training();
        User mockUser = new User();
        mockUser.setTrainings(Sets.newHashSet(Collections.singletonList(persistedTraining)));
        when(repository.findById(eq(MOCK_ID))).thenReturn(Optional.of(persistedTraining));
        when(accountService.getAuthenticatedUserWithData()).thenReturn(mockUser);
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);

        service.subscribeToTraining(MOCK_ID, subscription);

        verify(accountService).saveAccount(userArgumentCaptor.capture());
        assertEquals(0, userArgumentCaptor.getValue().getTrainings().size());
    }

    @Test
    public void findAllByStartDateIsBefore_whenFound() {
        when(repository.findAllByStartDateIsBefore(any()))
                .thenReturn(Optional.of(Collections.singletonList(new Training())));
        List<Training> result = service.findAllByStartDateIsBefore(LocalDateTime.now());
        assertEquals(1, result.size());
    }

    @Test
    public void findAllByStartDateIsBefore_whenNotFound() {
        when(repository.findAllByStartDateIsBefore(any()))
                .thenReturn(Optional.empty());
        List<Training> result = service.findAllByStartDateIsBefore(LocalDateTime.now());
        assertEquals(0, result.size());
    }
}