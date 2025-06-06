package backend.UserTests.MutationTests;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import backend.common.dto.MutationResponse;
import backend.users.dto.UserInput;
import backend.users.entities.UserEntity;
import backend.users.mappers.UserMapper;
import backend.users.mutations.UserMutations;
import backend.users.services.UserService;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserMutationTests {

  @Mock
  private UserService userService;

  @Mock
  private UserMapper userMapper;

  @InjectMocks
  private UserMutations userMutations;


  @Test
  public void testCreateUserMutation() {
    UUID userId = UUID.randomUUID();
    String email = "test@test.com";
    String name = "Test User";

    UserInput userInput = new UserInput(userId, email, name);

    UserEntity userEntity = new UserEntity(userId, email, name);

    when(userMapper.mapInputToUser(any(UserInput.class))).thenReturn(userEntity);
    when(userService.createUser(any(UserEntity.class))).thenReturn(userEntity);


    UserEntity result = userMutations.createUser(userInput);

    assertNotNull(result);
    assert result.getId().equals(userId);
    assert result.getEmail().equals(email);
    assert result.getName().equals(name);
    assert result.getStatus().equals("PENDING");

    verify(userService).createUser(userEntity);

  }

  @Test
  public void testDeleteUserSuccess() {
    when(userService.deleteUser()).thenReturn(true);

    MutationResponse response = userMutations.deleteUser();

    assertNotNull(response);
    assert response.isSuccess();
    assert response.getMessage().equals("User deleted successfully");
    verify(userService).deleteUser();
  }

  @Test
  public void testDeleteUserFailure() {
    when(userService.deleteUser()).thenReturn(false);

    MutationResponse response = userMutations.deleteUser();

    assertNotNull(response);
    assert !response.isSuccess();
    assert response.getMessage().equals("User deletion failed");
    verify(userService).deleteUser();
  }

  @Test
  public void testDeactivateUser() {
    UUID userId = UUID.randomUUID();

    userMutations.deactivateUser(userId.toString());

    verify(userService).deactivateUser(userId);
  }
}
