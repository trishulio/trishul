package io.trishul.ai.guardrail.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class AiGuardrailTest {

  @Test
  void testGettersAndSetters() {
    LocalDateTime now = LocalDateTime.now();
    AiGuardrail config = new AiGuardrail(1L, "name", AiGuardrailType.INPUT, "strategy", "config", 1,
        true, now, now, 1);

    assertEquals(1L, config.getId());
    assertEquals("name", config.getName());
    assertEquals(AiGuardrailType.INPUT, config.getType());
    assertEquals("strategy", config.getStrategy());
    assertEquals("config", config.getConfiguration());
    assertEquals(1, config.getPriority());
    assertTrue(config.getIsEnabled());
    assertEquals(now, config.getCreatedAt());
    assertEquals(now, config.getLastUpdated());
    assertEquals(1, config.getVersion());

    config.setId(2L).setName("new-name").setType(AiGuardrailType.OUTPUT).setStrategy("new-strategy")
        .setConfiguration("new-config").setPriority(2).setIsEnabled(false)
        .setCreatedAt(now.plusDays(1)).setLastUpdated(now.plusDays(1)).setVersion(2);

    assertEquals(2L, config.getId());
    assertEquals("new-name", config.getName());
    assertEquals(AiGuardrailType.OUTPUT, config.getType());
    assertEquals("new-strategy", config.getStrategy());
    assertEquals("new-config", config.getConfiguration());
    assertEquals(2, config.getPriority());
    assertTrue(!config.getIsEnabled());
    assertEquals(now.plusDays(1), config.getCreatedAt());
    assertEquals(now.plusDays(1), config.getLastUpdated());
    assertEquals(2, config.getVersion());
  }
}
