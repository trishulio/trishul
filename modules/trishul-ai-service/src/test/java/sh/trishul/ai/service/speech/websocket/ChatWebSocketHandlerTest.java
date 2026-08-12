package sh.trishul.ai.service.speech.websocket;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

class ChatWebSocketHandlerTest {

  private ChatWebSocketHandler handler;
  private WebSocketSession mockSession;

  @BeforeEach
  void setUp() {
    handler = new ChatWebSocketHandler();
    mockSession = mock(WebSocketSession.class);
    when(mockSession.getId()).thenReturn("session-1");
  }

  @Test
  void testAfterConnectionEstablished_LogsConnection() throws Exception {
    handler.afterConnectionEstablished(mockSession);
    verify(mockSession).getId();
  }

  @Test
  void testHandleTextMessage_SendsAckMessage() throws Exception {
    TextMessage message = new TextMessage("hello");
    handler.handleTextMessage(mockSession, message);
    verify(mockSession).sendMessage(any(TextMessage.class));
  }

  @Test
  void testAfterConnectionClosed_LogsClose() throws Exception {
    handler.afterConnectionClosed(mockSession, CloseStatus.NORMAL);
    verify(mockSession).getId();
  }
}
