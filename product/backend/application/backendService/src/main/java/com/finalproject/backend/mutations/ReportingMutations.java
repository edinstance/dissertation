package com.finalproject.backend.mutations;

import com.finalproject.backend.dto.MutationResponse;
import com.finalproject.backend.services.ReportingService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.InputArgument;

/**
 * This class contains the mutations for reports.
 */
@DgsComponent
public class ReportingMutations {

  /**
   * This is the service the mutation interacts with.
   */
  private final ReportingService reportingService;

  /**
   * Constructor for initializing the mutation class.
   *
   * @param inputReportingService The service to use.
   */
  public ReportingMutations(ReportingService inputReportingService) {
    this.reportingService = inputReportingService;
  }

  /**
   * This mutation reports a bug.
   *
   * @param title the title of the bug.
   * @param description the description of the bug.
   */
  @DgsMutation
  public MutationResponse reportBug(@InputArgument String title,
                                    @InputArgument String description) {
    String result = reportingService.reportBug(title, description);

    if (result.equals("Success")) {
      return new MutationResponse(true, "Bug with title: " +
              title + " has been reported successfully.");
    }
    return new MutationResponse(false, title + ": " + result);
  }
}
