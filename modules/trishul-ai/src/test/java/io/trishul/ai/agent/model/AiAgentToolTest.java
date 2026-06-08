package io.trishul.ai.agent.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import io.trishul.ai.tool.model.AiTool;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class AiAgentToolTest {

  @Test
  void testGettersAndSetters() {
    AiAgentTool agentTool = new AiAgentTool();
    assertNull(agentTool.getId());
    assertNull(agentTool.getAgentConfig());
    assertNull(agentTool.getTool());
    assertNull(agentTool.getVersion());
    assertNull(agentTool.getCreatedAt());
    assertNull(agentTool.getLastUpdated());

    LocalDateTime now = LocalDateTime.now();
    AiAgentConfig mockAgentConfig = new AiAgentConfig(10L);
    AiTool mockTool = new AiTool(20L);

    agentTool.setId(1L);
    agentTool.setAgentConfig(mockAgentConfig);
    agentTool.setTool(mockTool);
    agentTool.setVersion(2);
    agentTool.setCreatedAt(now);
    agentTool.setLastUpdated(now);

    assertEquals(1L, agentTool.getId());
    assertEquals(mockAgentConfig, agentTool.getAgentConfig());
    assertEquals(mockTool, agentTool.getTool());
    assertEquals(2, agentTool.getVersion());
    assertEquals(now, agentTool.getCreatedAt());
    assertEquals(now, agentTool.getLastUpdated());

    AiAgentTool agentTool2 = new AiAgentTool(2L);
    assertEquals(2L, agentTool2.getId());
  }
}
