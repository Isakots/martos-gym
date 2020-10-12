package hu.isakots.martosgym.rest.reservation;

import hu.isakots.martosgym.domain.Reservation;
import hu.isakots.martosgym.exception.ReservationValidationException;
import hu.isakots.martosgym.exception.ResourceNotFoundException;
import hu.isakots.martosgym.service.ReservationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static hu.isakots.martosgym.rest.util.EndpointConstants.API_CONTEXT;

@RestController
@RequestMapping(value = API_CONTEXT)
public class ReservationResource {
    private static final String RESERVATION_ENDPOINT = "/reservations";

    private final ReservationService reservationService;

    public ReservationResource(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping(RESERVATION_ENDPOINT)
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public List<Reservation> getAllReservations() {
        return reservationService.findAllForAuthenticatedUser();
    }

    @GetMapping("/user/{userId}/" + RESERVATION_ENDPOINT)
    @PreAuthorize("hasAuthority('ROLE_MEMBER')")
    public List<Reservation> getAllReservationsByUser(@PathVariable String userId) throws ResourceNotFoundException {
        return reservationService.findAllByUser(userId);
    }

    @GetMapping("/tools/{toolId}/" + RESERVATION_ENDPOINT)
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public List<Reservation> getAllReservationsByToolId(@PathVariable String toolId) throws ResourceNotFoundException {
        return reservationService.findAllByTool(toolId);
    }

    @PostMapping(RESERVATION_ENDPOINT)
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public Reservation createReservation(@Valid @RequestBody Reservation reservation) throws ReservationValidationException {
        return reservationService.createReservation(reservation);
    }

    @PutMapping(RESERVATION_ENDPOINT)
    @PreAuthorize("hasAuthority('ROLE_MEMBER')")
    public Reservation updateReservation(@Valid @RequestBody Reservation reservation) throws ResourceNotFoundException {
        return reservationService.updateReservation(reservation);
    }

    @DeleteMapping(RESERVATION_ENDPOINT + "/{id}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<Void> deleteReservation(@PathVariable String id) throws ResourceNotFoundException {
        reservationService.deleteReservation(id);
        return ResponseEntity.noContent().build();
    }

}
