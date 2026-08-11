package io.trishul.ai.guardrail.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class AiGuardrailDtoTest {

  @Test
  void testGettersAndSetters() {
    LocalDateTime now = LocalDateTime.now();
    AiGuardrailDto dto = new AiGuardrailDto(1L, "name", AiGuardrailType.INPUT, "strategy", "config",
        1, true, now, now, 1);

    assertEquals(1L, dto.getId());
    assertEquals("name", dto.getName());
    assertEquals(AiGuardrailType.INPUT, dto.getType());
    assertEquals("strategy", dto.getStrategy());
    assertEquals("config", dto.getConfiguration());
    assertEquals(1, dto.getPriority());
    assertTrue(dto.getIsEnabled());
    assertEquals(now, dto.getCreatedAt());
    assertEquals(now, dto.getLastUpdated());
    assertEquals(1, dto.getVersion());

    dto.setId(2L).setName("new-name").setType(AiGuardrailType.OUTPUT).setStrategy("new-strategy")
        .setConfiguration("new-config").setPriority(2).setIsEnabled(false)
        .setCreatedAt(now.plusDays(1)).setLastUpdated(now.plusDays(1)).setVersion(2);

    assertEquals(2L, dto.getId());
    assertEquals("new-name", dto.getName());
    assertEquals(AiGuardrailType.OUTPUT, dto.getType());
    assertEquals("new-strategy", dto.getStrategy());
    assertEquals("new-config", dto.getConfiguration());
    assertEquals(2, dto.getPriority());
    assertTrue(!dto.getIsEnabled());
    assertEquals(now.plusDays(1), dto.getCreatedAt());
    assertEquals(now.plusDays(1), dto.getLastUpdated());
    assertEquals(2, dto.getVersion());
  }

  @Test
  void testAccessId() throws Exception {
    AiGuardrailDto accessor = new AiGuardrailDto();
    assertSame(accessor, accessor.setId(123L));
    assertEquals(123L, accessor.getId());
  }

  @Test
  void testAccessName() throws Exception {
    AiGuardrailDto accessor = new AiGuardrailDto();
    assertSame(accessor, accessor.setName("testString"));
    assertEquals("testString", accessor.getName());
  }

  @Test
  void testAccessType() throws Exception {
    AiGuardrailDto accessor = new AiGuardrailDto();
    AiGuardrailType value = mock(AiGuardrailType.class);
    assertSame(accessor, accessor.setType(value));
    assertEquals(value, accessor.getType());
  }

  @Test
  void testAccessStrategy() throws Exception {
    AiGuardrailDto accessor = new AiGuardrailDto();
    assertSame(accessor, accessor.setStrategy("testString"));
    assertEquals("testString", accessor.getStrategy());
  }

  @Test
  void testAccessConfiguration() throws Exception {
    AiGuardrailDto accessor = new AiGuardrailDto();
    assertSame(accessor, accessor.setConfiguration("testString"));
    assertEquals("testString", accessor.getConfiguration());
  }

  @Test
  void testAccessPriority() throws Exception {
    AiGuardrailDto accessor = new AiGuardrailDto();
    assertSame(accessor, accessor.setPriority(123));
    assertEquals(123, accessor.getPriority());
  }

  @Test
  void testAccessIsEnabled() throws Exception {
    AiGuardrailDto accessor = new AiGuardrailDto();
    assertSame(accessor, accessor.setIsEnabled(true));
    assertEquals(true, accessor.getIsEnabled());
  }

  @Test
  void testAccessCreatedAt() throws Exception {
    AiGuardrailDto accessor = new AiGuardrailDto();
    LocalDateTime value = LocalDateTime.of(2000, 1, 1, 0, 0);
    assertSame(accessor, accessor.setCreatedAt(value));
    assertEquals(value, accessor.getCreatedAt());
  }

  @Test
  void testAccessLastUpdated() throws Exception {
    AiGuardrailDto accessor = new AiGuardrailDto();
    LocalDateTime value = LocalDateTime.of(2000, 1, 1, 0, 0);
    assertSame(accessor, accessor.setLastUpdated(value));
    assertEquals(value, accessor.getLastUpdated());
  }

  @Test
  void testAccessVersion() throws Exception {
    AiGuardrailDto accessor = new AiGuardrailDto();
    assertSame(accessor, accessor.setVersion(123));
    assertEquals(123, accessor.getVersion());
  }

}
