package backend.AdminTests.ServiceTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import backend.admin.dto.UserStats;
import backend.admin.services.AdminService;
import backend.common.exceptions.UnauthorisedException;
import backend.common.helpers.AuthHelpers;
import backend.permissions.authorizers.AdminAuthorizer;
import backend.permissions.types.Actions;
import backend.permissions.types.AdminViewTypes;
import backend.permissions.types.Resources;
import backend.users.repositories.UserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class GetUserStatsTests {

  @Mock
  private UserRepository userRepository;

  @Mock
  private AuthHelpers authHelpers;

  @Mock
  private AdminAuthorizer adminAuthorizer;

  @InjectMocks
  private AdminService adminService;

  @Test
  public void getUserStatsWithData() {
    when(authHelpers.getCurrentUserId()).thenReturn(UUID.randomUUID());
    when(adminAuthorizer.authorize(
            any(UUID.class),
            eq(Resources.USERS),
            eq(Actions.READ),
            eq(AdminViewTypes.ALL)
    )).thenReturn(true);

    List<Object[]> mockResults = new ArrayList<>();
    Object[] row = {10L, 5L, 2L};
    mockResults.add(row);

    when(userRepository.getAdminUserStats()).thenReturn(mockResults);

    UserStats userStats = adminService.getUserStats();

    assertEquals(10, userStats.getTotal());
    assertEquals(5, userStats.getNewUserTotal());
    assertEquals(2, userStats.getDeletedUserTotal());
  }

  @Test
  public void getUserStatsEmptyData() {
    when(authHelpers.getCurrentUserId()).thenReturn(UUID.randomUUID());
    when(adminAuthorizer.authorize(
            any(UUID.class),
            eq(Resources.USERS),
            eq(Actions.READ),
            eq(AdminViewTypes.ALL)
    )).thenReturn(true);

    when(userRepository.getAdminUserStats()).thenReturn(new ArrayList<>());

    UserStats userStats = adminService.getUserStats();

    assertEquals(0, userStats.getTotal());
    assertEquals(0, userStats.getNewUserTotal());
    assertEquals(0, userStats.getDeletedUserTotal());
  }

  @Test
  public void getUserStatsNullResults() {
    when(authHelpers.getCurrentUserId()).thenReturn(UUID.randomUUID());
    when(adminAuthorizer.authorize(
            any(UUID.class),
            eq(Resources.USERS),
            eq(Actions.READ),
            eq(AdminViewTypes.ALL)
    )).thenReturn(true);

    when(userRepository.getAdminUserStats()).thenReturn(null);

    UserStats userStats = adminService.getUserStats();

    assertEquals(0, userStats.getTotal());
    assertEquals(0, userStats.getNewUserTotal());
    assertEquals(0, userStats.getDeletedUserTotal());
  }

  @Test
  public void getUserStatsNoPermission() {
    when(authHelpers.getCurrentUserId()).thenReturn(UUID.randomUUID());
    when(adminAuthorizer.authorize(
            any(UUID.class),
            eq(Resources.USERS),
            eq(Actions.READ),
            eq(AdminViewTypes.ALL)
    )).thenReturn(false);

    UnauthorisedException exception = assertThrows(UnauthorisedException.class, () -> {
      adminService.getUserStats();
    });

    assert exception.getMessage().equals("User does not have permission to view user stats");
  }
}
