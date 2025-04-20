package backend.UserTests.QueryTests;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import backend.users.queries.UserDetailsQueries;
import backend.users.services.UserDetailsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserDetailsQueryTests {

  @Mock
  private UserDetailsService userDetailsService;


  @InjectMocks
  private UserDetailsQueries userDetailsQueries;

  @Test
  void testCheckCurrentUserDetailsExist() {
    when(userDetailsService.checkCurrentUserDetailsExist()).thenReturn(false);
    assertFalse(userDetailsQueries.checkCurrentUserDetailsExist());

    when(userDetailsService.checkCurrentUserDetailsExist()).thenReturn(true);
    assertTrue(userDetailsQueries.checkCurrentUserDetailsExist());

  }
}
