package backend.AdminTests.QueryTests;

import backend.admin.queries.AdminQueries;
import backend.admin.services.AdminService;
import backend.users.entities.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GetAllUsersTests {

  @Mock
  private AdminService adminService;

  @InjectMocks
  private AdminQueries adminQueries;

  @Test
  void testGetAllUsers() {
    UserEntity userEntity = new UserEntity(UUID.randomUUID(), "test@test.com", "name");
    when(adminService.getAllUsers()).thenReturn(List.of(userEntity));

    List<UserEntity> users = adminQueries.getAllUsers();

    assertNotNull(users);
    assert users.size() == 1;
    assert users.contains(userEntity);
  }
}
