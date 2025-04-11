package com.finalproject.backend.AdminTests.ServiceTests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.finalproject.backend.admin.entities.AdminEntity;
import com.finalproject.backend.admin.repositories.AdminRepository;
import com.finalproject.backend.admin.services.AdminService;
import com.finalproject.backend.common.exceptions.UnauthorisedException;
import com.finalproject.backend.common.helpers.AuthHelpers;
import com.finalproject.backend.permissions.authorizers.AdminAuthorizer;
import com.finalproject.backend.permissions.types.Actions;
import com.finalproject.backend.permissions.types.AdminViewTypes;
import com.finalproject.backend.permissions.types.Resources;
import java.util.Optional;
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
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CreateAdminTests {

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

  @Mock
  private ObjectMapper objectMapper;

  @InjectMocks
  private AdminService adminService;

  UUID adminId = UUID.randomUUID();
  UUID userId = UUID.randomUUID();
  AdminEntity adminEntity = new AdminEntity(adminId, false, "ACTIVE", adminId, adminId);



  @Test
  public void testCreateAdmin() throws JsonProcessingException {
    when(jedisPool.getResource()).thenReturn(jedis);
    when(authHelpers.getCurrentUserId()).thenReturn(adminId);
    when(adminAuthorizer.authorize(
            any(UUID.class),
            eq(Resources.ADMINS),
            eq(Actions.CREATE),
            eq(AdminViewTypes.ALL)
    )).thenReturn(true);
    when(adminRepository.findById(adminId))
            .thenReturn(Optional.of(adminEntity));


    Boolean result = adminService.createAdmin(userId);
    assertTrue(result);
    verify(adminRepository).createAdmin(userId, adminId);
    verify(jedis).setex("admin:" + adminId, 3600, objectMapper.writeValueAsString(adminEntity));
  }

  @Test
  public void testCreateAdminNoPermissions() {
    when(authHelpers.getCurrentUserId()).thenReturn(adminId);
    when(adminAuthorizer.authorize(
            any(UUID.class),
            eq(Resources.ADMINS),
            eq(Actions.CREATE),
            eq(AdminViewTypes.ALL)
    )).thenReturn(false);

    UnauthorisedException exception = assertThrows(
            UnauthorisedException.class, () -> {
              adminService.createAdmin(userId);
            });

    assert exception.getMessage().equals("Admin does not have permission to create new admins");
  }

  @Test
  public void testCreateAdminCacheWriteError() throws JsonProcessingException {
    when(jedisPool.getResource()).thenReturn(jedis);
    when(authHelpers.getCurrentUserId()).thenReturn(adminId);
    when(adminAuthorizer.authorize(
            any(UUID.class),
            eq(Resources.ADMINS),
            eq(Actions.CREATE),
            eq(AdminViewTypes.ALL)
    )).thenReturn(true);
    when(adminRepository.findById(adminId))
            .thenReturn(Optional.of(adminEntity));

    when(objectMapper.writeValueAsString(any())).thenThrow(JsonProcessingException.class);

    Boolean result = adminService.createAdmin(userId);

    assertTrue(result);
    verify(adminRepository).createAdmin(userId, adminId);
  }

  @Test
  public void testCreateAdminCacheError() throws JsonProcessingException {
    when(jedisPool.getResource()).thenThrow(new RuntimeException("Cache write error"));
    when(authHelpers.getCurrentUserId()).thenReturn(adminId);
    when(adminAuthorizer.authorize(
            any(UUID.class),
            eq(Resources.ADMINS),
            eq(Actions.CREATE),
            eq(AdminViewTypes.ALL)
    )).thenReturn(true);

    adminService.createAdmin(userId);

    verify(adminRepository, never()).findById(adminId);
    verify(jedis, never()).setex("admin:" + adminId, 3600, objectMapper.writeValueAsString(adminEntity));
  }
}
