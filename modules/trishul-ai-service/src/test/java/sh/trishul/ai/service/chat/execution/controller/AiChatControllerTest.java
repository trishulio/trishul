package sh.trishul.ai.service.chat.execution.controller;

import static java.lang.reflect.Proxy.newProxyInstance;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.ReflectionTestUtils.setField;

import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.ImageContent;
import dev.langchain4j.data.message.TextContent;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.output.Response;
import dev.langchain4j.service.TokenStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import sh.trishul.ai.agent.model.AiAgentConfig;
import sh.trishul.ai.service.agent.Assistant;
import sh.trishul.ai.service.agent.cache.AgentCache;
import sh.trishul.ai.service.chat.execution.dto.ChatMessageContentDto;
import sh.trishul.ai.service.chat.execution.dto.ChatRequestDto;
import sh.trishul.ai.service.session.model.service.AiChatSessionService;
import sh.trishul.ai.session.model.AiChatSession;

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
  void testStreamChat_ReturnsSseEmitter() throws Exception {
    ChatRequestDto request = new ChatRequestDto();
    request.setSessionId("session-id");
    request.setMessage("Hello");

    AiChatSession mockSession = mock(AiChatSession.class);
    AiAgentConfig mockAgentConfig = mock(AiAgentConfig.class);
    when(mockSession.getAgentConfig()).thenReturn(mockAgentConfig);
    when(mockSessionService.getBySessionKey("session-id")).thenReturn(mockSession);

    Assistant mockAssistant = mock(Assistant.class);
    when(mockAgentCache.getAgent(mockAgentConfig)).thenReturn(mockAssistant);

    TokenStream mockTokenStream = mock(TokenStream.class);
    when(mockAssistant.streamChat(any(), any(UserMessage.class))).thenReturn(mockTokenStream);

    AtomicReference<Consumer<String>> onNextRef = new AtomicReference<>();
    AtomicReference<Consumer<Response<AiMessage>>> onCompleteRef = new AtomicReference<>();

    when(mockTokenStream.onPartialResponse(any(Consumer.class))).thenAnswer(invocation -> {
      onNextRef.set(invocation.getArgument(0));
      return mockTokenStream;
    });
    when(mockTokenStream.onCompleteResponse(any(Consumer.class))).thenAnswer(invocation -> {
      onCompleteRef.set(invocation.getArgument(0));
      return mockTokenStream;
    });
    when(mockTokenStream.onError(any(Consumer.class))).thenReturn(mockTokenStream);

    doAnswer(invocation -> {
      return null;
    }).when(mockTokenStream).start();

    SseEmitter emitter = controller.streamChat(request);

    assertNotNull(emitter);

    Class<?> handlerClass = Class.forName(ResponseBodyEmitter.class.getName() + "$Handler");
    List<String> sentData = new ArrayList<>();
    AtomicBoolean completed = new AtomicBoolean(false);
    Object mockHandler = newProxyInstance(handlerClass.getClassLoader(),
        new Class<?>[] {handlerClass}, (proxy, method, args) -> {
          if (method.getName().equals("send")) {
            if (args[0] instanceof java.util.Set) {
              for (Object entry : (java.util.Set<?>) args[0]) {
                if (entry instanceof ResponseBodyEmitter.DataWithMediaType) {
                  Object data = ((ResponseBodyEmitter.DataWithMediaType) entry).getData();
                  if (data != null) {
                    sentData.add(data.toString());
                  }
                }
              }
            } else if (args[0] != null) {
              sentData.add(args[0].toString());
            }
          }
          if (method.getName().equals("complete")) {
            completed.set(true);
          }
          return null;
        });

    setField(emitter, "handler", mockHandler);

    onNextRef.get().accept("hi");
    onCompleteRef.get().accept(null);

    assertEquals(6, sentData.size());
    assertTrue(sentData.stream().anyMatch(d -> d.contains("hi")));
    assertTrue(sentData.stream().anyMatch(d -> d.contains("[DONE]")));
    assertTrue(completed.get());
    verify(mockTokenStream).start();
  }

  @Test
  @SuppressWarnings("unchecked")
  void testStreamChat_OnError_CompletesEmitterWithError() throws Exception {
    ChatRequestDto request = new ChatRequestDto();
    request.setSessionId("session-id");
    request.setMessage("Hello");

    AiChatSession mockSession = mock(AiChatSession.class);
    AiAgentConfig mockAgentConfig = mock(AiAgentConfig.class);
    when(mockSession.getAgentConfig()).thenReturn(mockAgentConfig);
    when(mockSessionService.getBySessionKey("session-id")).thenReturn(mockSession);

    Assistant mockAssistant = mock(Assistant.class);
    when(mockAgentCache.getAgent(mockAgentConfig)).thenReturn(mockAssistant);

    TokenStream mockTokenStream = mock(TokenStream.class);
    when(mockAssistant.streamChat(any(), any(UserMessage.class))).thenReturn(mockTokenStream);

    AtomicReference<Consumer<Throwable>> onErrorRef = new AtomicReference<>();

    when(mockTokenStream.onPartialResponse(any(Consumer.class))).thenReturn(mockTokenStream);
    when(mockTokenStream.onCompleteResponse(any(Consumer.class))).thenReturn(mockTokenStream);
    when(mockTokenStream.onError(any(Consumer.class))).thenAnswer(invocation -> {
      onErrorRef.set(invocation.getArgument(0));
      return mockTokenStream;
    });

    doAnswer(invocation -> {
      return null;
    }).when(mockTokenStream).start();

    SseEmitter emitter = controller.streamChat(request);

    Class<?> handlerClass = Class.forName(ResponseBodyEmitter.class.getName() + "$Handler");
    AtomicReference<Throwable> completeErrorRef = new AtomicReference<>();
    Object mockHandler = newProxyInstance(handlerClass.getClassLoader(),
        new Class<?>[] {handlerClass}, (proxy, method, args) -> {
          if (method.getName().equals("completeWithError")) {
            completeErrorRef.set((Throwable) args[0]);
          }
          return null;
        });

    setField(emitter, "handler", mockHandler);

    Exception testException = new Exception("Test error");
    onErrorRef.get().accept(testException);

    assertNotNull(emitter);
    assertNotNull(completeErrorRef.get());
    assertSame(testException, completeErrorRef.get());
    verify(mockTokenStream).start();
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

    Assistant mockAssistant = mock(Assistant.class);
    when(mockAgentCache.getAgent(mockAgentConfig)).thenReturn(mockAssistant);

    TokenStream mockTokenStream = mock(TokenStream.class);
    when(mockAssistant.streamChat(any(), any(UserMessage.class))).thenReturn(mockTokenStream);

    AtomicReference<Consumer<String>> onNextRef = new AtomicReference<>();

    when(mockTokenStream.onPartialResponse(any(Consumer.class))).thenAnswer(invocation -> {
      onNextRef.set(invocation.getArgument(0));
      return mockTokenStream;
    });
    when(mockTokenStream.onCompleteResponse(any(Consumer.class))).thenReturn(mockTokenStream);
    when(mockTokenStream.onError(any(Consumer.class))).thenReturn(mockTokenStream);

    doAnswer(invocation -> {
      return null;
    }).when(mockTokenStream).start();

    SseEmitter emitter = controller.streamChat(request);

    Class<?> handlerClass = Class.forName(ResponseBodyEmitter.class.getName() + "$Handler");
    AtomicReference<Throwable> completeErrorRef = new AtomicReference<>();
    Object mockHandler = newProxyInstance(handlerClass.getClassLoader(),
        new Class<?>[] {handlerClass}, (proxy, method, args) -> {
          if (method.getName().equals("send")) {
            throw new IOException("Network failure");
          }
          if (method.getName().equals("completeWithError")) {
            completeErrorRef.set((Throwable) args[0]);
          }
          return null;
        });

    setField(emitter, "handler", mockHandler);

    onNextRef.get().accept("token");

    assertNotNull(emitter);
    assertNotNull(completeErrorRef.get());
    assertEquals("Network failure", completeErrorRef.get().getMessage());
    verify(mockTokenStream).start();
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

    Assistant mockAssistant = mock(Assistant.class);
    when(mockAgentCache.getAgent(mockAgentConfig)).thenReturn(mockAssistant);

    TokenStream mockTokenStream = mock(TokenStream.class);
    when(mockAssistant.streamChat(any(), any(UserMessage.class))).thenReturn(mockTokenStream);

    AtomicReference<Consumer<Response<AiMessage>>> onCompleteRef = new AtomicReference<>();

    when(mockTokenStream.onPartialResponse(any(Consumer.class))).thenReturn(mockTokenStream);
    when(mockTokenStream.onCompleteResponse(any(Consumer.class))).thenAnswer(invocation -> {
      onCompleteRef.set(invocation.getArgument(0));
      return mockTokenStream;
    });
    when(mockTokenStream.onError(any(Consumer.class))).thenReturn(mockTokenStream);

    doAnswer(invocation -> {
      return null;
    }).when(mockTokenStream).start();

    SseEmitter emitter = controller.streamChat(request);

    Class<?> handlerClass = Class.forName(ResponseBodyEmitter.class.getName() + "$Handler");
    AtomicReference<Throwable> completeErrorRef = new AtomicReference<>();
    Object mockHandler = newProxyInstance(handlerClass.getClassLoader(),
        new Class<?>[] {handlerClass}, (proxy, method, args) -> {
          if (method.getName().equals("send")) {
            throw new IOException("Network failure");
          }
          if (method.getName().equals("completeWithError")) {
            completeErrorRef.set((Throwable) args[0]);
          }
          return null;
        });

    setField(emitter, "handler", mockHandler);

    onCompleteRef.get().accept(null);

    assertNotNull(emitter);
    assertNotNull(completeErrorRef.get());
    assertEquals("Network failure", completeErrorRef.get().getMessage());
    verify(mockTokenStream).start();
  }

  @Test
  @SuppressWarnings("unchecked")
  void testStreamChat_WithTextAndImageContents_BuildsCorrectUserMessage() throws Exception {
    ChatRequestDto request = new ChatRequestDto();
    request.setSessionId("session-id");
    request.setMessage("Fallback message");

    ChatMessageContentDto textContent = new ChatMessageContentDto();
    textContent.setType(ChatMessageContentDto.Type.TEXT);
    textContent.setText("Hello text");

    ChatMessageContentDto imageUrlContent = new ChatMessageContentDto();
    imageUrlContent.setType(ChatMessageContentDto.Type.IMAGE);
    imageUrlContent.setUrl("http://example.com/image.png");

    ChatMessageContentDto imageBase64Content = new ChatMessageContentDto();
    imageBase64Content.setType(ChatMessageContentDto.Type.IMAGE);
    imageBase64Content.setBase64Data("data");
    imageBase64Content.setMimeType("image/png");

    ChatMessageContentDto invalidImageContent = new ChatMessageContentDto();
    invalidImageContent.setType(ChatMessageContentDto.Type.IMAGE);

    request.setContents(
        List.of(textContent, imageUrlContent, imageBase64Content, invalidImageContent));

    AiChatSession mockSession = mock(AiChatSession.class);
    AiAgentConfig mockAgentConfig = mock(AiAgentConfig.class);
    when(mockSession.getAgentConfig()).thenReturn(mockAgentConfig);
    when(mockSessionService.getBySessionKey("session-id")).thenReturn(mockSession);

    Assistant mockAssistant = mock(Assistant.class);
    when(mockAgentCache.getAgent(mockAgentConfig)).thenReturn(mockAssistant);

    TokenStream mockTokenStream = mock(TokenStream.class);
    ArgumentCaptor<UserMessage> userMessageCaptor = ArgumentCaptor.forClass(UserMessage.class);
    when(mockAssistant.streamChat(any(), userMessageCaptor.capture())).thenReturn(mockTokenStream);

    when(mockTokenStream.onPartialResponse(any(Consumer.class))).thenReturn(mockTokenStream);
    when(mockTokenStream.onCompleteResponse(any(Consumer.class))).thenReturn(mockTokenStream);
    when(mockTokenStream.onError(any(Consumer.class))).thenReturn(mockTokenStream);

    SseEmitter emitter = controller.streamChat(request);

    assertNotNull(emitter);
    UserMessage capturedMessage = userMessageCaptor.getValue();
    assertNotNull(capturedMessage);
    assertEquals(3, capturedMessage.contents().size());
    assertTrue(capturedMessage.contents().get(0) instanceof TextContent);
    assertEquals("Hello text", ((TextContent) capturedMessage.contents().get(0)).text());
    assertTrue(capturedMessage.contents().get(1) instanceof ImageContent);
    assertTrue(capturedMessage.contents().get(2) instanceof ImageContent);
  }

  @Test
  @SuppressWarnings("unchecked")
  void testStreamChat_WithEmptyContentsList_BuildsTextUserMessage() throws Exception {
    ChatRequestDto request = new ChatRequestDto();
    request.setSessionId("session-id");
    request.setMessage("Hello fallback");
    request.setContents(new ArrayList<>());

    AiChatSession mockSession = mock(AiChatSession.class);
    AiAgentConfig mockAgentConfig = mock(AiAgentConfig.class);
    when(mockSession.getAgentConfig()).thenReturn(mockAgentConfig);
    when(mockSessionService.getBySessionKey("session-id")).thenReturn(mockSession);

    Assistant mockAssistant = mock(Assistant.class);
    when(mockAgentCache.getAgent(mockAgentConfig)).thenReturn(mockAssistant);

    TokenStream mockTokenStream = mock(TokenStream.class);
    ArgumentCaptor<UserMessage> userMessageCaptor = ArgumentCaptor.forClass(UserMessage.class);
    when(mockAssistant.streamChat(any(), userMessageCaptor.capture())).thenReturn(mockTokenStream);

    when(mockTokenStream.onPartialResponse(any(Consumer.class))).thenReturn(mockTokenStream);
    when(mockTokenStream.onCompleteResponse(any(Consumer.class))).thenReturn(mockTokenStream);
    when(mockTokenStream.onError(any(Consumer.class))).thenReturn(mockTokenStream);

    SseEmitter emitter = controller.streamChat(request);

    assertNotNull(emitter);
    UserMessage capturedMessage = userMessageCaptor.getValue();
    assertNotNull(capturedMessage);
    assertEquals("Hello fallback", capturedMessage.singleText());
  }

  @Test
  @SuppressWarnings("unchecked")
  void testStreamChat_WithOnlyInvalidContentsAndMessage_BuildsTextUserMessage() throws Exception {
    ChatRequestDto request = new ChatRequestDto();
    request.setSessionId("session-id");
    request.setMessage("Hello invalid fallback");

    ChatMessageContentDto invalidImageContent = new ChatMessageContentDto();
    invalidImageContent.setType(ChatMessageContentDto.Type.IMAGE);

    request.setContents(List.of(invalidImageContent));

    AiChatSession mockSession = mock(AiChatSession.class);
    AiAgentConfig mockAgentConfig = mock(AiAgentConfig.class);
    when(mockSession.getAgentConfig()).thenReturn(mockAgentConfig);
    when(mockSessionService.getBySessionKey("session-id")).thenReturn(mockSession);

    Assistant mockAssistant = mock(Assistant.class);
    when(mockAgentCache.getAgent(mockAgentConfig)).thenReturn(mockAssistant);

    TokenStream mockTokenStream = mock(TokenStream.class);
    ArgumentCaptor<UserMessage> userMessageCaptor = ArgumentCaptor.forClass(UserMessage.class);
    when(mockAssistant.streamChat(any(), userMessageCaptor.capture())).thenReturn(mockTokenStream);

    when(mockTokenStream.onPartialResponse(any(Consumer.class))).thenReturn(mockTokenStream);
    when(mockTokenStream.onCompleteResponse(any(Consumer.class))).thenReturn(mockTokenStream);
    when(mockTokenStream.onError(any(Consumer.class))).thenReturn(mockTokenStream);

    SseEmitter emitter = controller.streamChat(request);

    assertNotNull(emitter);
    UserMessage capturedMessage = userMessageCaptor.getValue();
    assertNotNull(capturedMessage);
    assertEquals("Hello invalid fallback", capturedMessage.singleText());
  }
}
