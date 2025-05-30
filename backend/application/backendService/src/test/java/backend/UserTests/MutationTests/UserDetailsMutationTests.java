package backend.UserTests.MutationTests;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import backend.users.dto.UserDetailsInput;
import backend.users.entities.UserDetailsEntity;
import backend.users.entities.UserEntity;
import backend.users.mappers.UserDetailsMapper;
import backend.users.mutations.UserDetailsMutations;
import backend.users.services.UserDetailsService;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserDetailsMutationTests {

  @Mock
  private UserDetailsService userDetailsService;

  @Mock
  private UserDetailsMapper userDetailsMapper;

  @InjectMocks
  private UserDetailsMutations userDetailsMutations;


  @Test
  public void testUserMutation() {
    UUID userId = UUID.randomUUID();
    String email = "test@test.com";
    String name = "Test User";


    UserDetailsEntity userDetailsEntity = new UserDetailsEntity(userId, "contact",
            "name", "street", "city",
            "county", "postcode");
    UserEntity userEntity = new UserEntity(userId, email, name);
    userEntity.setUserDetailsEntity(userDetailsEntity);

    when(userDetailsMapper.mapInputToDetails(any(UUID.class), any(UserDetailsInput.class)))
            .thenReturn(userDetailsEntity);

    when(userDetailsService.saveUserDetails(any(UserDetailsEntity.class))).thenReturn(userEntity);


    UserEntity result = userDetailsMutations.saveUserDetails(userId.toString(), new UserDetailsInput("contact",
            "name", "street", "city", "county", "postcode"));

    assertNotNull(result);
    assert result.getId().equals(userId);
    assert result.getEmail().equals(email);
    assert result.getName().equals(name);
    assert result.getStatus().equals("PENDING");

    UserDetailsEntity detailsResult = result.getUserDetailsEntity();

    assertNotNull(detailsResult);
    assert detailsResult.getId().equals(userId);
    assert detailsResult.getContactNumber().equals(userDetailsEntity.getContactNumber());
    assert detailsResult.getHouseName().equals(userDetailsEntity.getHouseName());
    assert detailsResult.getAddressStreet().equals(userDetailsEntity.getAddressStreet());
    assert detailsResult.getAddressCity().equals(userDetailsEntity.getAddressCity());
    assert detailsResult.getAddressCounty().equals(userDetailsEntity.getAddressCounty());
    assert detailsResult.getAddressPostcode().equals(userDetailsEntity.getAddressPostcode());

    verify(userDetailsService).saveUserDetails(userDetailsEntity);

  }


}
