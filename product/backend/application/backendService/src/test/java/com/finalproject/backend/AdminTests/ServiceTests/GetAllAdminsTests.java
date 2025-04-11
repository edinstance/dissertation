package com.finalproject.backend.AdminTests.ServiceTests;

import com.finalproject.backend.admin.dto.Admin;
import com.finalproject.backend.admin.entities.AdminEntity;
import com.finalproject.backend.admin.repositories.AdminRepository;
import com.finalproject.backend.admin.services.AdminService;
import com.finalproject.backend.common.exceptions.UnauthorisedException;
import com.finalproject.backend.common.helpers.AuthHelpers;
import com.finalproject.backend.permissions.authorizers.AdminAuthorizer;
import com.finalproject.backend.permissions.types.Actions;
import com.finalproject.backend.permissions.types.AdminViewTypes;
import com.finalproject.backend.permissions.types.Resources;
import com.finalproject.backend.users.entities.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GetAllAdminsTests {


  @Mock
  private AdminRepository adminRepository;

  @Mock
  private AuthHelpers authHelpers;

  @Mock
  private AdminAuthorizer adminAuthorizer;

  @InjectMocks
  private AdminService adminService;


  @Test
  public void getAllUsersNoResultsTest() {
    when(authHelpers.getCurrentUserId()).thenReturn(UUID.randomUUID());
    when(adminAuthorizer.authorize(
            any(UUID.class),
            eq(Resources.ADMINS),
            eq(Actions.READ),
            eq(AdminViewTypes.ALL)
    )).thenReturn(true);

    when(adminRepository.findAll()).thenReturn(List.of());

    List<Admin> result = adminService.getAllAdmins();

    assertNotNull(result);
    assertTrue(result.isEmpty());
    verify(adminRepository, times(1)).findAll();
  }

  @Test
  public void getAllUsersTest() {

    when(authHelpers.getCurrentUserId()).thenReturn(UUID.randomUUID());
    when(adminAuthorizer.authorize(
            any(UUID.class),
            eq(Resources.ADMINS),
            eq(Actions.READ),
            eq(AdminViewTypes.ALL)
    )).thenReturn(true);

    UUID adminId = UUID.randomUUID();
    AdminEntity adminEntity = new AdminEntity(adminId, false, "ACTIVE", adminId, adminId);
    adminEntity.setUser(new UserEntity(adminId, "Admin@test.com", "name"));
    Admin admin = new Admin(adminId, false, "ACTIVE", false, "Admin@test.com");

    when(adminRepository.findAll()).thenReturn(List.of(adminEntity));

    List<Admin> result = adminService.getAllAdmins();

    assertNotNull(result);
    assertFalse(result.isEmpty());
    assertEquals(1, result.size());

    Admin resultAdmin = result.getFirst();
    assertEquals(admin.getUserId(), resultAdmin.getUserId());
    assertEquals(admin.isSuperAdmin(), resultAdmin.isSuperAdmin());
    assertEquals(admin.getStatus(), resultAdmin.getStatus());
    assertEquals(admin.getIsDeleted(), resultAdmin.getIsDeleted());
    assertEquals(admin.getEmail(), resultAdmin.getEmail());

    verify(adminRepository, times(1)).findAll();
  }

  @Test
  public void testNoPermissions() {
    when(authHelpers.getCurrentUserId()).thenReturn(UUID.randomUUID());
    when(adminAuthorizer.authorize(
            any(UUID.class),
            eq(Resources.ADMINS),
            eq(Actions.READ),
            eq(AdminViewTypes.ALL)
    )).thenReturn(false);

    UnauthorisedException exception = assertThrows(UnauthorisedException.class, () -> {
      adminService.getAllAdmins();
    });

    assert exception.getMessage().equals("Admin does not have permission to view admin data");
  }
}