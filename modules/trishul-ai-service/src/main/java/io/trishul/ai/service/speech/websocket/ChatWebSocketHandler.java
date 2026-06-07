package io.trishul.ai.service.speech.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 * ChatWebSocketHandler handles real-time streaming of speech-to-text (STT) and text-to-speech (TTS)
 * chunks. Integrates with WebRTCSpeechService and AgentFactory.
 */
public class ChatWebSocketHandler extends TextWebSocketHandler {

  private static final Logger log = LoggerFactory.getLogger(ChatWebSocketHandler.class);

  @Override
  public void afterConnectionEstablished(WebSocketSession session) throws Exception {
    log.debug("WebSocket connection established: {}", session.getId());
  }

  @Override
  protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
    // Expected to receive JSON with audio chunks or text, run through Agent,
    // and stream back response chunks.
    session.sendMessage(new TextMessage("Ack: " + message.getPayloadLength() + " bytes"));
  }

  @Override
  public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
    log.debug("WebSocket connection closed: {}", session.getId());
  }
}
