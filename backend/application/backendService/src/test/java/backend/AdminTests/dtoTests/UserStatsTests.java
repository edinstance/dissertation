package backend.AdminTests.dtoTests;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import backend.admin.dto.UserStats;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserStatsTests {

  private UserStats userStats;

  @Test
  public void userStatsDefaultConstructorTest() {
    userStats = new UserStats();
    assertNotNull(userStats);
  }

  @Test
  public void userStatsonstructorTest() {
    userStats = new UserStats(1, 1, 1);

    assertNotNull(userStats);
    assert userStats.getNewUserTotal() == 1;
    assert userStats.getDeletedUserTotal() == 1;
    assert userStats.getTotal() == 1;
  }

  @BeforeEach
  public void setUp() {
    userStats = new UserStats(1, 1, 1);
  }

  @Test
  public void userStatsTotalMethodsTests() {
    userStats.setTotal(5);

    assert userStats.getTotal() == 5;
  }

  @Test
  public void userStatsNewUserTotalMethodsTests() {
    userStats.setNewUserTotal(5);

    assert userStats.getNewUserTotal() == 5;
  }

  @Test
  public void userStatsDeletedUserTotalMethodsTests() {
    userStats.setDeletedUserTotal(5);

    assert userStats.getDeletedUserTotal() == 5;
  }
}
