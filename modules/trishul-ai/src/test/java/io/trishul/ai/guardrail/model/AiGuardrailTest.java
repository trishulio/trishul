package io.trishul.ai.guardrail.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

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

  @Test
  void testAccessId() throws Exception {
    AiGuardrail accessor = new AiGuardrail();
    assertSame(accessor, accessor.setId(123L));
    assertEquals(123L, accessor.getId());
  }

  @Test
  void testAccessName() throws Exception {
    AiGuardrail accessor = new AiGuardrail();
    assertSame(accessor, accessor.setName("testString"));
    assertEquals("testString", accessor.getName());
  }

  @Test
  void testAccessType() throws Exception {
    AiGuardrail accessor = new AiGuardrail();
    AiGuardrailType value = mock(AiGuardrailType.class);
    assertSame(accessor, accessor.setType(value));
    assertEquals(value, accessor.getType());
  }

  @Test
  void testAccessStrategy() throws Exception {
    AiGuardrail accessor = new AiGuardrail();
    assertSame(accessor, accessor.setStrategy("testString"));
    assertEquals("testString", accessor.getStrategy());
  }

  @Test
  void testAccessConfiguration() throws Exception {
    AiGuardrail accessor = new AiGuardrail();
    assertSame(accessor, accessor.setConfiguration("testString"));
    assertEquals("testString", accessor.getConfiguration());
  }

  @Test
  void testAccessPriority() throws Exception {
    AiGuardrail accessor = new AiGuardrail();
    assertSame(accessor, accessor.setPriority(123));
    assertEquals(123, accessor.getPriority());
  }

  @Test
  void testAccessIsEnabled() throws Exception {
    AiGuardrail accessor = new AiGuardrail();
    assertSame(accessor, accessor.setIsEnabled(true));
    assertEquals(true, accessor.getIsEnabled());
  }

  @Test
  void testAccessVersion() throws Exception {
    AiGuardrail accessor = new AiGuardrail();
    assertSame(accessor, accessor.setVersion(123));
    assertEquals(123, accessor.getVersion());
  }

  @Test
  void testAccessCreatedAt() throws Exception {
    AiGuardrail accessor = new AiGuardrail();
    LocalDateTime value = LocalDateTime.of(2000, 1, 1, 0, 0);
    assertSame(accessor, accessor.setCreatedAt(value));
    assertEquals(value, accessor.getCreatedAt());
  }

  @Test
  void testAccessLastUpdated() throws Exception {
    AiGuardrail accessor = new AiGuardrail();
    LocalDateTime value = LocalDateTime.of(2000, 1, 1, 0, 0);
    assertSame(accessor, accessor.setLastUpdated(value));
    assertEquals(value, accessor.getLastUpdated());
  }

}
