package com.finalproject.backend.context;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UserContextTests {

  private UserContext userContext;

  @Test
  public void testConstructor() {

    userContext = new UserContext(UUID.randomUUID()
            .toString()
    );

    assertNotNull(userContext);
  }

  @Test
  public void testGetter() {
    String userId = UUID.randomUUID().toString();

    userContext = new UserContext(userId);

    assertNotNull(userContext);
    assert userContext.getUserId().equals(userId);
  }

}
