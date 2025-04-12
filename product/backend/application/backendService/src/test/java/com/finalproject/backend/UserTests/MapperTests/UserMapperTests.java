package com.finalproject.backend.UserTests.MapperTests;

import com.finalproject.backend.users.dto.UserInput;
import com.finalproject.backend.users.entities.UserEntity;
import com.finalproject.backend.users.mappers.UserMapper;
import org.junit.jupiter.api.Test;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UserMapperTests {

  private final UserMapper userMapper = new UserMapper();

  @Test
  public void testMapInputToUser() {
    UUID userId = UUID.randomUUID();

    UserInput userInput = new UserInput(userId, "test@test.com", "test");
    UserEntity userEntity = userMapper.mapInputToUser(userInput);

    assertNotNull(userEntity);
    assertEquals(userId, userEntity.getId());
    assertEquals("test@test.com", userEntity.getEmail());
    assertEquals("test", userEntity.getName());

  }

}
