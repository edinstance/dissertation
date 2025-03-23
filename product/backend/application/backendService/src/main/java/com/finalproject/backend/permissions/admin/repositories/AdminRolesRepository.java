package com.finalproject.backend.permissions.admin.repositories;

import com.finalproject.backend.permissions.admin.entities.AdminRolesEntity;
import com.finalproject.backend.permissions.admin.entities.ids.AdminRolesEntityId;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * This repository is responsible for managing the admin roles.
 */
public interface AdminRolesRepository extends JpaRepository<AdminRolesEntity, AdminRolesEntityId> {
}
