package backend.AdminTests.ServiceTests;


import com.fasterxml.jackson.databind.ObjectMapper;
import backend.admin.entities.AdminEntity;
import backend.admin.repositories.AdminRepository;
import backend.admin.services.AdminService;
import backend.common.exceptions.UnauthorisedException;
import backend.common.helpers.AuthHelpers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import java.util.Optional;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PromoteAdminToSuperAdminTests {

  @Mock
  private AdminRepository adminRepository;

  @Mock
  private AuthHelpers authHelpers;

  @Mock
  private JedisPool jedisPool;

  @Mock
  private Jedis jedis;

  @Mock
  private ObjectMapper objectMapper;

  @InjectMocks
  private AdminService adminService;


  private final UUID adminId = UUID.randomUUID();
  private final UUID userId = UUID.randomUUID();

  @Test
  public void promoteAdmin() {
    AdminEntity adminEntity = new AdminEntity(adminId, true, "ACTIVE", adminId, adminId);

    when(authHelpers.getCurrentUserId()).thenReturn(adminId);
    when(adminRepository.findById(adminId)).thenReturn(Optional.of(adminEntity));
    when(jedisPool.getResource()).thenReturn(jedis);

    Boolean result = adminService.promoteAdminToSuperUser(userId);
    assertTrue(result);
    verify(adminRepository).makeAdminSuperAdmin(userId, adminId);
    verify(jedis).del("admin:" + userId);
  }

  @Test
  public void testPromoteAdminNoPermissions() {
    AdminEntity adminEntity = new AdminEntity(adminId, false, "ACTIVE", adminId, adminId);

    when(authHelpers.getCurrentUserId()).thenReturn(adminId);
    when(adminRepository.findById(adminId)).thenReturn(Optional.of(adminEntity));
    when(jedisPool.getResource()).thenReturn(jedis);

    UnauthorisedException exception = assertThrows(
            UnauthorisedException.class, () -> {
              adminService.promoteAdminToSuperUser(userId);
            });

    assert exception.getMessage().equals("Admin does not have permission to create super admins");
  }
}
