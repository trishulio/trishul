package sh.trishul.ai.service.chat.execution.controller;

import dev.langchain4j.data.message.Content;
import dev.langchain4j.data.message.ImageContent;
import dev.langchain4j.data.message.TextContent;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.service.TokenStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import sh.trishul.ai.service.agent.Assistant;
import sh.trishul.ai.service.agent.cache.AgentCache;
import sh.trishul.ai.service.chat.execution.dto.ChatMessageContentDto;
import sh.trishul.ai.service.chat.execution.dto.ChatRequestDto;
import sh.trishul.ai.service.session.model.service.AiChatSessionService;
import sh.trishul.ai.session.model.AiChatSession;

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
    Assistant assistant = (Assistant) agentCache.getAgent(session.getAgentConfig());
    UserMessage userMessage = buildUserMessage(request);

    TokenStream tokenStream = assistant.streamChat(session.getId(), userMessage);
    tokenStream.onPartialResponse(token -> {
      try {
        emitter.send(SseEmitter.event().data(token));
      } catch (IOException e) {
        log.error("Failed to send SSE event", e);
        emitter.completeWithError(e);
      }
    }).onCompleteResponse(response -> {
      try {
        emitter.send(SseEmitter.event().data("[DONE]"));
        emitter.complete();
      } catch (IOException e) {
        log.error("Error completing SSE stream", e);
        emitter.completeWithError(e);
      }
    }).onError(error -> {
      log.error("Error generating chat response", error);
      emitter.completeWithError(error);
    }).start();

    return emitter;
  }

  private UserMessage buildUserMessage(ChatRequestDto request) {
    if (request.getContents() == null || request.getContents().isEmpty()) {
      return UserMessage.from(request.getMessage());
    }

    List<Content> contents = new ArrayList<>();
    for (ChatMessageContentDto contentDto : request.getContents()) {
      if (contentDto.getType() == ChatMessageContentDto.Type.TEXT) {
        contents.add(TextContent.from(contentDto.getText()));
      } else if (contentDto.getType() == ChatMessageContentDto.Type.IMAGE) {
        if (contentDto.getUrl() != null && !contentDto.getUrl().isEmpty()) {
          contents.add(ImageContent.from(contentDto.getUrl()));
        } else if (contentDto.getBase64Data() != null && !contentDto.getBase64Data().isEmpty()) {
          contents.add(ImageContent.from(contentDto.getBase64Data(), contentDto.getMimeType()));
        }
      }
    }

    if (contents.isEmpty() && request.getMessage() != null) {
      return UserMessage.from(request.getMessage());
    }

    return UserMessage.from(contents);
  }

  @org.springframework.web.bind.annotation.GetMapping(value = "/search", consumes = MediaType.ALL_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public sh.trishul.repo.jpa.repository.model.dto.PageDto<Object> search(
      @org.springframework.web.bind.annotation.RequestParam(name = "q", required = false) String query,
      @org.springframework.web.bind.annotation.RequestParam(name = "page", defaultValue = "0") int page,
      @org.springframework.web.bind.annotation.RequestParam(name = "size", defaultValue = "100") int size,
      @org.springframework.web.bind.annotation.RequestParam(name = "sort",
          defaultValue = "id") java.util.SortedSet<String> sort,
      @org.springframework.web.bind.annotation.RequestParam(name = "order_asc",
          defaultValue = "true") boolean orderAscending) {
    return new sh.trishul.repo.jpa.repository.model.dto.PageDto<>(new ArrayList<>(), 0, 0);
  }
}
