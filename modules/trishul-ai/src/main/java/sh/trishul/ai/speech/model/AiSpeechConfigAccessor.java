package sh.trishul.ai.speech.model;

public interface AiSpeechConfigAccessor<T extends AiSpeechConfigAccessor<T>> {
  final String ATTR_SPEECH_CONFIG = "speechConfig";

  AiSpeechConfig getSpeechConfig();

  T setSpeechConfig(AiSpeechConfig speechConfig);
}
