package io.trishul.ai.tool.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class UpdateAiToolDtoTest {

  @Test
  void testGettersAndSetters() {
    UpdateAiToolDto dto = new UpdateAiToolDto(1L, "name", "bean", "desc", true, 1);

    assertEquals(1L, dto.getId());
    assertEquals("name", dto.getName());
    assertEquals("bean", dto.getBeanName());
    assertEquals("desc", dto.getDescription());
    assertTrue(dto.getIsEnabled());
    assertEquals(1, dto.getVersion());

    dto.setId(2L).setName("new-name").setBeanName("new-bean").setDescription("new-desc")
        .setIsEnabled(false).setVersion(2);

    assertEquals(2L, dto.getId());
    assertEquals("new-name", dto.getName());
    assertEquals("new-bean", dto.getBeanName());
    assertEquals("new-desc", dto.getDescription());
    assertTrue(!dto.getIsEnabled());
    assertEquals(2, dto.getVersion());
  }
}
