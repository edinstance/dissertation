package com.finalproject.backend.CommonTests.Exceptions;

import com.finalproject.backend.common.exceptions.UnauthorisedException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UnauthorisedExceptionTests {

  @Test
  public void testDefaultConstructor() {
    UnauthorisedException exception = new UnauthorisedException();
    assertNull(exception.getMessage());
    assertNull(exception.getCause());
  }

  @Test
  public void testMessageConstructor() {
    String errorMessage = "Access denied to this resource";
    UnauthorisedException exception = new UnauthorisedException(errorMessage);

    assertEquals(errorMessage, exception.getMessage());
    assertNull(exception.getCause());
  }

  @Test
  public void testCauseConstructor() {
    Throwable cause = new IllegalArgumentException("Invalid argument");
    UnauthorisedException exception = new UnauthorisedException(cause);

    assertEquals(cause.toString(), exception.getMessage());
    assertEquals(cause, exception.getCause());
  }

  @Test
  public void testMessageAndCauseConstructor() {
    String errorMessage = "User is not authorized";
    Throwable cause = new IllegalStateException("User not logged in");
    UnauthorisedException exception = new UnauthorisedException(errorMessage, cause);

    assertEquals(errorMessage, exception.getMessage());
    assertEquals(cause, exception.getCause());
  }

}