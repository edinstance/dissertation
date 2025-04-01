package com.finalproject.backend.AdminTests.ServiceTests;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.finalproject.backend.admin.entities.AdminEntity;
import com.finalproject.backend.admin.repositories.AdminRepository;
import com.finalproject.backend.admin.services.AdminService;
import com.finalproject.backend.common.exceptions.UnauthorisedException;
import com.finalproject.backend.common.helpers.AuthHelpers;
import com.finalproject.backend.permissions.authorizers.AdminAuthorizer;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

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


  @Test
  public void promoteAdmin() {
    UUID adminId = UUID.randomUUID();
    UUID userId = UUID.randomUUID();

    AdminEntity adminEntity = new AdminEntity(adminId, true, "ACTIVE", adminId, adminId);

    when(authHelpers.getCurrentUserId()).thenReturn(adminId);
    when(adminRepository.findById(adminId)).thenReturn(Optional.of(adminEntity));
    when(jedisPool.getResource()).thenReturn(jedis);

    Boolean result = adminService.promoteAdminToSuperUser(userId);
    assertTrue(result);
    verify(adminRepository).makeAdminSuperAdmin(userId, adminId);
  }

  @Test
  public void testPromoteAdminNoPermissions() {
    UUID adminId = UUID.randomUUID();
    UUID userId = UUID.randomUUID();

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
