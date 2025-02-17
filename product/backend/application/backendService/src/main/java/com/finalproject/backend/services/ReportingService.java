package com.finalproject.backend.services;

import com.finalproject.backend.config.jira.JiraClient;
import com.finalproject.backend.helpers.AuthHelpers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportingService {

  private final JiraClient jiraClient;

  private final AuthHelpers authHelpers;

  private final UserService userService;

  @Autowired
  public ReportingService(JiraClient jiraClient, AuthHelpers authHelpers, UserService userService) {
    this.jiraClient = jiraClient;
    this.authHelpers = authHelpers;
    this.userService = userService;
  }


  public void reportBug(final String title, final String description) {
    jiraClient.createBug(title, userService.getUserById(authHelpers.getCurrentUserId()), description);
  }


}
