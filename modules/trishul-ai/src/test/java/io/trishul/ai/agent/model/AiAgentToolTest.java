package io.trishul.ai.agent.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;

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

  @Test
  void testAccessId() throws Exception {
    AiAgentTool accessor = new AiAgentTool();
    assertSame(accessor, accessor.setId(123L));
    assertEquals(123L, accessor.getId());
  }

  @Test
  void testAccessAgentConfig() throws Exception {
    AiAgentTool accessor = new AiAgentTool();
    AiAgentConfig value = mock(AiAgentConfig.class);
    assertSame(accessor, accessor.setAgentConfig(value));
    assertEquals(value, accessor.getAgentConfig());
  }

  @Test
  void testAccessTool() throws Exception {
    AiAgentTool accessor = new AiAgentTool();
    AiTool value = mock(AiTool.class);
    assertSame(accessor, accessor.setTool(value));
    assertEquals(value, accessor.getTool());
  }

  @Test
  void testAccessVersion() throws Exception {
    AiAgentTool accessor = new AiAgentTool();
    assertSame(accessor, accessor.setVersion(123));
    assertEquals(123, accessor.getVersion());
  }

  @Test
  void testAccessCreatedAt() throws Exception {
    AiAgentTool accessor = new AiAgentTool();
    LocalDateTime value = LocalDateTime.of(2000, 1, 1, 0, 0);
    assertSame(accessor, accessor.setCreatedAt(value));
    assertEquals(value, accessor.getCreatedAt());
  }

  @Test
  void testAccessLastUpdated() throws Exception {
    AiAgentTool accessor = new AiAgentTool();
    LocalDateTime value = LocalDateTime.of(2000, 1, 1, 0, 0);
    assertSame(accessor, accessor.setLastUpdated(value));
    assertEquals(value, accessor.getLastUpdated());
  }

}
