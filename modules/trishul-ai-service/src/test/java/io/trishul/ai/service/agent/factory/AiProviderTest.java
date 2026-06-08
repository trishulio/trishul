package io.trishul.ai.service.agent.factory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class AiProviderTest {

  @Test
  void testFromString_ReturnsOpenAi() {
    assertEquals(AiProvider.OPENAI, AiProvider.fromString("OPENAI"));
    assertEquals(AiProvider.OPENAI, AiProvider.fromString("openai"));
  }

  @Test
  void testFromString_ReturnsAnthropic() {
    assertEquals(AiProvider.ANTHROPIC, AiProvider.fromString("ANTHROPIC"));
    assertEquals(AiProvider.ANTHROPIC, AiProvider.fromString("anthropic"));
  }

  @Test
  void testFromString_ThrowsIllegalArgumentException_ForUnsupportedProvider() {
    assertThrows(IllegalArgumentException.class, () -> AiProvider.fromString("unsupported"));
  }

  @Test
  void testEnumValues() {
    assertEquals(3, AiProvider.values().length);
    assertEquals(AiProvider.OPENAI, AiProvider.valueOf("OPENAI"));
  }
}
