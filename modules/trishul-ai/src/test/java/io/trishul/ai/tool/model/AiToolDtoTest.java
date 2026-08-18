package io.trishul.ai.tool.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class AiToolDtoTest {
  @Test
  void testAccessId() throws Exception {
    AiToolDto accessor = new AiToolDto();
    assertSame(accessor, accessor.setId(123L));
    assertEquals(123L, accessor.getId());
  }

  @Test
  void testAccessName() throws Exception {
    AiToolDto accessor = new AiToolDto();
    assertSame(accessor, accessor.setName("testString"));
    assertEquals("testString", accessor.getName());
  }

  @Test
  void testAccessBeanName() throws Exception {
    AiToolDto accessor = new AiToolDto();
    assertSame(accessor, accessor.setBeanName("testString"));
    assertEquals("testString", accessor.getBeanName());
  }

  @Test
  void testAccessDescription() throws Exception {
    AiToolDto accessor = new AiToolDto();
    assertSame(accessor, accessor.setDescription("testString"));
    assertEquals("testString", accessor.getDescription());
  }

  @Test
  void testAccessIsEnabled() throws Exception {
    AiToolDto accessor = new AiToolDto();
    assertSame(accessor, accessor.setIsEnabled(true));
    assertEquals(true, accessor.getIsEnabled());

    assertSame(accessor, accessor.setIsEnabled(false));
    assertEquals(false, accessor.getIsEnabled());
  }

  @Test
  void testAccessCreatedAt() throws Exception {
    AiToolDto accessor = new AiToolDto();
    LocalDateTime value = LocalDateTime.of(2000, 1, 1, 0, 0);
    assertSame(accessor, accessor.setCreatedAt(value));
    assertEquals(value, accessor.getCreatedAt());
  }

  @Test
  void testAccessLastUpdated() throws Exception {
    AiToolDto accessor = new AiToolDto();
    LocalDateTime value = LocalDateTime.of(2000, 1, 1, 0, 0);
    assertSame(accessor, accessor.setLastUpdated(value));
    assertEquals(value, accessor.getLastUpdated());
  }

  @Test
  void testAccessVersion() throws Exception {
    AiToolDto accessor = new AiToolDto();
    assertSame(accessor, accessor.setVersion(123));
    assertEquals(123, accessor.getVersion());
  }
}
