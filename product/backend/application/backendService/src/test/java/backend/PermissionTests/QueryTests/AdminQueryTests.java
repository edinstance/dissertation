package backend.PermissionTests.QueryTests;

import static org.mockito.Mockito.when;

import backend.permissions.entities.PermissionView;
import backend.permissions.entities.PermissionsEntity;
import backend.permissions.queries.AdminPermissionQueries;
import backend.permissions.services.AdminPermissionsService;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AdminQueryTests {

  @Mock
  private AdminPermissionsService adminPermissionsService;

  @InjectMocks
  private AdminPermissionQueries adminPermissionQueries;

  @Test
  public void testGetAllAdminPermissions() {
    when(adminPermissionsService.getAllAdminPermissions()).thenReturn(List.of(new PermissionView()));
    List<PermissionView> results = adminPermissionQueries.getAllAdminPermissions();

    assert results.size() == 1;
  }

  @Test
  public void testGetCurrentAdminPermissions() {
    when(adminPermissionsService.getCurrentAdminPermissions()).thenReturn(List.of(new PermissionView()));
    List<PermissionView> results = adminPermissionQueries.getCurrentAdminPermissions();

    assert results.size() == 1;
  }

  @Test
  public void testGetAdminPermissionsByAdminId() {
    UUID adminId = UUID.randomUUID();
    when(adminPermissionsService.getAdminPermissionsById(adminId)).thenReturn(List.of(new PermissionsEntity()));
    List<PermissionsEntity> results = adminPermissionQueries.getAdminPermissionsByAdminId(adminId.toString());

    assert results.size() == 1;
  }
}
