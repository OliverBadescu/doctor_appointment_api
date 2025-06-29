package mycode.doctor_appointment_api.app.users.repository;

import mycode.doctor_appointment_api.app.users.model.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


/**
 * Repository interface for User entity operations.
 */
public interface UserRepository extends JpaRepository<User, Integer> {

    @EntityGraph(attributePaths = {"appointments"}, type = EntityGraph.EntityGraphType.FETCH)
    Optional<User> findById(long id);

    @EntityGraph(attributePaths = {"appointments"}, type = EntityGraph.EntityGraphType.FETCH)
    Optional<User> findByEmail(String email);

}
