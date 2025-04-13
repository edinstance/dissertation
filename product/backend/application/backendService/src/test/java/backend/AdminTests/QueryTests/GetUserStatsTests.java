package backend.AdminTests.QueryTests;

import backend.admin.dto.UserStats;
import backend.admin.queries.AdminQueries;
import backend.admin.services.AdminService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

/**
 * Unit tests for the UserQueries class.
 */
@ExtendWith(MockitoExtension.class)
public class GetUserStatsTests {

  @Mock
  private AdminService adminService;

  @InjectMocks
  private AdminQueries adminQueries;

  @Test
  void testGetUserStats() {
    when(adminService.getUserStats()).thenReturn(new UserStats(1L, 2L, 3L));

    UserStats userStats = adminQueries.getUserStats();

    assertNotNull(userStats);
    assert userStats.getTotal() == 1L;
    assert userStats.getNewUserTotal() == 2L;
    assert userStats.getDeletedUserTotal() == 3L;
  }
}
