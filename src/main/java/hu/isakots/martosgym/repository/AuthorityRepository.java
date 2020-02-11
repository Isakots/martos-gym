package hu.isakots.martosgym.repository;

import hu.isakots.martosgym.domain.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, String> {
}