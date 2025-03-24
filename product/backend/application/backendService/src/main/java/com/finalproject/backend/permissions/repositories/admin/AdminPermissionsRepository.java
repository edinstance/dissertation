package com.finalproject.backend.permissions.repositories.admin;

import com.finalproject.backend.permissions.entities.admin.AdminPermissionsEntity;
import com.finalproject.backend.permissions.entities.admin.ids.AdminPermissionsEntityId;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * This repository is responsible for managing the admin permissions.
 */
public interface AdminPermissionsRepository extends JpaRepository
        <AdminPermissionsEntity, AdminPermissionsEntityId> {
}
