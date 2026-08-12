package sh.trishul.integration.communication.model;

import java.time.LocalDateTime;
import sh.trishul.communication.model.channel.ChannelType;
import sh.trishul.integration.model.IntegrationDto;
import sh.trishul.model.base.dto.BaseDto;

public class IntegrationCommunicationConfigDto extends BaseDto {
  private Long id;
  private IntegrationDto integration;
  private ChannelType channelType;
  private String channelAddress;
  private String defaultFrom;
  private Boolean enabled;
  private LocalDateTime createdAt;
  private LocalDateTime lastUpdated;
  private Integer version;

  public IntegrationCommunicationConfigDto() {}

  public IntegrationCommunicationConfigDto(Long id) {
    this();
    setId(id);
  }

  public IntegrationCommunicationConfigDto(Long id, IntegrationDto integration,
      ChannelType channelType, String channelAddress, String defaultFrom, Boolean enabled,
      LocalDateTime createdAt, LocalDateTime lastUpdated, Integer version) {
    this(id);
    setIntegration(integration);
    setChannelType(channelType);
    setChannelAddress(channelAddress);
    setDefaultFrom(defaultFrom);
    setEnabled(enabled);
    setCreatedAt(createdAt);
    setLastUpdated(lastUpdated);
    setVersion(version);
  }

  public Long getId() {
    return id;
  }

  public IntegrationCommunicationConfigDto setId(Long id) {
    this.id = id;
    return this;
  }

  public IntegrationDto getIntegration() {
    return integration;
  }

  public IntegrationCommunicationConfigDto setIntegration(IntegrationDto integration) {
    this.integration = integration;
    return this;
  }

  public ChannelType getChannelType() {
    return channelType;
  }

  public IntegrationCommunicationConfigDto setChannelType(ChannelType channelType) {
    this.channelType = channelType;
    return this;
  }

  public String getChannelAddress() {
    return channelAddress;
  }

  public IntegrationCommunicationConfigDto setChannelAddress(String channelAddress) {
    this.channelAddress = channelAddress;
    return this;
  }

  public String getDefaultFrom() {
    return defaultFrom;
  }

  public IntegrationCommunicationConfigDto setDefaultFrom(String defaultFrom) {
    this.defaultFrom = defaultFrom;
    return this;
  }

  public Boolean getEnabled() {
    return enabled;
  }

  public IntegrationCommunicationConfigDto setEnabled(Boolean enabled) {
    this.enabled = enabled;
    return this;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public IntegrationCommunicationConfigDto setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
    return this;
  }

  public LocalDateTime getLastUpdated() {
    return lastUpdated;
  }

  public IntegrationCommunicationConfigDto setLastUpdated(LocalDateTime lastUpdated) {
    this.lastUpdated = lastUpdated;
    return this;
  }

  public Integer getVersion() {
    return version;
  }

  public IntegrationCommunicationConfigDto setVersion(Integer version) {
    this.version = version;
    return this;
  }
}
