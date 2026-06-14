package io.trishul.ai.tool.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class AiToolTest {

  @Test
  void testGettersAndSetters() {
    LocalDateTime now = LocalDateTime.now();
    AiTool config = new AiTool(1L, "name", "bean", "desc", true, now, now, 1);

    assertEquals(1L, config.getId());
    assertEquals("name", config.getName());
    assertEquals("bean", config.getBeanName());
    assertEquals("desc", config.getDescription());
    assertTrue(config.getIsEnabled());
    assertEquals(now, config.getCreatedAt());
    assertEquals(now, config.getLastUpdated());
    assertEquals(1, config.getVersion());

    config.setId(2L).setName("new-name").setBeanName("new-bean").setDescription("new-desc")
        .setIsEnabled(false).setCreatedAt(now.plusDays(1)).setLastUpdated(now.plusDays(1))
        .setVersion(2);

    assertEquals(2L, config.getId());
    assertEquals("new-name", config.getName());
    assertEquals("new-bean", config.getBeanName());
    assertEquals("new-desc", config.getDescription());
    assertTrue(!config.getIsEnabled());
    assertEquals(now.plusDays(1), config.getCreatedAt());
    assertEquals(now.plusDays(1), config.getLastUpdated());
    assertEquals(2, config.getVersion());
  }
}
