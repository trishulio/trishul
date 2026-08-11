package io.trishul.ai.service.speech.service;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

class WebRTCSpeechServiceTest {

  private WebRTCSpeechService service;
  private RestTemplate mockRestTemplate;

  @BeforeEach
  void setUp() {
    mockRestTemplate = mock(RestTemplate.class);
    service = new WebRTCSpeechService();
    ReflectionTestUtils.setField(service, "restTemplate", mockRestTemplate);
  }

  @Test
  void testTextToSpeech_ReturnsEmptyArray_WhenApiKeyIsNull() {
    ReflectionTestUtils.setField(service, "openAiApiKey", null);
    byte[] result = service.textToSpeech("hello", "alloy");
    assertArrayEquals(new byte[0], result);
  }

  @Test
  void testTextToSpeech_ReturnsEmptyArray_WhenApiKeyIsEmpty() {
    ReflectionTestUtils.setField(service, "openAiApiKey", "");
    byte[] result = service.textToSpeech("hello", "alloy");
    assertArrayEquals(new byte[0], result);
  }

  @Test
  void testTextToSpeech_ReturnsAudioBytes_WhenApiKeyIsValid() {
    ReflectionTestUtils.setField(service, "openAiApiKey", "mock-api-key");
    byte[] expectedAudio = new byte[] {1, 2, 3};
    ResponseEntity<byte[]> mockResponse = ResponseEntity.ok(expectedAudio);

    when(mockRestTemplate.postForEntity(eq("https://api.openai.com/v1/audio/speech"),
        any(HttpEntity.class), eq(byte[].class))).thenAnswer(invocation -> {
          HttpEntity<Map<String, Object>> entity = invocation.getArgument(1);
          assertEquals("Bearer mock-api-key", entity.getHeaders().getFirst("Authorization"));
          assertEquals(MediaType.APPLICATION_JSON, entity.getHeaders().getContentType());
          assertEquals("tts-1", entity.getBody().get("model"));
          assertEquals("hello", entity.getBody().get("input"));
          assertEquals("alloy", entity.getBody().get("voice"));
          return mockResponse;
        });

    byte[] result = service.textToSpeech("hello", "alloy");
    assertArrayEquals(expectedAudio, result);
  }

  @Test
  void testTextToSpeech_UsesDefaultVoice_WhenVoiceIsNull() {
    ReflectionTestUtils.setField(service, "openAiApiKey", "mock-api-key");
    byte[] expectedAudio = new byte[] {1, 2, 3};
    ResponseEntity<byte[]> mockResponse = ResponseEntity.ok(expectedAudio);

    when(mockRestTemplate.postForEntity(eq("https://api.openai.com/v1/audio/speech"),
        any(HttpEntity.class), eq(byte[].class))).thenAnswer(invocation -> {
          HttpEntity<Map<String, Object>> entity = invocation.getArgument(1);
          assertEquals("alloy", entity.getBody().get("voice"));
          return mockResponse;
        });

    byte[] result = service.textToSpeech("hello", null);
    assertArrayEquals(expectedAudio, result);
  }

  @Test
  void testSpeechToText_ReturnsEmptyString_WhenApiKeyIsNull() {
    ReflectionTestUtils.setField(service, "openAiApiKey", null);
    String result = service.speechToText(new byte[] {1, 2, 3});
    assertEquals("", result);
  }

  @Test
  void testSpeechToText_ReturnsEmptyString_WhenApiKeyIsEmpty() {
    ReflectionTestUtils.setField(service, "openAiApiKey", "");
    String result = service.speechToText(new byte[] {1, 2, 3});
    assertEquals("", result);
  }

  @Test
  void testSpeechToText_ReturnsTranscribedText_WhenApiKeyIsValid() {
    ReflectionTestUtils.setField(service, "openAiApiKey", "mock-api-key");
    Map<String, String> mockBody = new HashMap<>();
    mockBody.put("text", "Hello World");
    ResponseEntity<Map> mockResponse = ResponseEntity.ok(mockBody);

    when(mockRestTemplate.postForEntity(eq("https://api.openai.com/v1/audio/transcriptions"),
        any(HttpEntity.class), eq(Map.class))).thenAnswer(invocation -> {
          HttpEntity<LinkedMultiValueMap<String, Object>> entity = invocation.getArgument(1);
          assertEquals("Bearer mock-api-key", entity.getHeaders().getFirst("Authorization"));
          assertEquals(MediaType.MULTIPART_FORM_DATA, entity.getHeaders().getContentType());
          assertEquals("whisper-1", entity.getBody().getFirst("model"));
          ByteArrayResource resource = (ByteArrayResource) entity.getBody().getFirst("file");
          assertEquals("audio.webm", resource.getFilename());
          return mockResponse;
        });

    String result = service.speechToText(new byte[] {1, 2, 3});
    assertEquals("Hello World", result);
  }

  @Test
  void testSpeechToText_ReturnsEmptyString_WhenResponseIsEmpty() {
    ReflectionTestUtils.setField(service, "openAiApiKey", "mock-api-key");
    ResponseEntity<Map> mockResponse = ResponseEntity.ok(new HashMap<>());

    when(mockRestTemplate.postForEntity(eq("https://api.openai.com/v1/audio/transcriptions"),
        any(HttpEntity.class), eq(Map.class))).thenReturn(mockResponse);

    String result = service.speechToText(new byte[] {1, 2, 3});
    assertEquals("", result);
  }

  @Test
  void testSpeechToText_ReturnsEmptyString_WhenResponseBodyIsNull() {
    ReflectionTestUtils.setField(service, "openAiApiKey", "mock-api-key");
    ResponseEntity<Map> mockResponse = ResponseEntity.ok(null);

    when(mockRestTemplate.postForEntity(eq("https://api.openai.com/v1/audio/transcriptions"),
        any(HttpEntity.class), eq(Map.class))).thenReturn(mockResponse);

    String result = service.speechToText(new byte[] {1, 2, 3});
    assertEquals("", result);
  }
}
