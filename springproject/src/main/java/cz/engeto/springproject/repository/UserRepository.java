package cz.engeto.springproject.repository;

import cz.engeto.springproject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByPersonId(String personId);

    boolean existsByPersonId(String personId);
}
