package backend.AdminTests.MutationTests;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import backend.admin.mutations.AdminMutations;
import backend.admin.services.AdminService;
import backend.common.dto.MutationResponse;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AdminMutationTests {

  @Mock
  private AdminService adminService;

  @InjectMocks
  private AdminMutations adminMutations;

  @Test
  public void createAdminTest() {
    UUID userId = UUID.randomUUID();

    MutationResponse result = adminMutations.createAdmin(userId.toString());

    assertTrue(result.isSuccess());
    assert result.getMessage().equals("Admin created successfully");
  }

  @Test
  public void promoteAdminToSuperAdminSuccessTest() {
    UUID userId = UUID.randomUUID();

    when(adminService.promoteAdminToSuperUser(userId)).thenReturn(true);

    MutationResponse result = adminMutations.promoteAdminToSuperAdmin(userId.toString());

    assertTrue(result.isSuccess());
    assert result.getMessage().equals("Admin promoted successfully");
  }

  @Test
  public void promoteAdminToSuperAdminFailureTest() {
    UUID userId = UUID.randomUUID();

    when(adminService.promoteAdminToSuperUser(userId)).thenReturn(false);

    MutationResponse result = adminMutations.promoteAdminToSuperAdmin(userId.toString());

    assertFalse(result.isSuccess());
    assert result.getMessage().equals("Error promoting admin");
  }

  @Test
  public void deactivateUserSuccessTest() {
    UUID userId = UUID.randomUUID();

    when(adminService.deactivateAdmin(userId)).thenReturn(true);

    MutationResponse result = adminMutations.deactivateAdmin(userId.toString());

    assertTrue(result.isSuccess());
    assert result.getMessage().equals("Admin deactivated successfully");
  }

  @Test
  public void deactivateUserFailureTest() {
    UUID userId = UUID.randomUUID();

    when(adminService.deactivateAdmin(userId)).thenReturn(false);

    MutationResponse result = adminMutations.deactivateAdmin(userId.toString());

    assertFalse(result.isSuccess());
    assert result.getMessage().equals("Error deactivating admin");
  }
}
