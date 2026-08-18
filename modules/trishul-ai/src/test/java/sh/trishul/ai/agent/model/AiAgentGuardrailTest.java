package sh.trishul.ai.agent.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import sh.trishul.ai.guardrail.model.AiGuardrail;

class AiAgentGuardrailTest {

  @Test
  void testGetSet() {
    AiAgentGuardrail agentGuardrail = new AiAgentGuardrail();
    assertNull(agentGuardrail.getId());
    assertNull(agentGuardrail.getAgentConfig());
    assertNull(agentGuardrail.getGuardrail());
    assertNull(agentGuardrail.getVersion());
    assertNull(agentGuardrail.getCreatedAt());
    assertNull(agentGuardrail.getLastUpdated());

    LocalDateTime now = LocalDateTime.now();
    AiAgentConfig mockAgentConfig = new AiAgentConfig(10L);
    AiGuardrail mockGuardrail = new AiGuardrail(20L);

    agentGuardrail.setId(1L);
    agentGuardrail.setAgentConfig(mockAgentConfig);
    agentGuardrail.setGuardrail(mockGuardrail);
    agentGuardrail.setVersion(2);
    agentGuardrail.setCreatedAt(now);
    agentGuardrail.setLastUpdated(now);

    assertEquals(1L, agentGuardrail.getId());
    assertEquals(mockAgentConfig, agentGuardrail.getAgentConfig());
    assertEquals(mockGuardrail, agentGuardrail.getGuardrail());
    assertEquals(2, agentGuardrail.getVersion());
    assertEquals(now, agentGuardrail.getCreatedAt());
    assertEquals(now, agentGuardrail.getLastUpdated());

    AiAgentGuardrail agentGuardrail2 = new AiAgentGuardrail(2L);
    assertEquals(2L, agentGuardrail2.getId());
  }

  @Test
  void testAccessId() throws Exception {
    AiAgentGuardrail accessor = new AiAgentGuardrail();
    assertSame(accessor, accessor.setId(123L));
    assertEquals(123L, accessor.getId());
  }

  @Test
  void testAccessAgentConfig() throws Exception {
    AiAgentGuardrail accessor = new AiAgentGuardrail();
    AiAgentConfig value = mock(AiAgentConfig.class);
    assertSame(accessor, accessor.setAgentConfig(value));
    assertEquals(value, accessor.getAgentConfig());
  }

  @Test
  void testAccessGuardrail() throws Exception {
    AiAgentGuardrail accessor = new AiAgentGuardrail();
    AiGuardrail value = mock(AiGuardrail.class);
    assertSame(accessor, accessor.setGuardrail(value));
    assertEquals(value, accessor.getGuardrail());
  }

  @Test
  void testAccessVersion() throws Exception {
    AiAgentGuardrail accessor = new AiAgentGuardrail();
    assertSame(accessor, accessor.setVersion(123));
    assertEquals(123, accessor.getVersion());
  }

  @Test
  void testAccessCreatedAt() throws Exception {
    AiAgentGuardrail accessor = new AiAgentGuardrail();
    LocalDateTime value = LocalDateTime.of(2000, 1, 1, 0, 0);
    assertSame(accessor, accessor.setCreatedAt(value));
    assertEquals(value, accessor.getCreatedAt());
  }

  @Test
  void testAccessLastUpdated() throws Exception {
    AiAgentGuardrail accessor = new AiAgentGuardrail();
    LocalDateTime value = LocalDateTime.of(2000, 1, 1, 0, 0);
    assertSame(accessor, accessor.setLastUpdated(value));
    assertEquals(value, accessor.getLastUpdated());
  }

}
