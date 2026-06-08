package io.trishul.ai.agent.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import io.trishul.ai.guardrail.model.AiGuardrail;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

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
}
