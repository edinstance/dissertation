package backend.AdminTests.MapperTests;

import backend.admin.dto.Admin;
import backend.admin.entities.AdminEntity;
import backend.admin.mappers.AdminMapper;
import backend.users.entities.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class AdminMapperTests {

  private AdminEntity adminEntity;

  @BeforeEach
  public void setUp() {
    UUID adminId = UUID.randomUUID();
    adminEntity = new AdminEntity(adminId, true, "ACTIVE", UUID.randomUUID(), UUID.randomUUID());

    adminEntity.setUser(new UserEntity(UUID.randomUUID(), "admin@test.com", "admin"));
  }

  @Test
  public void testMapAdminEntityToAdmin() {
    Admin result = AdminMapper.mapAdminEntityToAdmin(adminEntity);
    assertEquals(adminEntity.getUserId(), result.getUserId());
    assertEquals(adminEntity.isSuperAdmin(), result.isSuperAdmin());
    assertEquals(adminEntity.getStatus(), result.getStatus());
    assertEquals(adminEntity.getIsDeleted(), result.getIsDeleted());
    assertEquals(adminEntity.getUser().getEmail(), result.getEmail());
  }

  @Test
  public void testMapNullEntity() {
    assertNull(AdminMapper.mapAdminEntityToAdmin(null));
  }

  @Test
  public void testMapNullUser() {
    adminEntity.setUser(null);
    Admin result = AdminMapper.mapAdminEntityToAdmin(adminEntity);
    assertNull(result.getEmail());
  }

  @Test
  public void testMapAdminEntityListToAdmins() {
    List<Admin> admins = AdminMapper.mapAdminEntityListToAdmins(List.of(adminEntity));
    assertEquals(1, admins.size());
    Admin result = admins.getFirst();
    assertEquals(adminEntity.getUserId(), result.getUserId());
    assertEquals(adminEntity.isSuperAdmin(), result.isSuperAdmin());
    assertEquals(adminEntity.getStatus(), result.getStatus());
    assertEquals(adminEntity.getIsDeleted(), result.getIsDeleted());
    assertEquals(adminEntity.getUser().getEmail(), result.getEmail());
  }

  @Test
  public void testMapEmptyList() {
    List<Admin> admins = AdminMapper.mapAdminEntityListToAdmins(List.of());
    assertEquals(0, admins.size());
  }

  @Test
  public void testMapNullList() {
    List<Admin> admins = AdminMapper.mapAdminEntityListToAdmins(null);
    assertEquals(0, admins.size());
  }

}
