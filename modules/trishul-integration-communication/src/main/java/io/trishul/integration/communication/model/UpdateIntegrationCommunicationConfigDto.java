package io.trishul.integration.communication.model;

import io.trishul.communication.model.channel.ChannelType;
import io.trishul.model.base.dto.BaseDto;
import jakarta.validation.constraints.NotNull;

public class UpdateIntegrationCommunicationConfigDto extends BaseDto {
  private Long id;

  private Long integrationId;

  private ChannelType channelType;

  private String channelAddress;

  private String defaultFrom;

  private Boolean enabled;

  @NotNull
  private Integer version;

  public UpdateIntegrationCommunicationConfigDto() {
    super();
  }

  public UpdateIntegrationCommunicationConfigDto(Long id) {
    this();
    setId(id);
  }

  public UpdateIntegrationCommunicationConfigDto(Long id, Long integrationId,
      ChannelType channelType, String channelAddress, String defaultFrom, Boolean enabled,
      @NotNull Integer version) {
    this(id);
    setIntegrationId(integrationId);
    setChannelType(channelType);
    setChannelAddress(channelAddress);
    setDefaultFrom(defaultFrom);
    setEnabled(enabled);
    setVersion(version);
  }

  public Long getId() {
    return id;
  }

  public UpdateIntegrationCommunicationConfigDto setId(Long id) {
    this.id = id;
    return this;
  }

  public Long getIntegrationId() {
    return integrationId;
  }

  public UpdateIntegrationCommunicationConfigDto setIntegrationId(Long integrationId) {
    this.integrationId = integrationId;
    return this;
  }

  public ChannelType getChannelType() {
    return channelType;
  }

  public UpdateIntegrationCommunicationConfigDto setChannelType(ChannelType channelType) {
    this.channelType = channelType;
    return this;
  }

  public String getChannelAddress() {
    return channelAddress;
  }

  public UpdateIntegrationCommunicationConfigDto setChannelAddress(String channelAddress) {
    this.channelAddress = channelAddress;
    return this;
  }

  public String getDefaultFrom() {
    return defaultFrom;
  }

  public UpdateIntegrationCommunicationConfigDto setDefaultFrom(String defaultFrom) {
    this.defaultFrom = defaultFrom;
    return this;
  }

  public Boolean getEnabled() {
    return enabled;
  }

  public UpdateIntegrationCommunicationConfigDto setEnabled(Boolean enabled) {
    this.enabled = enabled;
    return this;
  }

  public Integer getVersion() {
    return version;
  }

  public UpdateIntegrationCommunicationConfigDto setVersion(Integer version) {
    this.version = version;
    return this;
  }
}
