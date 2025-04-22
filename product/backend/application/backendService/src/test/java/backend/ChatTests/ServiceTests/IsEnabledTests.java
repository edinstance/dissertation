package backend.ChatTests.ServiceTests;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

public class IsEnabledTests extends SetupChatServiceTests {

  @Test
  public void testIsEnabled() {
    ReflectionTestUtils.setField(chatService, "isEnabled", true);

    Boolean isEnabled = chatService.isEnabled();

    assertTrue(isEnabled);
  }

  @Test
  public void testIsNotEnabled() {
    ReflectionTestUtils.setField(chatService, "isEnabled", false);

    Boolean isEnabled = chatService.isEnabled();

    assertFalse(isEnabled);
  }
}
