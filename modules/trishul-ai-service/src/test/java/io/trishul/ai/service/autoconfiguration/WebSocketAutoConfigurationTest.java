package io.trishul.ai.service.autoconfiguration;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.trishul.ai.service.speech.websocket.ChatWebSocketHandler;

class WebSocketAutoConfigurationTest {

  private WebSocketAutoConfiguration config;

  @BeforeEach
  void setUp() {
    config = new WebSocketAutoConfiguration();
  }

  @Test
  void testChatWebSocketHandler_ReturnsNonNull() {
    ChatWebSocketHandler result = config.chatWebSocketHandler();
    assertNotNull(result);
  }
}
