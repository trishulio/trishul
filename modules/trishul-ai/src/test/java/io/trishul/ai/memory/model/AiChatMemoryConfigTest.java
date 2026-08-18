package io.trishul.ai.memory.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class AiChatMemoryConfigTest {

  @Test
  void testGettersAndSetters() {
    LocalDateTime now = LocalDateTime.now();
    AiChatMemoryConfig config
        = new AiChatMemoryConfig(1L, "name", "strategy", 10, 100, 30, true, now, now, 1);

    assertEquals(1L, config.getId());
    assertEquals("name", config.getName());
    assertEquals("strategy", config.getStrategy());
    assertEquals(10, config.getMaxMessages());
    assertEquals(100, config.getMaxTokens());
    assertEquals(30, config.getTtlMinutes());
    assertTrue(config.getIsDefault());
    assertEquals(now, config.getCreatedAt());
    assertEquals(now, config.getLastUpdated());
    assertEquals(1, config.getVersion());

    config.setId(2L).setName("new-name").setStrategy("new-strategy").setMaxMessages(20)
        .setMaxTokens(200).setTtlMinutes(60).setIsDefault(false).setCreatedAt(now.plusDays(1))
        .setLastUpdated(now.plusDays(1)).setVersion(2);

    assertEquals(2L, config.getId());
    assertEquals("new-name", config.getName());
    assertEquals("new-strategy", config.getStrategy());
    assertEquals(20, config.getMaxMessages());
    assertEquals(200, config.getMaxTokens());
    assertEquals(60, config.getTtlMinutes());
    assertTrue(!config.getIsDefault());
    assertEquals(now.plusDays(1), config.getCreatedAt());
    assertEquals(now.plusDays(1), config.getLastUpdated());
    assertEquals(2, config.getVersion());
  }

  @Test
  void testAccessId() throws Exception {
    AiChatMemoryConfig accessor = new AiChatMemoryConfig();
    assertSame(accessor, accessor.setId(123L));
    assertEquals(123L, accessor.getId());
  }

  @Test
  void testAccessName() throws Exception {
    AiChatMemoryConfig accessor = new AiChatMemoryConfig();
    assertSame(accessor, accessor.setName("testString"));
    assertEquals("testString", accessor.getName());
  }

  @Test
  void testAccessStrategy() throws Exception {
    AiChatMemoryConfig accessor = new AiChatMemoryConfig();
    assertSame(accessor, accessor.setStrategy("testString"));
    assertEquals("testString", accessor.getStrategy());
  }

  @Test
  void testAccessMaxMessages() throws Exception {
    AiChatMemoryConfig accessor = new AiChatMemoryConfig();
    assertSame(accessor, accessor.setMaxMessages(123));
    assertEquals(123, accessor.getMaxMessages());
  }

  @Test
  void testAccessMaxTokens() throws Exception {
    AiChatMemoryConfig accessor = new AiChatMemoryConfig();
    assertSame(accessor, accessor.setMaxTokens(123));
    assertEquals(123, accessor.getMaxTokens());
  }

  @Test
  void testAccessTtlMinutes() throws Exception {
    AiChatMemoryConfig accessor = new AiChatMemoryConfig();
    assertSame(accessor, accessor.setTtlMinutes(123));
    assertEquals(123, accessor.getTtlMinutes());
  }

  @Test
  void testAccessIsDefault() throws Exception {
    AiChatMemoryConfig accessor = new AiChatMemoryConfig();
    assertSame(accessor, accessor.setIsDefault(true));
    assertEquals(true, accessor.getIsDefault());
  }

  @Test
  void testAccessVersion() throws Exception {
    AiChatMemoryConfig accessor = new AiChatMemoryConfig();
    assertSame(accessor, accessor.setVersion(123));
    assertEquals(123, accessor.getVersion());
  }

  @Test
  void testAccessCreatedAt() throws Exception {
    AiChatMemoryConfig accessor = new AiChatMemoryConfig();
    LocalDateTime value = LocalDateTime.of(2000, 1, 1, 0, 0);
    assertSame(accessor, accessor.setCreatedAt(value));
    assertEquals(value, accessor.getCreatedAt());
  }

  @Test
  void testAccessLastUpdated() throws Exception {
    AiChatMemoryConfig accessor = new AiChatMemoryConfig();
    LocalDateTime value = LocalDateTime.of(2000, 1, 1, 0, 0);
    assertSame(accessor, accessor.setLastUpdated(value));
    assertEquals(value, accessor.getLastUpdated());
  }

}
