package com.finalproject.backend.AdminTests.ServiceTests;

import com.finalproject.backend.admin.repositories.AdminRepository;
import com.finalproject.backend.admin.services.AdminService;
import com.finalproject.backend.common.Exceptions.UnauthorisedException;
import com.finalproject.backend.common.helpers.AuthHelpers;
import com.finalproject.backend.permissions.authorizers.AdminAuthorizer;
import com.finalproject.backend.permissions.types.Actions;
import com.finalproject.backend.permissions.types.AdminViewTypes;
import com.finalproject.backend.permissions.types.Resources;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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