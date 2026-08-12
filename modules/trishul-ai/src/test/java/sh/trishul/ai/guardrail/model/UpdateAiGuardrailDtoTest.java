package sh.trishul.ai.guardrail.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.Test;

class UpdateAiGuardrailDtoTest {

  @Test
  void testGettersAndSetters() {
    UpdateAiGuardrailDto dto = new UpdateAiGuardrailDto(1L, "name", AiGuardrailType.INPUT,
        "strategy", "config", 1, true, 1);

    assertEquals(1L, dto.getId());
    assertEquals("name", dto.getName());
    assertEquals(AiGuardrailType.INPUT, dto.getType());
    assertEquals("strategy", dto.getStrategy());
    assertEquals("config", dto.getConfiguration());
    assertEquals(1, dto.getPriority());
    assertTrue(dto.getIsEnabled());
    assertEquals(1, dto.getVersion());

    dto.setId(2L).setName("new-name").setType(AiGuardrailType.OUTPUT).setStrategy("new-strategy")
        .setConfiguration("new-config").setPriority(2).setIsEnabled(false).setVersion(2);

    assertEquals(2L, dto.getId());
    assertEquals("new-name", dto.getName());
    assertEquals(AiGuardrailType.OUTPUT, dto.getType());
    assertEquals("new-strategy", dto.getStrategy());
    assertEquals("new-config", dto.getConfiguration());
    assertEquals(2, dto.getPriority());
    assertTrue(!dto.getIsEnabled());
    assertEquals(2, dto.getVersion());
  }

  @Test
  void testAccessId() throws Exception {
    UpdateAiGuardrailDto accessor = new UpdateAiGuardrailDto();
    assertSame(accessor, accessor.setId(123L));
    assertEquals(123L, accessor.getId());
  }

  @Test
  void testAccessName() throws Exception {
    UpdateAiGuardrailDto accessor = new UpdateAiGuardrailDto();
    assertSame(accessor, accessor.setName("testString"));
    assertEquals("testString", accessor.getName());
  }

  @Test
  void testAccessType() throws Exception {
    UpdateAiGuardrailDto accessor = new UpdateAiGuardrailDto();
    AiGuardrailType value = mock(AiGuardrailType.class);
    assertSame(accessor, accessor.setType(value));
    assertEquals(value, accessor.getType());
  }

  @Test
  void testAccessStrategy() throws Exception {
    UpdateAiGuardrailDto accessor = new UpdateAiGuardrailDto();
    assertSame(accessor, accessor.setStrategy("testString"));
    assertEquals("testString", accessor.getStrategy());
  }

  @Test
  void testAccessConfiguration() throws Exception {
    UpdateAiGuardrailDto accessor = new UpdateAiGuardrailDto();
    assertSame(accessor, accessor.setConfiguration("testString"));
    assertEquals("testString", accessor.getConfiguration());
  }

  @Test
  void testAccessPriority() throws Exception {
    UpdateAiGuardrailDto accessor = new UpdateAiGuardrailDto();
    assertSame(accessor, accessor.setPriority(123));
    assertEquals(123, accessor.getPriority());
  }

  @Test
  void testAccessIsEnabled() throws Exception {
    UpdateAiGuardrailDto accessor = new UpdateAiGuardrailDto();
    assertSame(accessor, accessor.setIsEnabled(true));
    assertEquals(true, accessor.getIsEnabled());
  }

  @Test
  void testAccessVersion() throws Exception {
    UpdateAiGuardrailDto accessor = new UpdateAiGuardrailDto();
    assertSame(accessor, accessor.setVersion(123));
    assertEquals(123, accessor.getVersion());
  }

}
