package hu.isakots.martosgym.repository;

import hu.isakots.martosgym.domain.GymPeriod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GymPeriodRepository extends JpaRepository<GymPeriod, Long> {

    Optional<GymPeriod> findByIsActiveTrue();

}
