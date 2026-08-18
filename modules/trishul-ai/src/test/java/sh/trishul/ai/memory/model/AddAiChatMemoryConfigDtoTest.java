package sh.trishul.ai.memory.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class AddAiChatMemoryConfigDtoTest {

  @Test
  void testGettersAndSetters() {
    AddAiChatMemoryConfigDto dto
        = new AddAiChatMemoryConfigDto("name", "strategy", 10, 100, 30, true);

    assertEquals("name", dto.getName());
    assertEquals("strategy", dto.getStrategy());
    assertEquals(10, dto.getMaxMessages());
    assertEquals(100, dto.getMaxTokens());
    assertEquals(30, dto.getTtlMinutes());
    assertTrue(dto.getIsDefault());

    dto.setName("new-name").setStrategy("new-strategy").setMaxMessages(20).setMaxTokens(200)
        .setTtlMinutes(60).setIsDefault(false);

    assertEquals("new-name", dto.getName());
    assertEquals("new-strategy", dto.getStrategy());
    assertEquals(20, dto.getMaxMessages());
    assertEquals(200, dto.getMaxTokens());
    assertEquals(60, dto.getTtlMinutes());
    assertTrue(!dto.getIsDefault());
  }

  @Test
  void testAccessName() throws Exception {
    AddAiChatMemoryConfigDto accessor = new AddAiChatMemoryConfigDto();
    assertSame(accessor, accessor.setName("testString"));
    assertEquals("testString", accessor.getName());
  }

  @Test
  void testAccessStrategy() throws Exception {
    AddAiChatMemoryConfigDto accessor = new AddAiChatMemoryConfigDto();
    assertSame(accessor, accessor.setStrategy("testString"));
    assertEquals("testString", accessor.getStrategy());
  }

  @Test
  void testAccessMaxMessages() throws Exception {
    AddAiChatMemoryConfigDto accessor = new AddAiChatMemoryConfigDto();
    assertSame(accessor, accessor.setMaxMessages(123));
    assertEquals(123, accessor.getMaxMessages());
  }

  @Test
  void testAccessMaxTokens() throws Exception {
    AddAiChatMemoryConfigDto accessor = new AddAiChatMemoryConfigDto();
    assertSame(accessor, accessor.setMaxTokens(123));
    assertEquals(123, accessor.getMaxTokens());
  }

  @Test
  void testAccessTtlMinutes() throws Exception {
    AddAiChatMemoryConfigDto accessor = new AddAiChatMemoryConfigDto();
    assertSame(accessor, accessor.setTtlMinutes(123));
    assertEquals(123, accessor.getTtlMinutes());
  }

  @Test
  void testAccessIsDefault() throws Exception {
    AddAiChatMemoryConfigDto accessor = new AddAiChatMemoryConfigDto();
    assertSame(accessor, accessor.setIsDefault(true));
    assertEquals(true, accessor.getIsDefault());
  }

}
