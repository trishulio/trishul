package io.trishul.ai.memory.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class AiChatMemoryConfigDtoTest {

  @Test
  void testGettersAndSetters() {
    LocalDateTime now = LocalDateTime.now();
    AiChatMemoryConfigDto dto
        = new AiChatMemoryConfigDto(1L, "name", "strategy", 10, 100, 30, true, now, now, 1);

    assertEquals(1L, dto.getId());
    assertEquals("name", dto.getName());
    assertEquals("strategy", dto.getStrategy());
    assertEquals(10, dto.getMaxMessages());
    assertEquals(100, dto.getMaxTokens());
    assertEquals(30, dto.getTtlMinutes());
    assertTrue(dto.getIsDefault());
    assertEquals(now, dto.getCreatedAt());
    assertEquals(now, dto.getLastUpdated());
    assertEquals(1, dto.getVersion());

    dto.setId(2L).setName("new-name").setStrategy("new-strategy").setMaxMessages(20)
        .setMaxTokens(200).setTtlMinutes(60).setIsDefault(false).setCreatedAt(now.plusDays(1))
        .setLastUpdated(now.plusDays(1)).setVersion(2);

    assertEquals(2L, dto.getId());
    assertEquals("new-name", dto.getName());
    assertEquals("new-strategy", dto.getStrategy());
    assertEquals(20, dto.getMaxMessages());
    assertEquals(200, dto.getMaxTokens());
    assertEquals(60, dto.getTtlMinutes());
    assertTrue(!dto.getIsDefault());
    assertEquals(now.plusDays(1), dto.getCreatedAt());
    assertEquals(now.plusDays(1), dto.getLastUpdated());
    assertEquals(2, dto.getVersion());
  }

  @Test
  void testAccessId() throws Exception {
    AiChatMemoryConfigDto accessor = new AiChatMemoryConfigDto();
    assertSame(accessor, accessor.setId(123L));
    assertEquals(123L, accessor.getId());
  }

  @Test
  void testAccessName() throws Exception {
    AiChatMemoryConfigDto accessor = new AiChatMemoryConfigDto();
    assertSame(accessor, accessor.setName("testString"));
    assertEquals("testString", accessor.getName());
  }

  @Test
  void testAccessStrategy() throws Exception {
    AiChatMemoryConfigDto accessor = new AiChatMemoryConfigDto();
    assertSame(accessor, accessor.setStrategy("testString"));
    assertEquals("testString", accessor.getStrategy());
  }

  @Test
  void testAccessMaxMessages() throws Exception {
    AiChatMemoryConfigDto accessor = new AiChatMemoryConfigDto();
    assertSame(accessor, accessor.setMaxMessages(123));
    assertEquals(123, accessor.getMaxMessages());
  }

  @Test
  void testAccessMaxTokens() throws Exception {
    AiChatMemoryConfigDto accessor = new AiChatMemoryConfigDto();
    assertSame(accessor, accessor.setMaxTokens(123));
    assertEquals(123, accessor.getMaxTokens());
  }

  @Test
  void testAccessTtlMinutes() throws Exception {
    AiChatMemoryConfigDto accessor = new AiChatMemoryConfigDto();
    assertSame(accessor, accessor.setTtlMinutes(123));
    assertEquals(123, accessor.getTtlMinutes());
  }

  @Test
  void testAccessIsDefault() throws Exception {
    AiChatMemoryConfigDto accessor = new AiChatMemoryConfigDto();
    assertSame(accessor, accessor.setIsDefault(true));
    assertEquals(true, accessor.getIsDefault());
  }

  @Test
  void testAccessCreatedAt() throws Exception {
    AiChatMemoryConfigDto accessor = new AiChatMemoryConfigDto();
    LocalDateTime value = LocalDateTime.of(2000, 1, 1, 0, 0);
    assertSame(accessor, accessor.setCreatedAt(value));
    assertEquals(value, accessor.getCreatedAt());
  }

  @Test
  void testAccessLastUpdated() throws Exception {
    AiChatMemoryConfigDto accessor = new AiChatMemoryConfigDto();
    LocalDateTime value = LocalDateTime.of(2000, 1, 1, 0, 0);
    assertSame(accessor, accessor.setLastUpdated(value));
    assertEquals(value, accessor.getLastUpdated());
  }

  @Test
  void testAccessVersion() throws Exception {
    AiChatMemoryConfigDto accessor = new AiChatMemoryConfigDto();
    assertSame(accessor, accessor.setVersion(123));
    assertEquals(123, accessor.getVersion());
  }

}
