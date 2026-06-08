package io.trishul.ai.agent.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

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
}
