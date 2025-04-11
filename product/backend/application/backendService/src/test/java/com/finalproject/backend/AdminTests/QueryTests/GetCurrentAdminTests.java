package com.finalproject.backend.AdminTests.QueryTests;

import com.finalproject.backend.admin.dto.Admin;
import com.finalproject.backend.admin.queries.AdminQueries;
import com.finalproject.backend.admin.services.AdminService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GetCurrentAdminTests {

  @Mock
  private AdminService adminService;

  @InjectMocks
  private AdminQueries adminQueries;

  private final UUID adminId = UUID.randomUUID();
  private final Admin admin = new Admin(adminId, false, "ACTIVE", false, "admin@test.com");

  @Test
  void testGetCurrentAdmin() {
    when(adminService.getCurrentAdmin()).thenReturn(admin);

    Admin result = adminQueries.getCurrentAdmin();

    assertNotNull(admin);
    assert result.equals(admin);
  }
}
