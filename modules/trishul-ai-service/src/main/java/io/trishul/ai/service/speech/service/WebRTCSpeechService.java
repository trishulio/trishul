package io.trishul.ai.service.speech.service;

import java.util.HashMap;
import java.util.Map;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * WebRTCSpeechService. Interfaces with a Speech provider (like OpenAI Whisper/TTS) to stream audio
 * in and out.
 */
@Service
public class WebRTCSpeechService {

  private final RestTemplate restTemplate;
  private final String openAiApiKey = System.getenv("OPENAI_API_KEY");

  public WebRTCSpeechService() {
    this.restTemplate = new RestTemplate();
  }

  public byte[] textToSpeech(String text, String voice) {
    if (openAiApiKey == null || openAiApiKey.isEmpty()) {
      return new byte[0]; // Fallback if no API key
    }

    HttpHeaders headers = new HttpHeaders();
    headers.setBearerAuth(openAiApiKey);
    headers.setContentType(MediaType.APPLICATION_JSON);

    Map<String, Object> body = new HashMap<>();
    body.put("model", "tts-1");
    body.put("input", text);
    body.put("voice", voice != null ? voice : "alloy");

    HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);
    ResponseEntity<byte[]> response = restTemplate
        .postForEntity("https://api.openai.com/v1/audio/speech", entity, byte[].class);

    return response.getBody();
  }

  public String speechToText(byte[] audioData) {
    if (openAiApiKey == null || openAiApiKey.isEmpty()) {
      return ""; // Fallback if no API key
    }

    HttpHeaders headers = new HttpHeaders();
    headers.setBearerAuth(openAiApiKey);
    headers.setContentType(MediaType.MULTIPART_FORM_DATA);

    LinkedMultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
    body.add("model", "whisper-1");
    body.add("file", new ByteArrayResource(audioData) {
      @Override
      public String getFilename() {
        return "audio.webm";
      }
    });

    HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
    ResponseEntity<Map> response = restTemplate
        .postForEntity("https://api.openai.com/v1/audio/transcriptions", requestEntity, Map.class);

    Map<?, ?> responseBody = response.getBody();
    if (responseBody != null && responseBody.containsKey("text")) {
      return (String) responseBody.get("text");
    }
    return "";
  }
}
