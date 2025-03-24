package com.finalproject.backend.PermissionTests.QueryTests;

import com.finalproject.backend.permissions.entities.PermissionView;
import com.finalproject.backend.permissions.queries.AdminPermissionQueries;
import com.finalproject.backend.permissions.services.PermissionsService;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AdminQueryTests {

  @Mock
  private PermissionsService permissionsService;

  @InjectMocks
    private AdminPermissionQueries adminPermissionQueries;

  @Test
  public void testGetAllAdminPermissions() {
    when(permissionsService.getAllAdminPermissions()).thenReturn(List.of(new PermissionView()));
    List<PermissionView> results = adminPermissionQueries.getAllAdminPermissions();

    assert results.size() == 1;
  }

  @Test
  public void testGetCurrentAdminPermissions() {
    when(permissionsService.getCurrentAdminPermissions()).thenReturn(List.of(new PermissionView()));
    List<PermissionView> results = adminPermissionQueries.getCurrentAdminPermissions();

    assert results.size() == 1;
  }
}
