package sh.trishul.ai.speech.model;

public interface BaseAiSpeechConfig<T extends BaseAiSpeechConfig<T>> {
  String ATTR_NAME = "name";
  String ATTR_PROVIDER = "provider";
  String ATTR_TTS_MODEL_NAME = "ttsModelName";
  String ATTR_STT_MODEL_NAME = "sttModelName";
  String ATTR_VOICE = "voice";
  String ATTR_SPEED = "speed";
  String ATTR_IS_DEFAULT = "isDefault";

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
