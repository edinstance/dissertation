package com.finalproject.backend.AdminTests.ServiceTests;

import com.finalproject.backend.admin.entities.AdminEntity;
import com.finalproject.backend.admin.repositories.AdminRepository;
import com.finalproject.backend.admin.services.AdminService;
import com.finalproject.backend.common.exceptions.UnauthorisedException;
import com.finalproject.backend.common.helpers.AuthHelpers;
import com.finalproject.backend.permissions.authorizers.AdminAuthorizer;
import com.finalproject.backend.permissions.types.Actions;
import com.finalproject.backend.permissions.types.AdminViewTypes;
import com.finalproject.backend.permissions.types.Resources;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CreateAdminTests {

  @Mock
  private AdminRepository adminRepository;

  @Mock
  private AuthHelpers authHelpers;

  @Mock
  private AdminAuthorizer adminAuthorizer;

  @InjectMocks
  private AdminService adminService;


  @Test
  public void testCreateAdmin() {
    UUID adminId = UUID.randomUUID();
    UUID userId = UUID.randomUUID();

    AdminEntity adminEntity = new AdminEntity(userId, false, "Active", adminId, adminId);

    when(authHelpers.getCurrentUserId()).thenReturn(adminId);
    when(adminAuthorizer.authorize(
            any(UUID.class),
            eq(Resources.ADMINS),
            eq(Actions.CREATE),
            eq(AdminViewTypes.ALL)
    )).thenReturn(true);

    Boolean result = adminService.createAdmin(userId);
    assertTrue(result);
  }

  @Test
  public void testCreateAdminNoPermissions() {
    UUID adminId = UUID.randomUUID();
    UUID userId = UUID.randomUUID();

    when(authHelpers.getCurrentUserId()).thenReturn(adminId);
    when(adminAuthorizer.authorize(
            any(UUID.class),
            eq(Resources.ADMINS),
            eq(Actions.CREATE),
            eq(AdminViewTypes.ALL)
    )).thenReturn(false);

    UnauthorisedException exception = assertThrows(
            UnauthorisedException.class, () -> {
      adminService.createAdmin(userId);
    });

    assert exception.getMessage().equals("Admin does not have permission to create new admins");
  }
}
