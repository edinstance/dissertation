package backend.BidTests.ServiceTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import backend.bids.services.RedisLeaderElectionService;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.atomic.AtomicBoolean;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisException;
import redis.clients.jedis.params.SetParams;

@ExtendWith(MockitoExtension.class)
class TestRedisLeaderElectionService {

  @Mock
  private JedisPool jedisPool;

  @Mock
  private Jedis jedis;

  @InjectMocks
  private RedisLeaderElectionService leaderElectionService;

  private final String instanceId = "test-instance-id";
  private final String leaderKey = "auction:service:leader";

  @BeforeEach
  public void setUp() {
    leaderElectionService = new RedisLeaderElectionService(jedisPool);
    ReflectionTestUtils.setField(leaderElectionService, "instanceId", instanceId);
  }

  @Test
  public void testAttemptToAcquireLeadershipSuccessfully() {
    Jedis jedis = mock(Jedis.class);
    when(jedisPool.getResource()).thenReturn(jedis);
    when(jedis.set(eq(leaderKey), eq(instanceId), any(SetParams.class))).thenReturn("OK");

    leaderElectionService.leadershipHeartbeat();

    assertTrue(leaderElectionService.isLeader());
    verify(jedis, times(1)).set(eq(leaderKey), eq(instanceId), any(SetParams.class));
  }

  @Test
  public void testAttemptToAcquireLeadershipFailed() {
    Jedis jedis = mock(Jedis.class);
    when(jedisPool.getResource()).thenReturn(jedis);
    when(jedis.set(eq(leaderKey), eq(instanceId), any(SetParams.class))).thenReturn(null);

    leaderElectionService.leadershipHeartbeat();

    assertFalse(leaderElectionService.isLeader());
    verify(jedis, times(1)).set(eq(leaderKey), eq(instanceId), any(SetParams.class));
  }

  @Test
  public void testLeadershipRenewalSuccessfully() {
    Jedis jedis = mock(Jedis.class);
    when(jedisPool.getResource()).thenReturn(jedis);
    when(jedis.set(eq(leaderKey), eq(instanceId), any(SetParams.class))).thenReturn("OK");
    when(jedis.get(eq(leaderKey))).thenReturn(instanceId);
    when(jedis.expire(eq(leaderKey), eq((long) 30))).thenReturn(1L);

    leaderElectionService.leadershipHeartbeat();
    assertTrue(leaderElectionService.isLeader());

    leaderElectionService.leadershipHeartbeat();

    int leaderExpirySeconds = 30;
    verify(jedis, times(1)).expire(eq(leaderKey), eq((long) leaderExpirySeconds));
  }

  @Test
  public void testLeadershipLost() {
    Jedis jedis = mock(Jedis.class);
    when(jedisPool.getResource()).thenReturn(jedis);
    when(jedis.set(eq(leaderKey), eq(instanceId), any(SetParams.class))).thenReturn("OK");

    leaderElectionService.leadershipHeartbeat();
    assertTrue(leaderElectionService.isLeader());

    when(jedis.get(eq(leaderKey))).thenReturn("another-instance-id");
    leaderElectionService.leadershipHeartbeat();

    assertFalse(leaderElectionService.isLeader());
  }

  @Test
  public void testJedisExceptionDuringAcquisition() {
    when(jedisPool.getResource()).thenThrow(new JedisException("Connection failed"));

    leaderElectionService.leadershipHeartbeat();
    assertFalse(leaderElectionService.isLeader());
  }

  @Test
  void testRelinquishesLeadership() {
    ReflectionTestUtils.setField(
            leaderElectionService, "isLeader", new AtomicBoolean(true));

    leaderElectionService.leadershipHeartbeat();

    assertFalse(leaderElectionService.isLeader());
    verify(jedis, never()).del(eq(leaderKey));
  }

  @Test
  public void testShutdownReleasesLeadership() {
    Jedis jedis = mock(Jedis.class);
    when(jedisPool.getResource()).thenReturn(jedis);
    when(jedis.set(eq(leaderKey), eq(instanceId), any(SetParams.class))).thenReturn("OK");

    leaderElectionService.leadershipHeartbeat();

    when(jedis.get(eq(leaderKey))).thenReturn(instanceId);
    leaderElectionService.shutdown();

    verify(jedis, times(1)).del(eq(leaderKey));
  }

  @Test
  void testGetHostInfoSuccess() {
    InetAddress mockInetAddress =
            when(mock(InetAddress.class).getHostName()).thenReturn("test-hostname").getMock();

    try (MockedStatic<InetAddress> inetAddressMockedStatic = mockStatic(InetAddress.class)) {
      inetAddressMockedStatic.when(InetAddress::getLocalHost).thenReturn(mockInetAddress);

      String hostInfo = leaderElectionService.getHostInfo();

      assertEquals("test-hostname", hostInfo);
    }
  }

  @Test
  void testGetHostInfoException() {
    try (MockedStatic<InetAddress> inetAddressMockedStatic = mockStatic(InetAddress.class)) {
      inetAddressMockedStatic.when(InetAddress::getLocalHost).thenThrow(new UnknownHostException());

      String hostInfo = leaderElectionService.getHostInfo();

      assertEquals("unknown-host", hostInfo);
    }
  }

  @Test
  void shutdownNotLeader() {
    ReflectionTestUtils.setField(leaderElectionService, "isLeader", new AtomicBoolean(false));

    leaderElectionService.shutdown();

    verify(jedisPool, never()).getResource();
  }

  @Test
  void shutdownInstanceNotLeader() {
    ReflectionTestUtils.setField(leaderElectionService, "isLeader", new AtomicBoolean(true));
    when(jedisPool.getResource()).thenReturn(jedis);

    leaderElectionService.shutdown();

    verify(jedis, never()).del(eq(leaderKey));
  }

  @Test
  void shutdownException() {
    ReflectionTestUtils.setField(leaderElectionService, "isLeader", new AtomicBoolean(true));
    when(jedisPool.getResource()).thenThrow(new RuntimeException());

    leaderElectionService.shutdown();
    verify(jedis, never()).del(leaderKey);
  }
}
