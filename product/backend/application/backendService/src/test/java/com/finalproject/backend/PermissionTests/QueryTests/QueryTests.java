package com.finalproject.backend.PermissionTests.QueryTests;

import com.finalproject.backend.permissions.entities.PermissionsEntity;
import com.finalproject.backend.permissions.queries.PermissionsQueries;
import com.finalproject.backend.permissions.services.PermissionsService;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class QueryTests {

  @Mock
  private PermissionsService permissionsService;

  @InjectMocks
  private PermissionsQueries permissionQueries;

  @Test
  public void testGetAllPermissions() {
    when(permissionsService.getAllPermissions()).thenReturn(List.of(new PermissionsEntity()));
    List<PermissionsEntity> results = permissionQueries.getAllPermissions();

    assert results.size() == 1;
  }
}
