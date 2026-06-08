package io.trishul.ai.service.autoconfiguration;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistration;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

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

  @Test
  void testRegisterWebSocketHandlers_RegistersHandler() {
    WebSocketHandlerRegistry mockRegistry = mock(WebSocketHandlerRegistry.class);
    WebSocketHandlerRegistration mockRegistration = mock(WebSocketHandlerRegistration.class);

    when(mockRegistry.addHandler(any(), any())).thenReturn(mockRegistration);
    when(mockRegistration.setAllowedOrigins(any())).thenReturn(mockRegistration);

    ReflectionTestUtils.setField(config, "allowedOrigins", new String[] {"*"});

    config.registerWebSocketHandlers(mockRegistry);

    verify(mockRegistry).addHandler(any(ChatWebSocketHandler.class), eq("/api/v1/ai/chat/stream"));
    verify(mockRegistration).setAllowedOrigins(new String[] {"*"});
  }
}
