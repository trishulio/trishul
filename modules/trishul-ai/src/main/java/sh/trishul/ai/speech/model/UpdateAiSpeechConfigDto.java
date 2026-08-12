package sh.trishul.ai.speech.model;

import jakarta.validation.constraints.NotNull;
import sh.trishul.model.base.dto.BaseDto;
import sh.trishul.model.validation.NullOrNotBlank;

public class UpdateAiSpeechConfigDto extends BaseDto {
  private Long id;

  @NullOrNotBlank
  private String name;

  @NullOrNotBlank
  private String provider;

  private String ttsModelName;

  private String sttModelName;

  private String voice;

  private Double speed;

  private Boolean isDefault;

  @NotNull
  private Integer version;

  public UpdateAiSpeechConfigDto() {}

  public UpdateAiSpeechConfigDto(Long id) {
    this();
    setId(id);
  }

  public UpdateAiSpeechConfigDto(Long id, String name, String provider, String ttsModelName,
      String sttModelName, String voice, Double speed, Boolean isDefault,
      @NotNull Integer version) {
    this(id);
    setName(name);
    setProvider(provider);
    setTtsModelName(ttsModelName);
    setSttModelName(sttModelName);
    setVoice(voice);
    setSpeed(speed);
    setIsDefault(isDefault);
    setVersion(version);
  }

  public Long getId() {
    return id;
  }

  public UpdateAiSpeechConfigDto setId(Long id) {
    this.id = id;
    return this;
  }

  public String getName() {
    return name;
  }

  public UpdateAiSpeechConfigDto setName(String name) {
    this.name = name;
    return this;
  }

  public String getProvider() {
    return provider;
  }

  public UpdateAiSpeechConfigDto setProvider(String provider) {
    this.provider = provider;
    return this;
  }

  public String getTtsModelName() {
    return ttsModelName;
  }

  public UpdateAiSpeechConfigDto setTtsModelName(String ttsModelName) {
    this.ttsModelName = ttsModelName;
    return this;
  }

  public String getSttModelName() {
    return sttModelName;
  }

  public UpdateAiSpeechConfigDto setSttModelName(String sttModelName) {
    this.sttModelName = sttModelName;
    return this;
  }

  public String getVoice() {
    return voice;
  }

  public UpdateAiSpeechConfigDto setVoice(String voice) {
    this.voice = voice;
    return this;
  }

  public Double getSpeed() {
    return speed;
  }

  public UpdateAiSpeechConfigDto setSpeed(Double speed) {
    this.speed = speed;
    return this;
  }

  public Boolean getIsDefault() {
    return isDefault;
  }

  public UpdateAiSpeechConfigDto setIsDefault(Boolean isDefault) {
    this.isDefault = isDefault;
    return this;
  }

  public Integer getVersion() {
    return version;
  }

  public UpdateAiSpeechConfigDto setVersion(Integer version) {
    this.version = version;
    return this;
  }
}
