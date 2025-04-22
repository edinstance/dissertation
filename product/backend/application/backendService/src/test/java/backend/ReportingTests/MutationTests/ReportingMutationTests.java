package backend.ReportingTests.MutationTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import backend.common.dto.MutationResponse;
import backend.reporting.mutations.ReportingMutations;
import backend.reporting.services.ReportingService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ReportingMutationTests {

  @Mock
  private ReportingService reportingService;

  @InjectMocks
  private ReportingMutations reportingMutations;

  @Test
  public void testReportBugSuccess() {

    String title = "Bug";
    String description = "Description";

    when(reportingService.reportBug(title, description)).thenReturn("Success");


    MutationResponse response = reportingMutations.reportBug(title, description);

    assert response.isSuccess();
    assert response.getMessage().equals("Bug with title: " + title + " has been reported successfully.");

    verify(reportingService, times(1)).reportBug(title, description);
  }

  @Test
  public void testReportBugException() {
    String title = "Bug";
    String description = "Description";
    String errorMessage = "Jira error";

    when(reportingService.reportBug(title, description)).thenReturn(errorMessage);

    MutationResponse response = reportingMutations.reportBug(title, description);

    assertFalse(response.isSuccess());
    assertEquals(title + ": " + errorMessage, response.getMessage());
    verify(reportingService, times(1)).reportBug(title, description);
  }
}

