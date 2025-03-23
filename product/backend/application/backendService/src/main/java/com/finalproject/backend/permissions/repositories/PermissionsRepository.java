package com.finalproject.backend.permissions.repositories;

import com.finalproject.backend.permissions.entities.PermissionsEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing permissions entities.
 */
public interface PermissionsRepository extends JpaRepository<PermissionsEntity, UUID> {
}
