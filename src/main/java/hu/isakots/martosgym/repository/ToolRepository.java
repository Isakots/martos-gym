package hu.isakots.martosgym.repository;

import hu.isakots.martosgym.domain.Tool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ToolRepository extends JpaRepository<Tool, Long> {

    Optional<Tool> findByName(String name);

}
