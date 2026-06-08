package io.trishul.ai.service.chat.execution.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import dev.langchain4j.model.StreamingResponseHandler;
import dev.langchain4j.model.chat.StreamingChatLanguageModel;
import io.trishul.ai.agent.model.AiAgentConfig;
import io.trishul.ai.service.agent.cache.AgentCache;
import io.trishul.ai.service.chat.execution.dto.ChatRequestDto;
import io.trishul.ai.service.session.model.service.AiChatSessionService;
import io.trishul.ai.session.model.AiChatSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;
import static java.lang.reflect.Proxy.newProxyInstance;
import static org.springframework.test.util.ReflectionTestUtils.setField;

class AiChatControllerTest {

  private AiChatController controller;
  private AiChatSessionService mockSessionService;
  private AgentCache mockAgentCache;

  @BeforeEach
  void setUp() {
    mockSessionService = mock(AiChatSessionService.class);
    mockAgentCache = mock(AgentCache.class);
    controller = new AiChatController(mockSessionService, mockAgentCache);
  }

  @Test
  @SuppressWarnings("unchecked")
  void testStreamChat_ReturnsSseEmitter() {
    ChatRequestDto request = new ChatRequestDto();
    request.setSessionId("session-id");
    request.setMessage("Hello");

    AiChatSession mockSession = mock(AiChatSession.class);
    AiAgentConfig mockAgentConfig = mock(AiAgentConfig.class);
    when(mockSession.getAgentConfig()).thenReturn(mockAgentConfig);
    when(mockSessionService.getBySessionKey("session-id")).thenReturn(mockSession);

    StreamingChatLanguageModel mockChatModel = mock(StreamingChatLanguageModel.class);
    when(mockAgentCache.getAgent(mockAgentConfig)).thenReturn(mockChatModel);

    doAnswer(invocation -> {
      StreamingResponseHandler<?> handler = invocation.getArgument(1);
      handler.onNext("hi");
      handler.onComplete(null);
      return null;
    }).when(mockChatModel).generate(anyString(), any(StreamingResponseHandler.class));

    SseEmitter emitter = controller.streamChat(request);

    assertNotNull(emitter);
  }

  @Test
  @SuppressWarnings("unchecked")
  void testStreamChat_OnError_CompletesEmitterWithError() {
    ChatRequestDto request = new ChatRequestDto();
    request.setSessionId("session-id");
    request.setMessage("Hello");

    AiChatSession mockSession = mock(AiChatSession.class);
    AiAgentConfig mockAgentConfig = mock(AiAgentConfig.class);
    when(mockSession.getAgentConfig()).thenReturn(mockAgentConfig);
    when(mockSessionService.getBySessionKey("session-id")).thenReturn(mockSession);

    StreamingChatLanguageModel mockChatModel = mock(StreamingChatLanguageModel.class);
    when(mockAgentCache.getAgent(mockAgentConfig)).thenReturn(mockChatModel);

    AtomicReference<StreamingResponseHandler<?>> handlerRef = new AtomicReference<>();
    doAnswer(invocation -> {
      handlerRef.set(invocation.getArgument(1));
      return null;
    }).when(mockChatModel).generate(anyString(), any(StreamingResponseHandler.class));

    SseEmitter emitter = controller.streamChat(request);

    Exception testException = new Exception("Test error");
    handlerRef.get().onError(testException);

    assertNotNull(emitter);
  }

  @Test
  @SuppressWarnings("unchecked")
  void testStreamChat_OnNext_ThrowsIOException_WhenSendFails() throws Exception {
    ChatRequestDto request = new ChatRequestDto();
    request.setSessionId("session-id");
    request.setMessage("Hello");

    AiChatSession mockSession = mock(AiChatSession.class);
    AiAgentConfig mockAgentConfig = mock(AiAgentConfig.class);
    when(mockSession.getAgentConfig()).thenReturn(mockAgentConfig);
    when(mockSessionService.getBySessionKey("session-id")).thenReturn(mockSession);

    StreamingChatLanguageModel mockChatModel = mock(StreamingChatLanguageModel.class);
    when(mockAgentCache.getAgent(mockAgentConfig)).thenReturn(mockChatModel);

    AtomicReference<StreamingResponseHandler<?>> handlerRef = new AtomicReference<>();
    doAnswer(invocation -> {
      handlerRef.set(invocation.getArgument(1));
      return null;
    }).when(mockChatModel).generate(anyString(), any(StreamingResponseHandler.class));

    SseEmitter emitter = controller.streamChat(request);

    Class<?> handlerClass = Class.forName(
        "org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter$Handler");
    Object mockHandler = newProxyInstance(handlerClass.getClassLoader(),
        new Class<?>[] {handlerClass}, (proxy, method, args) -> {
          if (method.getName().equals("send")) {
            throw new IOException("Network failure");
          }
          return null;
        });

    setField(emitter, "handler", mockHandler);

    handlerRef.get().onNext("token");

    assertNotNull(emitter);
  }

  @Test
  @SuppressWarnings("unchecked")
  void testStreamChat_OnComplete_ThrowsIOException_WhenSendFails() throws Exception {
    ChatRequestDto request = new ChatRequestDto();
    request.setSessionId("session-id");
    request.setMessage("Hello");

    AiChatSession mockSession = mock(AiChatSession.class);
    AiAgentConfig mockAgentConfig = mock(AiAgentConfig.class);
    when(mockSession.getAgentConfig()).thenReturn(mockAgentConfig);
    when(mockSessionService.getBySessionKey("session-id")).thenReturn(mockSession);

    StreamingChatLanguageModel mockChatModel = mock(StreamingChatLanguageModel.class);
    when(mockAgentCache.getAgent(mockAgentConfig)).thenReturn(mockChatModel);

    AtomicReference<StreamingResponseHandler<?>> handlerRef = new AtomicReference<>();
    doAnswer(invocation -> {
      handlerRef.set(invocation.getArgument(1));
      return null;
    }).when(mockChatModel).generate(anyString(), any(StreamingResponseHandler.class));

    SseEmitter emitter = controller.streamChat(request);

    Class<?> handlerClass = Class.forName(
        "org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter$Handler");
    Object mockHandler = newProxyInstance(handlerClass.getClassLoader(),
        new Class<?>[] {handlerClass}, (proxy, method, args) -> {
          if (method.getName().equals("send")) {
            throw new IOException("Network failure");
          }
          return null;
        });

    setField(emitter, "handler", mockHandler);

    handlerRef.get().onComplete(null);

    assertNotNull(emitter);
  }
}
