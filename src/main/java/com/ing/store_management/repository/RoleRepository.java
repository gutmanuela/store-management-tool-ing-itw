package com.ing.store_management.repository;

import com.ing.store_management.model.entity.Role;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(@NotBlank String name);

    void deleteRoleByName(@NotBlank String name);
}
