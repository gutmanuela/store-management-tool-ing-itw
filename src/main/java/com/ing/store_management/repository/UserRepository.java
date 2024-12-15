package com.ing.store_management.repository;

import com.ing.store_management.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository  extends JpaRepository<User,Long> {
}
