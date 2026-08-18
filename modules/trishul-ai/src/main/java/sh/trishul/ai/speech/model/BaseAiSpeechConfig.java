package sh.trishul.ai.speech.model;

public interface BaseAiSpeechConfig<T extends BaseAiSpeechConfig<T>> {
  final String ATTR_NAME = "name";
  final String ATTR_PROVIDER = "provider";
  final String ATTR_TTS_MODEL_NAME = "ttsModelName";
  final String ATTR_STT_MODEL_NAME = "sttModelName";
  final String ATTR_VOICE = "voice";
  final String ATTR_SPEED = "speed";
  final String ATTR_IS_DEFAULT = "isDefault";

  String getName();

  T setName(String name);

  String getProvider();

  T setProvider(String provider);

  String getTtsModelName();

  T setTtsModelName(String ttsModelName);

  String getSttModelName();

  T setSttModelName(String sttModelName);

  String getVoice();

  T setVoice(String voice);

  Double getSpeed();

  T setSpeed(Double speed);

  Boolean getIsDefault();

  T setIsDefault(Boolean isDefault);
}
