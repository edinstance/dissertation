package backend.UserTests.MapperTests;

import backend.users.dto.UserInput;
import backend.users.entities.UserEntity;
import backend.users.mappers.UserMapper;
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
