package com.finalproject.backend.dtoTests;

import com.finalproject.backend.dto.DeleteResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DeleteResponseTests {

  private DeleteResponse deleteResponse;

  @Test
  public void testConstructor() {
    deleteResponse = new DeleteResponse(true, "test message");
    assertNotNull(deleteResponse);
  }

  @BeforeEach
  public void setUp() {
    deleteResponse = new DeleteResponse(true, "test message");
  }

  @Test
  public void testSuccessFunctions() {
    deleteResponse.setSuccess(false);
    assert !deleteResponse.isSuccess();
  }

  @Test
  public void testMessageFunctions() {
    deleteResponse.setMessage("new message");
    assert deleteResponse.getMessage().equals("new message");
  }

}
