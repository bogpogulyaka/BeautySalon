package org.salon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.salon.models.User;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT * FROM users WHERE login = ?1")
    User findByLogin(String login);
}
