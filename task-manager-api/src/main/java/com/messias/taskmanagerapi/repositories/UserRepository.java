package com.messias.taskmanagerapi.repositories;

import com.messias.taskmanagerapi.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findById(UUID id);

    @Query("SELECT u FROM User u WHERE u.username = :username")
    User findByUsername(@Param(value = "username") String username);
}
