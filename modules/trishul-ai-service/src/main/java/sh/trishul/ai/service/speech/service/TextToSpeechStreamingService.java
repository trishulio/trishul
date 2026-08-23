package sh.trishul.ai.service.speech.service;

import org.springframework.stereotype.Service;

/**
 * Service for handling real-time Text-to-Speech audio streaming chunks.
 */
@Service
public class TextToSpeechStreamingService {

  public byte[] streamChunk(String textChunk) {
    // In a real application, this would interface with a streaming TTS provider
    // and yield byte arrays of PCM audio data as quickly as possible.
    return new byte[0];
  }

  public org.springframework.data.domain.Page<Object> search(String query,
      java.util.SortedSet<String> sort, boolean orderAscending, int page, int size) {
    return org.springframework.data.domain.Page.empty();
  }
}
