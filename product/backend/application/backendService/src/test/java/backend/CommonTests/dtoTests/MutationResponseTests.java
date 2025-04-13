package backend.CommonTests.dtoTests;

import backend.common.dto.MutationResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class MutationResponseTests {

  private MutationResponse mutationResponse;

  @Test
  public void testConstructor() {
    mutationResponse = new MutationResponse(true, "test message");
    assertNotNull(mutationResponse);
  }

  @BeforeEach
  public void setUp() {
    mutationResponse = new MutationResponse(true, "test message");
  }

  @Test
  public void testSuccessFunctions() {
    mutationResponse.setSuccess(false);
    assert !mutationResponse.isSuccess();
  }

  @Test
  public void testMessageFunctions() {
    mutationResponse.setMessage("new message");
    assert mutationResponse.getMessage().equals("new message");
  }

}
