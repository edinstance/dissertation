package backend.users.mappers;


import backend.users.dto.UserInput;
import backend.users.entities.UserEntity;
import org.springframework.stereotype.Component;


/**
 * Mapper for converting between UserInputDTO and User entity.
 */
@Component
public class UserMapper {


  /**
   * Maps UserInputDTO to UserEntity.
   *
   * @param userInput The input to map.
   * @return UserEntity The user that is returned.
   */
  public UserEntity mapInputToUser(final UserInput userInput) {
    return new UserEntity(userInput.getId(),
            userInput.getEmail(), userInput.getName());
  }

}
