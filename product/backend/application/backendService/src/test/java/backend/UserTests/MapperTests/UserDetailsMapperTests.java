package backend.UserTests.MapperTests;

import backend.users.dto.UserDetailsInput;
import backend.users.entities.UserDetailsEntity;
import backend.users.mappers.UserDetailsMapper;
import org.junit.jupiter.api.Test;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UserDetailsMapperTests {

  private final UserDetailsMapper userDetailsMapper = new UserDetailsMapper();

  @Test
  public void testToEntity() {
    UUID userId = UUID.randomUUID();

    UserDetailsInput userDetailsInput = new UserDetailsInput("contact", "name", "street",
            "city", "county", "postcode");

    UserDetailsEntity userDetails = userDetailsMapper.mapInputToDetails(userId, userDetailsInput);

    assertNotNull(userDetails);
    assertEquals(userId, userDetails.getId());
    assertEquals(userDetailsInput.getContactNumber(), userDetails.getContactNumber());
    assertEquals(userDetailsInput.getHouseName(), userDetails.getHouseName());
    assertEquals(userDetailsInput.getAddressStreet(), userDetails.getAddressStreet());
    assertEquals(userDetailsInput.getAddressCity(), userDetails.getAddressCity());
    assertEquals(userDetailsInput.getAddressCounty(), userDetails.getAddressCounty());
    assertEquals(userDetailsInput.getAddressPostcode(), userDetails.getAddressPostcode());
  }
}