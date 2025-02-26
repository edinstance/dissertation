package com.finalproject.backend.UserTests.MutationTests;

import com.finalproject.backend.common.dto.MutationResponse;
import com.finalproject.backend.users.dto.UserInput;
import com.finalproject.backend.users.entities.UserEntity;
import com.finalproject.backend.users.mappers.UserMapper;
import com.finalproject.backend.users.mutations.UserMutations;
import com.finalproject.backend.users.services.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
}
