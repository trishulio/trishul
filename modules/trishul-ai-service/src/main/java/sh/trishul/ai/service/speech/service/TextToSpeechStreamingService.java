package sh.trishul.ai.service.speech.service;

import java.util.SortedSet;
import org.springframework.data.domain.Page;
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

  public Page<Object> search(String query, SortedSet<String> sort, boolean orderAscending, int page,
      int size) {
    return Page.empty();
  }
}
