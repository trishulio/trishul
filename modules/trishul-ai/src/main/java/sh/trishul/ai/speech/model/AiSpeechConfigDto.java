package sh.trishul.ai.speech.model;

import java.time.LocalDateTime;
import sh.trishul.model.base.dto.BaseDto;

public class AiSpeechConfigDto extends BaseDto {
  private Long id;

  private String name;

  private String provider;

  private String ttsModelName;

  private String sttModelName;

  private String voice;

  private Double speed;

  private Boolean isDefault;

  private LocalDateTime createdAt;

  private LocalDateTime lastUpdated;

  private Integer version;

  public AiSpeechConfigDto() {}

  public AiSpeechConfigDto(Long id) {
    this();
    setId(id);
  }

  public AiSpeechConfigDto(Long id, String name, String provider, String ttsModelName,
      String sttModelName, String voice, Double speed, Boolean isDefault, LocalDateTime createdAt,
      LocalDateTime lastUpdated, Integer version) {
    this(id);
    setName(name);
    setProvider(provider);
    setTtsModelName(ttsModelName);
    setSttModelName(sttModelName);
    setVoice(voice);
    setSpeed(speed);
    setIsDefault(isDefault);
    setCreatedAt(createdAt);
    setLastUpdated(lastUpdated);
    setVersion(version);
  }

  public Long getId() {
    return id;
  }

  public AiSpeechConfigDto setId(Long id) {
    this.id = id;
    return this;
  }

  public String getName() {
    return name;
  }

  public AiSpeechConfigDto setName(String name) {
    this.name = name;
    return this;
  }

  public String getProvider() {
    return provider;
  }

  public AiSpeechConfigDto setProvider(String provider) {
    this.provider = provider;
    return this;
  }

  public String getTtsModelName() {
    return ttsModelName;
  }

  public AiSpeechConfigDto setTtsModelName(String ttsModelName) {
    this.ttsModelName = ttsModelName;
    return this;
  }

  public String getSttModelName() {
    return sttModelName;
  }

  public AiSpeechConfigDto setSttModelName(String sttModelName) {
    this.sttModelName = sttModelName;
    return this;
  }

  public String getVoice() {
    return voice;
  }

  public AiSpeechConfigDto setVoice(String voice) {
    this.voice = voice;
    return this;
  }

  public Double getSpeed() {
    return speed;
  }

  public AiSpeechConfigDto setSpeed(Double speed) {
    this.speed = speed;
    return this;
  }

  public Boolean getIsDefault() {
    return isDefault;
  }

  public AiSpeechConfigDto setIsDefault(Boolean isDefault) {
    this.isDefault = isDefault;
    return this;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public AiSpeechConfigDto setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
    return this;
  }

  public LocalDateTime getLastUpdated() {
    return lastUpdated;
  }

  public AiSpeechConfigDto setLastUpdated(LocalDateTime lastUpdated) {
    this.lastUpdated = lastUpdated;
    return this;
  }

  public Integer getVersion() {
    return version;
  }

  public AiSpeechConfigDto setVersion(Integer version) {
    this.version = version;
    return this;
  }
}
