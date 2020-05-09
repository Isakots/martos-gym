package hu.isakots.martosgym.repository;

import hu.isakots.martosgym.domain.Training;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TrainingRepository extends JpaRepository<Training, Long> {

    Optional<List<Training>> findAllByStartDateIsBefore(LocalDateTime startDateTimeout);

}
