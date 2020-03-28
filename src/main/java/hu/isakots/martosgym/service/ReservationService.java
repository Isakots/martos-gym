package hu.isakots.martosgym.service;

import hu.isakots.martosgym.repository.ReservationRepository;
import hu.isakots.martosgym.repository.ToolRepository;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {

    private final ToolRepository toolRepository;
    private final ReservationRepository reservationRepository;

    public ReservationService(ToolRepository toolRepository, ReservationRepository reservationRepository) {
        this.toolRepository = toolRepository;
        this.reservationRepository = reservationRepository;
    }



}
