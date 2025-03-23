package com.finalproject.backend.permissions.admin.repositories;

import com.finalproject.backend.permissions.admin.entities.AdminPermissionsEntity;
import com.finalproject.backend.permissions.admin.entities.ids.AdminPermissionsEntityId;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * This repository is responsible for managing the admin permissions.
 */
public interface AdminPermissionsRepository extends JpaRepository
        <AdminPermissionsEntity, AdminPermissionsEntityId> {
}
