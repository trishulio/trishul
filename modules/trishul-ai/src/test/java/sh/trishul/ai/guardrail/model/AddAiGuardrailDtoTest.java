package sh.trishul.ai.guardrail.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.Test;

class AddAiGuardrailDtoTest {

  @Test
  void testGettersAndSetters() {
    AddAiGuardrailDto dto
        = new AddAiGuardrailDto("name", AiGuardrailType.INPUT, "strategy", "config", 1, true);

    assertEquals("name", dto.getName());
    assertEquals(AiGuardrailType.INPUT, dto.getType());
    assertEquals("strategy", dto.getStrategy());
    assertEquals("config", dto.getConfiguration());
    assertEquals(1, dto.getPriority());
    assertTrue(dto.getIsEnabled());

    dto.setName("new-name").setType(AiGuardrailType.OUTPUT).setStrategy("new-strategy")
        .setConfiguration("new-config").setPriority(2).setIsEnabled(false);

    assertEquals("new-name", dto.getName());
    assertEquals(AiGuardrailType.OUTPUT, dto.getType());
    assertEquals("new-strategy", dto.getStrategy());
    assertEquals("new-config", dto.getConfiguration());
    assertEquals(2, dto.getPriority());
    assertTrue(!dto.getIsEnabled());
  }

  @Test
  void testAccessName() throws Exception {
    AddAiGuardrailDto accessor = new AddAiGuardrailDto();
    assertSame(accessor, accessor.setName("testString"));
    assertEquals("testString", accessor.getName());
  }

  @Test
  void testAccessType() throws Exception {
    AddAiGuardrailDto accessor = new AddAiGuardrailDto();
    AiGuardrailType value = mock(AiGuardrailType.class);
    assertSame(accessor, accessor.setType(value));
    assertEquals(value, accessor.getType());
  }

  @Test
  void testAccessStrategy() throws Exception {
    AddAiGuardrailDto accessor = new AddAiGuardrailDto();
    assertSame(accessor, accessor.setStrategy("testString"));
    assertEquals("testString", accessor.getStrategy());
  }

  @Test
  void testAccessConfiguration() throws Exception {
    AddAiGuardrailDto accessor = new AddAiGuardrailDto();
    assertSame(accessor, accessor.setConfiguration("testString"));
    assertEquals("testString", accessor.getConfiguration());
  }

  @Test
  void testAccessPriority() throws Exception {
    AddAiGuardrailDto accessor = new AddAiGuardrailDto();
    assertSame(accessor, accessor.setPriority(123));
    assertEquals(123, accessor.getPriority());
  }

  @Test
  void testAccessIsEnabled() throws Exception {
    AddAiGuardrailDto accessor = new AddAiGuardrailDto();
    assertSame(accessor, accessor.setIsEnabled(true));
    assertEquals(true, accessor.getIsEnabled());
  }

}
