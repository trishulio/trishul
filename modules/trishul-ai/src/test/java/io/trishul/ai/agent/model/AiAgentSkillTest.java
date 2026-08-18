package io.trishul.ai.agent.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;

import io.trishul.ai.skill.model.AiSkill;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class AiAgentSkillTest {

  @Test
  void testGettersAndSetters() {
    AiAgentSkill agentSkill = new AiAgentSkill();
    assertNull(agentSkill.getId());
    assertNull(agentSkill.getAgentConfig());
    assertNull(agentSkill.getSkill());
    assertNull(agentSkill.getVersion());
    assertNull(agentSkill.getCreatedAt());
    assertNull(agentSkill.getLastUpdated());

    LocalDateTime now = LocalDateTime.now();
    AiAgentConfig mockAgentConfig = new AiAgentConfig(10L);
    AiSkill mockSkill = new AiSkill(20L);

    agentSkill.setId(1L);
    agentSkill.setAgentConfig(mockAgentConfig);
    agentSkill.setSkill(mockSkill);
    agentSkill.setVersion(2);
    agentSkill.setCreatedAt(now);
    agentSkill.setLastUpdated(now);

    assertEquals(1L, agentSkill.getId());
    assertEquals(mockAgentConfig, agentSkill.getAgentConfig());
    assertEquals(mockSkill, agentSkill.getSkill());
    assertEquals(2, agentSkill.getVersion());
    assertEquals(now, agentSkill.getCreatedAt());
    assertEquals(now, agentSkill.getLastUpdated());

    AiAgentSkill agentSkill2 = new AiAgentSkill(2L);
    assertEquals(2L, agentSkill2.getId());
  }

  @Test
  void testAccessId() throws Exception {
    AiAgentSkill accessor = new AiAgentSkill();
    assertSame(accessor, accessor.setId(123L));
    assertEquals(123L, accessor.getId());
  }

  @Test
  void testAccessAgentConfig() throws Exception {
    AiAgentSkill accessor = new AiAgentSkill();
    AiAgentConfig value = mock(AiAgentConfig.class);
    assertSame(accessor, accessor.setAgentConfig(value));
    assertEquals(value, accessor.getAgentConfig());
  }

  @Test
  void testAccessSkill() throws Exception {
    AiAgentSkill accessor = new AiAgentSkill();
    AiSkill value = mock(AiSkill.class);
    assertSame(accessor, accessor.setSkill(value));
    assertEquals(value, accessor.getSkill());
  }

  @Test
  void testAccessVersion() throws Exception {
    AiAgentSkill accessor = new AiAgentSkill();
    assertSame(accessor, accessor.setVersion(123));
    assertEquals(123, accessor.getVersion());
  }

  @Test
  void testAccessCreatedAt() throws Exception {
    AiAgentSkill accessor = new AiAgentSkill();
    LocalDateTime value = LocalDateTime.of(2000, 1, 1, 0, 0);
    assertSame(accessor, accessor.setCreatedAt(value));
    assertEquals(value, accessor.getCreatedAt());
  }

  @Test
  void testAccessLastUpdated() throws Exception {
    AiAgentSkill accessor = new AiAgentSkill();
    LocalDateTime value = LocalDateTime.of(2000, 1, 1, 0, 0);
    assertSame(accessor, accessor.setLastUpdated(value));
    assertEquals(value, accessor.getLastUpdated());
  }

}
