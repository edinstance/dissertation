package com.finalproject.backend.AdminTests.QueryTests;

import com.finalproject.backend.admin.entities.AdminEntity;
import com.finalproject.backend.admin.queries.AdminQueries;
import com.finalproject.backend.admin.services.AdminService;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GetCurrentAdminTests {

  @Mock
  private AdminService adminService;

  @InjectMocks
    private AdminQueries adminQueries;

  private final UUID adminId = UUID.randomUUID();
  private final AdminEntity adminEntity = new AdminEntity(adminId, false, "ACTIVE", adminId, adminId);

    @Test
    void testGetCurrentAdmin() {
      when(adminService.getCurrentAdmin()).thenReturn(adminEntity);

      AdminEntity admin = adminQueries.getCurrentAdmin();

      assertNotNull(admin);
      assert adminEntity.equals(admin);
    }
}
