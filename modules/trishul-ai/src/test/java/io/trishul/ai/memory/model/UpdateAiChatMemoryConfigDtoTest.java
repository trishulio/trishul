package io.trishul.ai.memory.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class UpdateAiChatMemoryConfigDtoTest {

  @Test
  void testGettersAndSetters() {
    UpdateAiChatMemoryConfigDto dto
        = new UpdateAiChatMemoryConfigDto(1L, "name", "strategy", 10, 100, 30, true, 1);

    assertEquals(1L, dto.getId());
    assertEquals("name", dto.getName());
    assertEquals("strategy", dto.getStrategy());
    assertEquals(10, dto.getMaxMessages());
    assertEquals(100, dto.getMaxTokens());
    assertEquals(30, dto.getTtlMinutes());
    assertTrue(dto.getIsDefault());
    assertEquals(1, dto.getVersion());

    dto.setId(2L).setName("new-name").setStrategy("new-strategy").setMaxMessages(20)
        .setMaxTokens(200).setTtlMinutes(60).setIsDefault(false).setVersion(2);

    assertEquals(2L, dto.getId());
    assertEquals("new-name", dto.getName());
    assertEquals("new-strategy", dto.getStrategy());
    assertEquals(20, dto.getMaxMessages());
    assertEquals(200, dto.getMaxTokens());
    assertEquals(60, dto.getTtlMinutes());
    assertTrue(!dto.getIsDefault());
    assertEquals(2, dto.getVersion());
  }

  @Test
  void testAccessId() throws Exception {
    UpdateAiChatMemoryConfigDto accessor = new UpdateAiChatMemoryConfigDto();
    assertSame(accessor, accessor.setId(123L));
    assertEquals(123L, accessor.getId());
  }

  @Test
  void testAccessName() throws Exception {
    UpdateAiChatMemoryConfigDto accessor = new UpdateAiChatMemoryConfigDto();
    assertSame(accessor, accessor.setName("testString"));
    assertEquals("testString", accessor.getName());
  }

  @Test
  void testAccessStrategy() throws Exception {
    UpdateAiChatMemoryConfigDto accessor = new UpdateAiChatMemoryConfigDto();
    assertSame(accessor, accessor.setStrategy("testString"));
    assertEquals("testString", accessor.getStrategy());
  }

  @Test
  void testAccessMaxMessages() throws Exception {
    UpdateAiChatMemoryConfigDto accessor = new UpdateAiChatMemoryConfigDto();
    assertSame(accessor, accessor.setMaxMessages(123));
    assertEquals(123, accessor.getMaxMessages());
  }

  @Test
  void testAccessMaxTokens() throws Exception {
    UpdateAiChatMemoryConfigDto accessor = new UpdateAiChatMemoryConfigDto();
    assertSame(accessor, accessor.setMaxTokens(123));
    assertEquals(123, accessor.getMaxTokens());
  }

  @Test
  void testAccessTtlMinutes() throws Exception {
    UpdateAiChatMemoryConfigDto accessor = new UpdateAiChatMemoryConfigDto();
    assertSame(accessor, accessor.setTtlMinutes(123));
    assertEquals(123, accessor.getTtlMinutes());
  }

  @Test
  void testAccessIsDefault() throws Exception {
    UpdateAiChatMemoryConfigDto accessor = new UpdateAiChatMemoryConfigDto();
    assertSame(accessor, accessor.setIsDefault(true));
    assertEquals(true, accessor.getIsDefault());
  }

  @Test
  void testAccessVersion() throws Exception {
    UpdateAiChatMemoryConfigDto accessor = new UpdateAiChatMemoryConfigDto();
    assertSame(accessor, accessor.setVersion(123));
    assertEquals(123, accessor.getVersion());
  }

}
