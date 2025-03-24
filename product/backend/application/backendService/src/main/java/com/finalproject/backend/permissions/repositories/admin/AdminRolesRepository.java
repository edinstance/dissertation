package com.finalproject.backend.permissions.repositories.admin;

import com.finalproject.backend.permissions.entities.admin.AdminRolesEntity;
import com.finalproject.backend.permissions.entities.admin.ids.AdminRolesEntityId;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * This repository is responsible for managing the admin roles.
 */
public interface AdminRolesRepository extends JpaRepository<AdminRolesEntity, AdminRolesEntityId> {
}
