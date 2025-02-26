package com.finalproject.backend.UserTests.HelperTests;

import com.finalproject.backend.users.entities.UserEntity;
import com.finalproject.backend.users.helpers.UserHelpers;
import com.finalproject.backend.users.services.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.mockito.Mockito.when;

@SpringBootTest
public class UserHelpersTests {

  @Mock
  private UserService userService;

  @InjectMocks
  private UserHelpers userHelpers;

  @Test
  public void testGetUserByIdHelper() {

    UserEntity userEntity = new UserEntity(UUID.randomUUID(),
            "email", "name", "PENDING");

    when(userService.getUserById(userEntity.getId())).thenReturn(userEntity);

    UserEntity foundUser = userHelpers.getUserById(userEntity.getId());

    assert foundUser.getId().equals(userEntity.getId());
    assert foundUser.getEmail().equals(userEntity.getEmail());
    assert foundUser.getName().equals(userEntity.getName());
    assert foundUser.getStatus().equals(userEntity.getStatus());

  }
}
