package backend.AdminTests.ServiceTests;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import backend.admin.repositories.AdminRepository;
import backend.admin.services.AdminService;
import backend.common.exceptions.UnauthorisedException;
import backend.common.helpers.AuthHelpers;
import backend.permissions.authorizers.AdminAuthorizer;
import backend.permissions.types.Actions;
import backend.permissions.types.AdminViewTypes;
import backend.permissions.types.Resources;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@ExtendWith(MockitoExtension.class)
public class DeactivateAdminTests {

  @Mock
  private AdminRepository adminRepository;

  @Mock
  private AuthHelpers authHelpers;

  @Mock
  private AdminAuthorizer adminAuthorizer;

  @Mock
  private JedisPool jedisPool;

  @Mock
  private Jedis jedis;

  @InjectMocks
  private AdminService adminService;

  private final UUID adminId = UUID.randomUUID();
  private final UUID userId = UUID.randomUUID();


  @Test
  public void deactivateAdmin() {
    when(authHelpers.getCurrentUserId()).thenReturn(adminId);
    when(jedisPool.getResource()).thenReturn(jedis);
    when(adminAuthorizer.authorize(
            any(UUID.class),
            eq(Resources.ADMINS),
            eq(Actions.DELETE),
            eq(AdminViewTypes.ALL)
    )).thenReturn(true);

    Boolean result = adminService.deactivateAdmin(userId);

    verify(adminRepository).deactivateAdmin(userId, adminId);
    verify(jedis).del("admin:" + userId);
    assertTrue(result);
  }

  @Test
  public void deactivateAdminNotAuthorized() {
    when(authHelpers.getCurrentUserId()).thenReturn(adminId);
    when(adminAuthorizer.authorize(
            any(UUID.class),
            eq(Resources.ADMINS),
            eq(Actions.DELETE),
            eq(AdminViewTypes.ALL)
    )).thenReturn(false);

    UnauthorisedException exception = assertThrows(
            UnauthorisedException.class, () -> {
              adminService.deactivateAdmin(userId);
            });

    verify(adminRepository, times(0)).deactivateAdmin(userId, adminId);
    assert exception.getMessage().equals("Admin does not have permission to deactivate admins");
  }


}