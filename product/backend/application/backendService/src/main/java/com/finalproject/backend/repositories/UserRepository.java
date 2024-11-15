package com.finalproject.backend.repositories;

import com.finalproject.backend.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * Repository interface for managing user entities.
 */
public interface UserRepository extends JpaRepository<UserEntity, UUID> {
}
