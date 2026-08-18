package sh.trishul.ai.speech.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import sh.trishul.model.base.dto.BaseDto;

public class AddAiSpeechConfigDto extends BaseDto {
  @NotBlank
  private String name;

  @NotBlank
  private String provider;

  private String ttsModelName;

  private String sttModelName;

  private String voice;

  private Double speed;

  @NotNull
  private Boolean isDefault;

  public AddAiSpeechConfigDto() {}

  public AddAiSpeechConfigDto(String name, String provider, String ttsModelName,
      String sttModelName, String voice, Double speed, Boolean isDefault) {
    setName(name);
    setProvider(provider);
    setTtsModelName(ttsModelName);
    setSttModelName(sttModelName);
    setVoice(voice);
    setSpeed(speed);
    setIsDefault(isDefault);
  }

  public String getName() {
    return name;
  }

  public AddAiSpeechConfigDto setName(String name) {
    this.name = name;
    return this;
  }

  public String getProvider() {
    return provider;
  }

  public AddAiSpeechConfigDto setProvider(String provider) {
    this.provider = provider;
    return this;
  }

  public String getTtsModelName() {
    return ttsModelName;
  }

  public AddAiSpeechConfigDto setTtsModelName(String ttsModelName) {
    this.ttsModelName = ttsModelName;
    return this;
  }

  public String getSttModelName() {
    return sttModelName;
  }

  public AddAiSpeechConfigDto setSttModelName(String sttModelName) {
    this.sttModelName = sttModelName;
    return this;
  }

  public String getVoice() {
    return voice;
  }

  public AddAiSpeechConfigDto setVoice(String voice) {
    this.voice = voice;
    return this;
  }

  public Double getSpeed() {
    return speed;
  }

  public AddAiSpeechConfigDto setSpeed(Double speed) {
    this.speed = speed;
    return this;
  }

  public Boolean getIsDefault() {
    return isDefault;
  }

  public AddAiSpeechConfigDto setIsDefault(Boolean isDefault) {
    this.isDefault = isDefault;
    return this;
  }
}
