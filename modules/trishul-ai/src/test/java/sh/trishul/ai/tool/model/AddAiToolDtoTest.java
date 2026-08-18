package sh.trishul.ai.tool.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
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

  @Test
  void testAccessName() throws Exception {
    AddAiToolDto accessor = new AddAiToolDto();
    assertSame(accessor, accessor.setName("testString"));
    assertEquals("testString", accessor.getName());
  }

  @Test
  void testAccessBeanName() throws Exception {
    AddAiToolDto accessor = new AddAiToolDto();
    assertSame(accessor, accessor.setBeanName("testString"));
    assertEquals("testString", accessor.getBeanName());
  }

  @Test
  void testAccessDescription() throws Exception {
    AddAiToolDto accessor = new AddAiToolDto();
    assertSame(accessor, accessor.setDescription("testString"));
    assertEquals("testString", accessor.getDescription());
  }

  @Test
  void testAccessIsEnabled() throws Exception {
    AddAiToolDto accessor = new AddAiToolDto();
    assertSame(accessor, accessor.setIsEnabled(true));
    assertEquals(true, accessor.getIsEnabled());
  }

}
