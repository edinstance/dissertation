package com.finalproject.backend.reporting.services;

import com.finalproject.backend.common.config.logging.AppLogger;
import com.finalproject.backend.common.helpers.AuthHelpers;
import com.finalproject.backend.reporting.config.jira.JiraClient;
import com.finalproject.backend.users.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * This class is used for handling reporting.
 */
@Service
public class ReportingService {

  /**
   * The jira client to use for reporting.
   */
  private final JiraClient jiraClient;

  /**
   * The authentication helpers for getting authentication information.
   */
  private final AuthHelpers authHelpers;

  /**
   * The user service to use for interacting with users.
   */
  private final UserService userService;

  /**
   * Constructor for setting up the reporting service.
   *
   * @param jiraClient  the jira client to use.
   * @param authHelpers the authHelpers to use.
   * @param userService the userService to use.
   */
  @Autowired
  public ReportingService(JiraClient jiraClient, AuthHelpers authHelpers,
                          UserService userService) {
    this.jiraClient = jiraClient;
    this.authHelpers = authHelpers;
    this.userService = userService;
  }

  /**
   * This method reports bugs in jira.
   *
   * @param title       the title of the bug.
   * @param description the description of the bug.
   */
  public String reportBug(final String title, final String description) {
    try {
      jiraClient.createBug(title, userService.getUserById(authHelpers.getCurrentUserId()),
              description);
      return "Success";
    } catch (Exception e) {
      AppLogger.error("Error creating bug", e);
      return e.getMessage();
    }
  }

}
