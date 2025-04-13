package backend.ReportingTests.ServiceTests;

import backend.common.helpers.AuthHelpers;
import backend.reporting.config.jira.JiraClient;
import backend.reporting.services.ReportingService;
import backend.users.entities.UserEntity;
import backend.users.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.UUID;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReportingServiceTests {


  @Mock
  private JiraClient jiraClient;

  @Mock
  private AuthHelpers authHelpers;

  @Mock
  private UserService userService;

  private ReportingService reportingService;

  @BeforeEach
  void setUp() {
    reportingService = new ReportingService(jiraClient, authHelpers, userService);
  }

  @Test
  void reportBugTest() throws Exception {
    UUID userId = UUID.randomUUID();
    UserEntity newUser = new UserEntity(userId, "new@test.com", "New User");

    when(authHelpers.getCurrentUserId()).thenReturn(userId);
    when(userService.getUserById(userId)).thenReturn(newUser);

    String result = reportingService.reportBug("Title", "Description");

    assert result.equals("Success");

    verify(authHelpers).getCurrentUserId();
    verify(userService).getUserById(userId);
    verify(jiraClient).createBug("Title", newUser, "Description");
  }

  @Test
  void reportBugError() throws Exception {
    UUID userId = UUID.randomUUID();
    UserEntity newUser = new UserEntity(userId, "new@test.com", "New User");

    when(authHelpers.getCurrentUserId()).thenReturn(userId);
    when(userService.getUserById(userId)).thenReturn(newUser);

    doThrow(new Exception("Jira error")).when(jiraClient)
            .createBug(anyString(), any(UserEntity.class), anyString());

    String result = reportingService.reportBug("Title", "Description");

    assert result.equals("Jira error");
  }
}