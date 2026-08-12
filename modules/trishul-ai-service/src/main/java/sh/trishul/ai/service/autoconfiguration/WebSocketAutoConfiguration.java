package sh.trishul.ai.service.autoconfiguration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import sh.trishul.ai.service.speech.websocket.ChatWebSocketHandler;

@Configuration
@EnableWebSocket
public class WebSocketAutoConfiguration implements WebSocketConfigurer {

  @Value("${ai.websocket.allowed-origins:*}")
  private String[] allowedOrigins;

  @Bean
  @ConditionalOnMissingBean(ChatWebSocketHandler.class)
  public ChatWebSocketHandler chatWebSocketHandler() {
    return new ChatWebSocketHandler();
  }

  @Override
  public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
    registry.addHandler(chatWebSocketHandler(), "/api/v1/ai/chat/stream")
        .setAllowedOrigins(allowedOrigins);
  }
}
