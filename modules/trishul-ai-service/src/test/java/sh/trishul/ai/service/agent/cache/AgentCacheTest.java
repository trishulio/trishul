package sh.trishul.ai.service.agent.cache;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.ReflectionTestUtils.setField;

import com.google.common.cache.LoadingCache;
import java.util.concurrent.ExecutionException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sh.trishul.ai.agent.model.AiAgentConfig;
import sh.trishul.ai.service.agent.factory.AgentFactory;

class AgentCacheTest {

  private AgentCache agentCache;
  private AgentFactory mockAgentFactory;

  @BeforeEach
  void setUp() {
    mockAgentFactory = mock(AgentFactory.class);
    agentCache = new AgentCache(mockAgentFactory);
  }

  @Test
  void testGetAgent_LoadsAgentFromFactory() {
    AiAgentConfig config = new AiAgentConfig(1L);
    Object mockAgent = new Object();
    when(mockAgentFactory.buildAgent(config)).thenReturn(mockAgent);

    Object result = agentCache.getAgent(config);

    assertEquals(mockAgent, result);
    verify(mockAgentFactory).buildAgent(config);
  }

  @Test
  @SuppressWarnings("unchecked")
  void testGetAgent_ThrowsRuntimeException_WhenExecutionExceptionOccurs() throws Exception {
    AiAgentConfig config = new AiAgentConfig(1L);
    Exception cause = new Exception("Checked exception");
    ExecutionException exception = new ExecutionException(cause);

    LoadingCache<AiAgentConfig, Object> mockCache = mock(LoadingCache.class);
    when(mockCache.get(config)).thenThrow(exception);

    setField(agentCache, "cache", mockCache);

    RuntimeException thrown
        = assertThrows(RuntimeException.class, () -> agentCache.getAgent(config));
    assertEquals("Failed to load agent", thrown.getMessage());
    assertEquals(cause, thrown.getCause());
  }

  @Test
  void testEvictAgent_InvalidatesCache() {
    AiAgentConfig config = new AiAgentConfig(1L);
    Object mockAgent1 = new Object();
    Object mockAgent2 = new Object();

    when(mockAgentFactory.buildAgent(config)).thenReturn(mockAgent1).thenReturn(mockAgent2);

    Object result1 = agentCache.getAgent(config);
    assertEquals(mockAgent1, result1);

    // Evict and get again to verify it loads a new agent
    agentCache.evictAgent(config);

    Object result2 = agentCache.getAgent(config);
    assertEquals(mockAgent2, result2);
  }
}
