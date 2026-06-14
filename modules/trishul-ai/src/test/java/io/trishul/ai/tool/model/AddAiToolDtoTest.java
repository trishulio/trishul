package io.trishul.ai.tool.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class AddAiToolDtoTest {

  @Test
  void testGettersAndSetters() {
    AddAiToolDto dto = new AddAiToolDto("name", "bean", "desc", true);

    assertEquals("name", dto.getName());
    assertEquals("bean", dto.getBeanName());
    assertEquals("desc", dto.getDescription());
    assertTrue(dto.getIsEnabled());

    dto.setName("new-name").setBeanName("new-bean").setDescription("new-desc").setIsEnabled(false);

    assertEquals("new-name", dto.getName());
    assertEquals("new-bean", dto.getBeanName());
    assertEquals("new-desc", dto.getDescription());
    assertTrue(!dto.getIsEnabled());
  }
}
