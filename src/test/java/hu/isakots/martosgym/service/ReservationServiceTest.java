package hu.isakots.martosgym.service;


import hu.isakots.martosgym.configuration.properties.MartosGymProperties;
import hu.isakots.martosgym.domain.Reservation;
import hu.isakots.martosgym.domain.Tool;
import hu.isakots.martosgym.domain.User;
import hu.isakots.martosgym.exception.ReservationValidationException;
import hu.isakots.martosgym.exception.ResourceNotFoundException;
import hu.isakots.martosgym.repository.ReservationRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ReservationServiceTest {

    private static final Long MOCK_ID = 1L;
    private static final String MOCK_SUBJECT_NAME = "MOCK_SUBJECT_NAME";
    private static final Long MAX_RESERVATION_DAYS = 7l;

    @Mock
    private ToolService toolService;

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private AccountService accountService;

    @Mock
    private MartosGymProperties martosGymProperties;

    @InjectMocks
    private ReservationService reservationService;

    @Test
    public void findAllForAuthenticatedUser() {
        User mockUser = new User();
        mockUser.setReservations(Collections.singletonList(new Reservation()));
        when(accountService.getAuthenticatedUserWithData()).thenReturn(mockUser);

        List<Reservation> result = reservationService.findAllForAuthenticatedUser();

        assertEquals(1, result.size());
    }

    @Test
    public void findAllByUser() throws ResourceNotFoundException {
        final Long id = 1L;
        User mockUser = new User();
        mockUser.setId(id);
        mockUser.setReservations(Collections.singletonList(new Reservation()));
        when(accountService.getAuthenticatedUserWithData()).thenReturn(mockUser);

        List<Reservation> result = reservationService.findAllForAuthenticatedUser();

        assertEquals(1, result.size());
    }

    @Test
    public void findAllByTool_whenFound() throws ResourceNotFoundException {
        Reservation reservation = new Reservation();
        String subjectName = "subjectName";
        reservation.setSubjectName(subjectName);
        when(reservationRepository.findAllBySubjectName(eq(subjectName)))
                .thenReturn(Optional.of(Collections.singletonList(reservation)));
        Tool tool = new Tool();
        final Long id = 1L;
        tool.setId(id);
        tool.setName(subjectName);
        when(toolService.getTool(eq(id))).thenReturn(tool);

        List<Reservation> result = reservationService.findAllByTool(id);
        assertEquals(1, result.size());
    }

    @Test
    public void findAllByTool_whenNotFound() throws ResourceNotFoundException {
        String subjectName = "subjectName";
        when(reservationRepository.findAllBySubjectName(eq(subjectName))).thenReturn(Optional.empty());
        Tool tool = new Tool();
        final Long id = 1L;
        tool.setId(id);
        tool.setName(subjectName);
        when(toolService.getTool(eq(id))).thenReturn(tool);

        List<Reservation> result = reservationService.findAllByTool(id);
        assertEquals(0, result.size());
    }


    @Test
    public void findAllByNameAndNotReturned_whenFound() {
        String name = "subjectName";
        when(reservationRepository.findAllBySubjectNameIsAndIsReturnedFalse(eq(name)))
                .thenReturn(Optional.of(Collections.singletonList(new Reservation())));

        List<Reservation> result = reservationService.findAllByNameAndNotReturned(name);
        assertEquals(1, result.size());
    }

    @Test
    public void findAllByNameAndNotReturned_whenNotFound() {
        String name = "subjectName";
        when(reservationRepository.findAllBySubjectNameIsAndIsReturnedFalse(eq(name))).thenReturn(Optional.empty());

        List<Reservation> result = reservationService.findAllByNameAndNotReturned(name);
        assertEquals(0, result.size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void createReservation_whenAlreadyHasId_thenIllegalArgumentExceptionIsThrown() throws ReservationValidationException {
        Reservation reservation = new Reservation();
        reservation.setId(MOCK_ID);
        reservationService.createReservation(reservation);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createReservation_whenToolNotFound_thenIllegalArgumentExceptionIsThrown() throws ReservationValidationException {
        Reservation reservation = new Reservation();
        reservation.setSubjectName(MOCK_SUBJECT_NAME);
        when(toolService.findByName(eq(MOCK_SUBJECT_NAME))).thenReturn(Optional.empty());
        reservationService.createReservation(reservation);
    }

    @Test(expected = ReservationValidationException.class)
    public void createReservation_whenReservationStartDateIsBeforeNow_thenReservationValidationExceptionIsThrown() throws ReservationValidationException {
        Reservation reservation = new Reservation();
        reservation.setSubjectName(MOCK_SUBJECT_NAME);
        reservation.setStartDate(LocalDateTime.now().minus(2L, ChronoUnit.HOURS));
        when(toolService.findByName(eq(MOCK_SUBJECT_NAME))).thenReturn(Optional.of(new Tool()));
        reservationService.createReservation(reservation);
    }

    @Test(expected = ReservationValidationException.class)
    public void createReservation_whenReservationEndDateisBeforeStartDate_thenReservationValidationExceptionIsThrown() throws ReservationValidationException {
        Reservation reservation = new Reservation();
        reservation.setSubjectName(MOCK_SUBJECT_NAME);
        reservation.setStartDate(LocalDateTime.now().plus(2L, ChronoUnit.DAYS).plus(2L, ChronoUnit.HOURS));
        reservation.setEndDate(LocalDateTime.now().plus(2L, ChronoUnit.DAYS));
        when(toolService.findByName(eq(MOCK_SUBJECT_NAME))).thenReturn(Optional.of(new Tool()));
        reservationService.createReservation(reservation);
    }

    @Test(expected = ReservationValidationException.class)
    public void createReservation_whenReservationIsLongerThanMaxDaysAllowed_thenReservationValidationExceptionIsThrown() throws ReservationValidationException {
        Reservation reservation = new Reservation();
        reservation.setSubjectName(MOCK_SUBJECT_NAME);
        reservation.setStartDate(LocalDateTime.now().plus(2L, ChronoUnit.DAYS));
        reservation.setEndDate(LocalDateTime.now().plus(10L, ChronoUnit.DAYS));
        when(toolService.findByName(eq(MOCK_SUBJECT_NAME))).thenReturn(Optional.of(new Tool()));
        when(martosGymProperties.getMaxReservationDays()).thenReturn(MAX_RESERVATION_DAYS);
        reservationService.createReservation(reservation);
    }

    @Test(expected = ReservationValidationException.class)
    public void createReservation_whenReservationQuantityIsHigherThanReachableTools_thenReservationValidationExceptionIsThrown() throws ReservationValidationException {
        Reservation reservation = new Reservation();
        reservation.setSubjectName(MOCK_SUBJECT_NAME);
        reservation.setStartDate(LocalDateTime.now().plus(2L, ChronoUnit.DAYS));
        reservation.setEndDate(LocalDateTime.now().plus(5L, ChronoUnit.DAYS));
        reservation.setQuantity(2);
        Tool foundTool = new Tool();
        foundTool.setName(MOCK_SUBJECT_NAME);
        foundTool.setQuantity(5);
        when(toolService.findByName(eq(MOCK_SUBJECT_NAME))).thenReturn(Optional.of(foundTool));
        when(martosGymProperties.getMaxReservationDays()).thenReturn(MAX_RESERVATION_DAYS);
        Reservation reservationInDatabase = new Reservation();
        reservationInDatabase.setQuantity(4);
        when(reservationRepository.findAllBySubjectNameIsAndIsReturnedFalse(eq(MOCK_SUBJECT_NAME)))
                .thenReturn(Optional.of(Collections.singletonList(reservationInDatabase)));

        reservationService.createReservation(reservation);
    }


    @Test
    public void createReservation_whenValidated_thenSave() throws ReservationValidationException {
        Reservation reservation = new Reservation();
        reservation.setSubjectName(MOCK_SUBJECT_NAME);
        reservation.setStartDate(LocalDateTime.now().plus(2L, ChronoUnit.DAYS));
        reservation.setEndDate(LocalDateTime.now().plus(5L, ChronoUnit.DAYS));
        reservation.setQuantity(2);
        Tool foundTool = new Tool();
        foundTool.setName(MOCK_SUBJECT_NAME);
        foundTool.setQuantity(5);
        when(toolService.findByName(eq(MOCK_SUBJECT_NAME))).thenReturn(Optional.of(foundTool));
        when(martosGymProperties.getMaxReservationDays()).thenReturn(MAX_RESERVATION_DAYS);
        Reservation reservationInDatabase = new Reservation();
        reservationInDatabase.setQuantity(3);
        when(reservationRepository.findAllBySubjectNameIsAndIsReturnedFalse(eq(MOCK_SUBJECT_NAME)))
                .thenReturn(Optional.of(Collections.singletonList(reservationInDatabase)));

        when(accountService.getAuthenticatedUserWithData()).thenReturn(new User());

        reservationService.createReservation(reservation);
        verify(reservationRepository).save(any());
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateReservation_whenDoesNotHaveId_thenIllegalArgumentExceptionIsThrown() throws ResourceNotFoundException {
        Reservation reservation = new Reservation();
        reservationService.updateReservation(reservation);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void updateReservation_whenNotFound_thenResourceNotFoundExceptionIsThrown() throws ResourceNotFoundException {
        Reservation reservation = new Reservation();
        reservation.setId(MOCK_ID);
        when(reservationRepository.findById(eq(MOCK_ID))).thenReturn(Optional.empty());
        reservationService.updateReservation(reservation);
    }

    @Test
    public void updateReservation_whenFound_thenSave() throws ResourceNotFoundException {
        Reservation reservation = new Reservation();
        reservation.setId(MOCK_ID);
        when(reservationRepository.findById(eq(MOCK_ID))).thenReturn(Optional.of(new Reservation()));

        reservationService.updateReservation(reservation);

        verify(reservationRepository).save(any());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void deleteReservation_whenNotFound() throws ResourceNotFoundException {
        when(reservationRepository.findById(any())).thenReturn(Optional.empty());
        reservationService.deleteReservation(1L);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void deleteReservation_whenDeleteNotOwnedReservation() throws ResourceNotFoundException {
        Reservation reservation = new Reservation();
        User mockUser = new User();
        mockUser.setReservations(Collections.emptyList());
        mockUser.setId(1L);
        when(reservationRepository.findById(any())).thenReturn(Optional.of(reservation));
        when(accountService.getAuthenticatedUserWithData()).thenReturn(mockUser);
        reservationService.deleteReservation(1L);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void deleteReservation_whenAlreadyReturned() throws ResourceNotFoundException {
        Reservation reservation = new Reservation();
        reservation.setReturned(true);
        User mockUser = new User();
        mockUser.setReservations(Collections.singletonList(reservation));
        mockUser.setId(1L);
        when(reservationRepository.findById(any())).thenReturn(Optional.of(reservation));
        when(accountService.getAuthenticatedUserWithData()).thenReturn(mockUser);
        reservationService.deleteReservation(1L);
    }

    @Test
    public void deleteReservation_whenAllowedToDelete() throws ResourceNotFoundException {
        Reservation reservation = new Reservation();
        Long reservationId = 1L;
        reservation.setId(reservationId);
        reservation.setReturned(false);
        User mockUser = new User();
        mockUser.setReservations(Collections.singletonList(reservation));
        mockUser.setId(1L);
        when(reservationRepository.findById(any())).thenReturn(Optional.of(reservation));
        when(accountService.getAuthenticatedUserWithData()).thenReturn(mockUser);
        reservationService.deleteReservation(1L);

        verify(reservationRepository).deleteById(eq(reservationId));
    }

}