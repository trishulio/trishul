package io.trishul.ai.speech.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.trishul.base.types.base.pojo.Audited;
import io.trishul.base.types.base.pojo.CrudEntity;
import io.trishul.model.base.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity(name = "ai_speech_config")
@Table(name = "AI_SPEECH_CONFIG")
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class AiSpeechConfig extends BaseEntity implements CrudEntity<Long, AiSpeechConfig>,
    UpdateAiSpeechConfig<AiSpeechConfig>, Audited<AiSpeechConfig> {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ai_speech_config_generator")
  @SequenceGenerator(name = "ai_speech_config_generator",
      sequenceName = "ai_speech_config_sequence", allocationSize = 1)
  private Long id;

  @Column(name = "name")
  private String name;

  @Column(name = "provider")
  private String provider;

  @Column(name = "tts_model_name")
  private String ttsModelName;

  @Column(name = "stt_model_name")
  private String sttModelName;

  @Column(name = "voice")
  private String voice;

  @Column(name = "speed")
  private Double speed;

  @Column(name = "is_default")
  private Boolean isDefault;

  @Version
  private Integer version;

  @CreationTimestamp
  @Column(name = "created_at", updatable = false)
  private LocalDateTime createdAt;

  @UpdateTimestamp
  @Column(name = "last_updated")
  private LocalDateTime lastUpdated;

  public AiSpeechConfig() {}

  public AiSpeechConfig(Long id) {
    this();
    setId(id);
  }

  public AiSpeechConfig(Long id, String name, String provider, String ttsModelName,
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

  @Override
  public Long getId() {
    return id;
  }

  @Override
  public AiSpeechConfig setId(Long id) {
    this.id = id;
    return this;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public AiSpeechConfig setName(String name) {
    this.name = name;
    return this;
  }

  @Override
  public String getProvider() {
    return provider;
  }

  @Override
  public AiSpeechConfig setProvider(String provider) {
    this.provider = provider;
    return this;
  }

  @Override
  public String getTtsModelName() {
    return ttsModelName;
  }

  @Override
  public AiSpeechConfig setTtsModelName(String ttsModelName) {
    this.ttsModelName = ttsModelName;
    return this;
  }

  @Override
  public String getSttModelName() {
    return sttModelName;
  }

  @Override
  public AiSpeechConfig setSttModelName(String sttModelName) {
    this.sttModelName = sttModelName;
    return this;
  }

  @Override
  public String getVoice() {
    return voice;
  }

  @Override
  public AiSpeechConfig setVoice(String voice) {
    this.voice = voice;
    return this;
  }

  @Override
  public Double getSpeed() {
    return speed;
  }

  @Override
  public AiSpeechConfig setSpeed(Double speed) {
    this.speed = speed;
    return this;
  }

  @Override
  public Boolean getIsDefault() {
    return isDefault;
  }

  @Override
  public AiSpeechConfig setIsDefault(Boolean isDefault) {
    this.isDefault = isDefault;
    return this;
  }

  @Override
  public Integer getVersion() {
    return version;
  }

  public AiSpeechConfig setVersion(Integer version) {
    this.version = version;
    return this;
  }

  @Override
  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  @Override
  public AiSpeechConfig setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
    return this;
  }

  @Override
  public LocalDateTime getLastUpdated() {
    return lastUpdated;
  }

  @Override
  public AiSpeechConfig setLastUpdated(LocalDateTime lastUpdated) {
    this.lastUpdated = lastUpdated;
    return this;
  }
}
