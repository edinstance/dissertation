package backend.CommonTests.DynamodbTests.TableTests;

import backend.common.dynamodb.tables.AdminLogs;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AdminLogsTests {

  private AdminLogs adminLogs;

  @Test
  public void testDefaultConstructor() {
    adminLogs = new AdminLogs();
    assertNotNull(adminLogs);
  }

  @Test
  public void testConstructor() {
    adminLogs = new AdminLogs(UUID.randomUUID(), UUID.randomUUID(), "Query", "query");

    assertNotNull(adminLogs);
    assertNotNull(adminLogs.getAdminId());
    assertNotNull(adminLogs.getLogId());
    assertNotNull(adminLogs.getQueryType());
    assertNotNull(adminLogs.getQueryName());
  }

  @BeforeEach
  public void setUp() {
    adminLogs = new AdminLogs();
  }

  @Test
  public void testAdminIdMethods() {
    UUID adminId = UUID.randomUUID();
    adminLogs.setAdminId(adminId);
    assertEquals(adminId, adminLogs.getAdminId());
  }

  @Test
  public void testTimestampMethods() {
    adminLogs.setTimestamp("2023-10-01T00:00:00Z");
    assertEquals("2023-10-01T00:00:00Z", adminLogs.getTimestamp());
  }

  @Test
  public void testLogIdMethods() {
    UUID logId = UUID.randomUUID();
    adminLogs.setLogId(logId);
    assertEquals(logId, adminLogs.getLogId());
  }

  @Test
  public void testSuccessMethods() {
    adminLogs.setSuccess(true);
    assertEquals(true, adminLogs.getSuccess());
  }

  @Test
  public void testQueryTypeMethods() {
    adminLogs.setQueryType("QueryType");
    assertEquals("QueryType", adminLogs.getQueryType());
  }

  @Test
  public void testQueryNameMethods() {
    adminLogs.setQueryName("QueryName");
    assertEquals("QueryName", adminLogs.getQueryName());
  }

  @Test
  public void testIpAddressMethods() {
    adminLogs.setIpAddress("IPAddress");
    assertEquals("IPAddress", adminLogs.getIpAddress());
  }

  @Test
  public void testUserAgentMethods() {
    adminLogs.setUserAgent("UserAgent");
    assertEquals("UserAgent", adminLogs.getUserAgent());
  }

  @Test
  public void testTtlTimestampMethods() {
    adminLogs.setTtlTimestamp(123456789L);
    assertEquals(123456789L, adminLogs.getTtlTimestamp());
  }
}
