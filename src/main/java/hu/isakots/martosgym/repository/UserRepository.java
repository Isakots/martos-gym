package hu.isakots.martosgym.repository;

import hu.isakots.martosgym.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findOneWithAuthoritiesByEmailIgnoreCase(String email);
}
