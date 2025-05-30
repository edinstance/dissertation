package backend.AdminTests.QueryTests;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import backend.admin.dto.Admin;
import backend.admin.queries.AdminQueries;
import backend.admin.services.AdminService;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
public class GetAllAdminsTests {

  @Mock
  private AdminService adminService;

  @InjectMocks
  private AdminQueries adminQueries;

  @Test
  void testGetAllUsers() {
    UUID adminId = UUID.randomUUID();
    Admin admin = new Admin(adminId, false, "Active", false, "Admin@test.com");

    when(adminService.getAllAdmins()).thenReturn(List.of(admin));

    List<Admin> admins = adminQueries.getAllAdmins();

    assertNotNull(admins);
    assert admins.size() == 1;
    assert admins.contains(admin);
  }
}
