package com.orlovandrei.authservice.repository;

import com.orlovandrei.authservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = "select u from User u where u.username = :username")
    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
}
