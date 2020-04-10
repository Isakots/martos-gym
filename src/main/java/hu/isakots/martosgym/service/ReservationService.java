package hu.isakots.martosgym.service;

import hu.isakots.martosgym.domain.Reservation;
import hu.isakots.martosgym.domain.Tool;
import hu.isakots.martosgym.domain.User;
import hu.isakots.martosgym.exception.ReservationValidationException;
import hu.isakots.martosgym.exception.ResourceNotFoundException;
import hu.isakots.martosgym.repository.ReservationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;

@Service
public class ReservationService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReservationService.class.getName());
    private static final long MAX_RESERVATION_DAYS = 7L; // TODO add to config property

    private final ToolService toolService;
    private final ReservationRepository reservationRepository;
    private final AccountService accountService;

    public ReservationService(ToolService toolService, ReservationRepository reservationRepository, AccountService accountService) {
        this.toolService = toolService;
        this.reservationRepository = reservationRepository;
        this.accountService = accountService;
    }

    public List<Reservation> findAllForAuthenticatedUser() {
        return accountService.getAuthenticatedUserWithData().getReservations();
    }

    public List<Reservation> findAllByUser(Long userId) throws ResourceNotFoundException {
        return accountService.findById(userId).getReservations();
    }

    public List<Reservation> findAllByTool(Long toolId) throws ResourceNotFoundException {
        return reservationRepository.findAllBySubjectName(toolService.getTool(toolId).getName())
                .orElse(Collections.emptyList());
    }

    List<Reservation> findAllByNameAndNotReturned(String subjectName) {
        return reservationRepository.findAllBySubjectNameIsAndIsReturnedFalse(subjectName)
                .orElse(Collections.emptyList());
    }

    public Reservation createReservation(Reservation reservation) throws ReservationValidationException {
        LOGGER.debug("REST request to create Reservation : {}", reservation);
        if (reservation.getId() != null) {
            throw new IllegalArgumentException("The provided resource must not have an id.");
        }

        validateReservation(reservation);

        reservation.setReturned(false);
        User authenticatedUser = accountService.getAuthenticatedUserWithData();
        reservation.setUser(authenticatedUser);
        return reservationRepository.save(reservation);
    }

    private void validateReservation(Reservation reservation) throws ReservationValidationException {
        Tool tool = toolService.findByName(reservation.getSubjectName())
                .orElseThrow(
                        () -> new IllegalArgumentException(MessageFormat.format("Tool with name {0} cannot be reserved.", reservation.getSubjectName()))
                );
        validateReservationDates(reservation);
        validateQuantity(reservation, tool);
    }

    private void validateReservationDates(Reservation reservation) throws ReservationValidationException {
        if (reservation.getStartDate().isBefore(LocalDateTime.now())) {
            throw new ReservationValidationException("Reserving to the past is not allowed.");
        }
        if (reservation.getStartDate().isAfter(reservation.getEndDate())) {
            throw new ReservationValidationException("End date of reservation must be after start date.");
        }
        if (reservation.getStartDate().plus(MAX_RESERVATION_DAYS, ChronoUnit.DAYS).isBefore(reservation.getEndDate())) {
            throw new ReservationValidationException(
                    MessageFormat.format("Tool cannot be reserved for more than {0} days.", MAX_RESERVATION_DAYS)
            );
        }
    }

    private void validateQuantity(Reservation reservation, Tool tool) throws ReservationValidationException {
        List<Reservation> activeReservationsWithSpecifiedTool = findAllByNameAndNotReturned(tool.getName());
        int quantitySum = activeReservationsWithSpecifiedTool.stream()
                .map(Reservation::getQuantity)
                .reduce(0, Integer::sum);
        if (quantitySum + reservation.getQuantity() > tool.getQuantity()) {
            throw new ReservationValidationException("There is not enough tool for this reservation.");
        }
    }

    public Reservation updateReservation(Reservation reservation) throws ResourceNotFoundException {
        LOGGER.debug("REST request to update Reservation : {}", reservation);
        Long reservationId = reservation.getId();
        if (reservationId == null) {
            throw new IllegalArgumentException("The provided resource must have an id.");
        }

        Reservation persistedReservation = reservationRepository.findById(reservationId)
                .orElseThrow(
                        () -> new ResourceNotFoundException(MessageFormat.format("Reservation not found with id: {0}", reservationId))
                );
        persistedReservation.setReturned(reservation.isReturned());

        return reservationRepository.save(persistedReservation);
    }

    public void deleteReservation(Long id) throws ResourceNotFoundException {
        LOGGER.debug("REST request to delete Reservation : {}", id);

        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException(MessageFormat.format("Reservation not found with id: {0}", id))
                );
        if (!accountService.getAuthenticatedUserWithData().getReservations().contains(reservation)) {
            throw new UnsupportedOperationException("Illegal access.");
        }
        if (reservation.isReturned()) {
            throw new UnsupportedOperationException("Tool is already returned. Reservation cannot be deleted.");
        }

        reservationRepository.deleteById(id);
    }

}
