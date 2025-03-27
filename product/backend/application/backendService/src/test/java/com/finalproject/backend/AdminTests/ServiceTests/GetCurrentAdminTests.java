package com.finalproject.backend.AdminTests.ServiceTests;

import com.finalproject.backend.admin.dto.Admin;
import com.finalproject.backend.admin.entities.AdminEntity;
import com.finalproject.backend.admin.repositories.AdminRepository;
import com.finalproject.backend.admin.services.AdminService;
import com.finalproject.backend.common.helpers.AuthHelpers;
import com.finalproject.backend.users.entities.UserEntity;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GetCurrentAdminTests {

  @Mock
  private AdminRepository adminRepository;

  @Mock
  private AuthHelpers authHelpers;

  @InjectMocks
  private AdminService adminService;

  UUID adminId = UUID.randomUUID();
  AdminEntity adminEntity = new AdminEntity(adminId, false, "ACTIVE", adminId, adminId);
  Admin admin = new Admin(adminId, false, "ACTIVE", false, "Admin@test.com");

  @Test
  public void testGetCurrentAdmin() {
    when(authHelpers.getCurrentUserId()).thenReturn(adminId);
    when(adminRepository.findById(adminId)).thenReturn(Optional.of(adminEntity));

    adminEntity.setUser(new UserEntity(adminId, "Admin@test.com", "Name"));

    Admin currentAdmin = adminService.getCurrentAdmin();
    assertEquals(admin.getUserId(), currentAdmin.getUserId());
    assertEquals(admin.getEmail(), currentAdmin.getEmail());
    assertEquals(admin.getStatus(), currentAdmin.getStatus());
    assertEquals(admin.isSuperAdmin(), currentAdmin.isSuperAdmin());
    assertEquals(admin.getIsDeleted(), currentAdmin.getIsDeleted());
  }

  @Test
  public void testGetCurrentAdminNull() {
    when(authHelpers.getCurrentUserId()).thenReturn(adminId);
    when(adminRepository.findById(adminId)).thenReturn(Optional.empty());

    Admin currentAdmin = adminService.getCurrentAdmin();
    assertNull(currentAdmin);
  }

}

