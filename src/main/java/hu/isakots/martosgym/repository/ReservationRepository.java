package hu.isakots.martosgym.repository;

import hu.isakots.martosgym.domain.Reservation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends CrudRepository<Reservation, Long> {

    Optional<List<Reservation>> findAllBySubjectNameIsAndIsReturnedFalse(String subjectName);

    Optional<List<Reservation>> findAllBySubjectName(String subjectName);
}
