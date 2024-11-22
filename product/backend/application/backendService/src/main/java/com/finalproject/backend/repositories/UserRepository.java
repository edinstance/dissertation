package com.finalproject.backend.repositories;

import com.finalproject.backend.entities.UserEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing user entities.
 */
public interface UserRepository extends JpaRepository<UserEntity, UUID> {
}
