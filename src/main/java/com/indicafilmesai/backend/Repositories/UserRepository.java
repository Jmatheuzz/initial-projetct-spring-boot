package com.indicafilmesai.backend.Repositories;

import com.indicafilmesai.backend.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByEmailAndCode(String email, String code);
}

