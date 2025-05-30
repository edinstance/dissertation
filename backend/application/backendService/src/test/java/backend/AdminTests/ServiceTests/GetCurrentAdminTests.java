package backend.AdminTests.ServiceTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import backend.admin.dto.Admin;
import backend.admin.entities.AdminEntity;
import backend.admin.repositories.AdminRepository;
import backend.admin.services.AdminService;
import backend.common.helpers.AuthHelpers;
import backend.users.entities.UserEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@ExtendWith(MockitoExtension.class)
public class GetCurrentAdminTests {

  @Mock
  private AdminRepository adminRepository;

  @Mock
  private JedisPool jedisPool;

  @Mock
  private Jedis jedis;

  @Mock
  private ObjectMapper mockObjectMapper;

  @Mock
  private AuthHelpers authHelpers;

  @InjectMocks
  private AdminService adminService;

  private final ObjectMapper objectMapper = new ObjectMapper();
  UUID adminId = UUID.randomUUID();
  AdminEntity adminEntity = new AdminEntity(adminId, false, "ACTIVE", adminId, adminId);
  Admin admin = new Admin(adminId, false, "ACTIVE", false, "Admin@test.com");

  @Test
  public void testGetCurrentAdmin() throws JsonProcessingException {
    when(jedisPool.getResource()).thenReturn(jedis);
    when(authHelpers.getCurrentUserId()).thenReturn(adminId);
    when(adminRepository.findById(adminId)).thenReturn(Optional.of(adminEntity));

    adminEntity.setUser(new UserEntity(adminId, "Admin@test.com", "Name"));

    Admin currentAdmin = adminService.getCurrentAdmin();
    assertEquals(admin.getUserId(), currentAdmin.getUserId());
    assertEquals(admin.getEmail(), currentAdmin.getEmail());
    assertEquals(admin.getStatus(), currentAdmin.getStatus());
    assertEquals(admin.isSuperAdmin(), currentAdmin.isSuperAdmin());
    assertEquals(admin.getIsDeleted(), currentAdmin.getIsDeleted());

    verify(jedis).setex("admin:" + adminId, 600, mockObjectMapper.writeValueAsString(admin));
  }

  @Test
  public void testGetCurrentAdminNull() {
    when(jedisPool.getResource()).thenReturn(jedis);
    when(authHelpers.getCurrentUserId()).thenReturn(adminId);
    when(adminRepository.findById(adminId)).thenReturn(Optional.empty());

    Admin currentAdmin = adminService.getCurrentAdmin();
    assertNull(currentAdmin);
  }

  @Test
  public void testGetCurrentAdminNoUserId() {
    when(authHelpers.getCurrentUserId()).thenReturn(null);

    Admin currentAdmin = adminService.getCurrentAdmin();
    assertNull(currentAdmin);
  }

  @Test
  public void testGetCurrentAdminFromCache() throws JsonProcessingException {
    when(jedisPool.getResource()).thenReturn(jedis);
    when(authHelpers.getCurrentUserId()).thenReturn(adminId);

    when(jedis.get("admin:" + adminId)).thenReturn(objectMapper.writeValueAsString(admin));
    when(mockObjectMapper.readValue(objectMapper.writeValueAsString(admin), Admin.class)).thenReturn(admin);

    Admin currentAdmin = adminService.getCurrentAdmin();
    assertEquals(admin.getUserId(), currentAdmin.getUserId());
    assertEquals(admin.getEmail(), currentAdmin.getEmail());
    assertEquals(admin.getStatus(), currentAdmin.getStatus());
    assertEquals(admin.isSuperAdmin(), currentAdmin.isSuperAdmin());
    assertEquals(admin.getIsDeleted(), currentAdmin.getIsDeleted());
  }

  @Test
  public void testGetCurrentAdminCacheError() {
    when(authHelpers.getCurrentUserId()).thenReturn(adminId);
    when(jedisPool.getResource()).thenThrow(new RuntimeException("Redis error"));

    Admin currentAdmin = adminService.getCurrentAdmin();
    assertNull(currentAdmin);
    verify(jedis, never()).get("admin:" + adminId);
    verify(adminRepository, never()).findById(adminId);
  }

}

