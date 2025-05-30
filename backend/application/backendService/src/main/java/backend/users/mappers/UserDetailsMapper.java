package backend.users.mappers;

import backend.users.dto.UserDetailsInput;
import backend.users.entities.UserDetailsEntity;
import java.util.UUID;
import org.springframework.stereotype.Component;

/**
 * Mapper for converting between UserDetailsInputDTO and User details entity.
 */
@Component
public class UserDetailsMapper {

  /**
   * Maps UserDetailsInputDTTO to UserDetailsEntity.
   *
   * @param id    The id of the user.
   * @param input The user details.
   *
   * @return UserDetailsEntity The user details that are returned.
   */
  public UserDetailsEntity mapInputToDetails(UUID id, UserDetailsInput input) {
    return new UserDetailsEntity(
            id,
            input.getContactNumber(),
            input.getHouseName(),
            input.getAddressStreet(),
            input.getAddressCity(),
            input.getAddressCounty(),
            input.getAddressPostcode()
    );
  }
}