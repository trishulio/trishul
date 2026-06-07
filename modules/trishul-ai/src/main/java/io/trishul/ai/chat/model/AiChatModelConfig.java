package io.trishul.ai.chat.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.trishul.base.types.base.pojo.Audited;
import io.trishul.base.types.base.pojo.CrudEntity;
import io.trishul.model.base.entity.BaseEntity;
import io.trishul.repo.jpa.converter.StringCryptoConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
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

@Entity(name = "ai_chat_model_config")
@Table(name = "AI_CHAT_MODEL_CONFIG")
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class AiChatModelConfig extends BaseEntity implements CrudEntity<Long, AiChatModelConfig>,
    UpdateAiChatModelConfig<AiChatModelConfig>, Audited<AiChatModelConfig> {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ai_chat_model_config_generator")
  @SequenceGenerator(name = "ai_chat_model_config_generator",
      sequenceName = "ai_chat_model_config_sequence", allocationSize = 1)
  private Long id;

  @Column(name = "name")
  private String name;

  @Column(name = "provider")
  private String provider;

  @Column(name = "model_name")
  private String modelName;

  @Column(name = "streaming_model_name")
  private String streamingModelName;

  @Column(name = "api_key")
  @Convert(converter = StringCryptoConverter.class)
  private String apiKey;

  @Column(name = "temperature")
  private Double temperature;

  @Column(name = "max_tokens")
  private Integer maxTokens;

  @Column(name = "top_p")
  private Double topP;

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

  public AiChatModelConfig() {}

  public AiChatModelConfig(Long id) {
    this();
    setId(id);
  }

  public AiChatModelConfig(Long id, String name, String provider, String modelName,
      String streamingModelName, String apiKey, Double temperature, Integer maxTokens, Double topP,
      Boolean isDefault, LocalDateTime createdAt, LocalDateTime lastUpdated, Integer version) {
    this(id);
    setName(name);
    setProvider(provider);
    setModelName(modelName);
    setStreamingModelName(streamingModelName);
    setApiKey(apiKey);
    setTemperature(temperature);
    setMaxTokens(maxTokens);
    setTopP(topP);
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
  public AiChatModelConfig setId(Long id) {
    this.id = id;
    return this;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public AiChatModelConfig setName(String name) {
    this.name = name;
    return this;
  }

  @Override
  public String getProvider() {
    return provider;
  }

  @Override
  public AiChatModelConfig setProvider(String provider) {
    this.provider = provider;
    return this;
  }

  @Override
  public String getModelName() {
    return modelName;
  }

  @Override
  public AiChatModelConfig setModelName(String modelName) {
    this.modelName = modelName;
    return this;
  }

  @Override
  public String getStreamingModelName() {
    return streamingModelName;
  }

  @Override
  public AiChatModelConfig setStreamingModelName(String streamingModelName) {
    this.streamingModelName = streamingModelName;
    return this;
  }

  @Override
  public String getApiKey() {
    return apiKey;
  }

  @Override
  public AiChatModelConfig setApiKey(String apiKey) {
    this.apiKey = apiKey;
    return this;
  }

  @Override
  public Double getTemperature() {
    return temperature;
  }

  @Override
  public AiChatModelConfig setTemperature(Double temperature) {
    this.temperature = temperature;
    return this;
  }

  @Override
  public Integer getMaxTokens() {
    return maxTokens;
  }

  @Override
  public AiChatModelConfig setMaxTokens(Integer maxTokens) {
    this.maxTokens = maxTokens;
    return this;
  }

  @Override
  public Double getTopP() {
    return topP;
  }

  @Override
  public AiChatModelConfig setTopP(Double topP) {
    this.topP = topP;
    return this;
  }

  @Override
  public Boolean getIsDefault() {
    return isDefault;
  }

  @Override
  public AiChatModelConfig setIsDefault(Boolean isDefault) {
    this.isDefault = isDefault;
    return this;
  }

  @Override
  public Integer getVersion() {
    return version;
  }

  public AiChatModelConfig setVersion(Integer version) {
    this.version = version;
    return this;
  }

  @Override
  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  @Override
  public AiChatModelConfig setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
    return this;
  }

  @Override
  public LocalDateTime getLastUpdated() {
    return lastUpdated;
  }

  @Override
  public AiChatModelConfig setLastUpdated(LocalDateTime lastUpdated) {
    this.lastUpdated = lastUpdated;
    return this;
  }
}
