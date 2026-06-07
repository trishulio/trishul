package io.trishul.ai.service.chat.execution.controller;

import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.model.StreamingResponseHandler;
import dev.langchain4j.model.chat.StreamingChatLanguageModel;
import dev.langchain4j.model.output.Response;
import io.trishul.ai.service.agent.cache.AgentCache;
import io.trishul.ai.service.chat.execution.dto.ChatRequestDto;
import io.trishul.ai.service.session.model.service.AiChatSessionService;
import io.trishul.ai.session.model.AiChatSession;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping("/api/v1/ai/chat")
public class AiChatController {

  private static final Logger log = LoggerFactory.getLogger(AiChatController.class);

  private final AiChatSessionService sessionService;
  private final AgentCache agentCache;

  @Autowired
  public AiChatController(AiChatSessionService sessionService, AgentCache agentCache) {
    this.sessionService = sessionService;
    this.agentCache = agentCache;
  }

  @PostMapping(value = "/stream", consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.TEXT_EVENT_STREAM_VALUE)
  public SseEmitter streamChat(@RequestBody ChatRequestDto request) {
    SseEmitter emitter = new SseEmitter(180000L); // 3 minutes timeout

    AiChatSession session = sessionService.getBySessionKey(request.getSessionId());
    StreamingChatLanguageModel chatModel = (StreamingChatLanguageModel) agentCache
        .getAgent(session.getAgentConfig());

    chatModel.generate(request.getMessage(), new StreamingResponseHandler<AiMessage>() {
      @Override
      public void onNext(String token) {
        try {
          emitter.send(SseEmitter.event().data(token));
        } catch (IOException e) {
          log.error("Failed to send SSE event", e);
          emitter.completeWithError(e);
        }
      }

      @Override
      public void onComplete(Response<AiMessage> response) {
        try {
          // Send a [DONE] marker if needed, or simply complete the emitter
          emitter.send(SseEmitter.event().data("[DONE]"));
          emitter.complete();
        } catch (IOException e) {
          log.error("Error completing SSE stream", e);
          emitter.completeWithError(e);
        }
      }

      @Override
      public void onError(Throwable error) {
        log.error("Error generating chat response", error);
        emitter.completeWithError(error);
      }
    });

    return emitter;
  }
}
