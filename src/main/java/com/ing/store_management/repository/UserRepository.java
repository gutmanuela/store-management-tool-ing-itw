package com.ing.store_management.repository;

import com.ing.store_management.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository  extends JpaRepository<User,Long> {
    void deleteUserByUsername(String username);

    Optional<User> findByUsername(String username);
}
