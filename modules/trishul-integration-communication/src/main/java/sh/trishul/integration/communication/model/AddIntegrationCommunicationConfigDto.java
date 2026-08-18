package sh.trishul.integration.communication.model;

import jakarta.validation.constraints.NotNull;
import sh.trishul.communication.model.channel.ChannelType;
import sh.trishul.model.base.dto.BaseDto;

public class AddIntegrationCommunicationConfigDto extends BaseDto {
  @NotNull
  private Long integrationId;

  @NotNull
  private ChannelType channelType;

  private String channelAddress;

  private String defaultFrom;

  private Boolean enabled;

  public AddIntegrationCommunicationConfigDto() {}

  public AddIntegrationCommunicationConfigDto(@NotNull Long integrationId,
      @NotNull ChannelType channelType, String channelAddress, String defaultFrom,
      Boolean enabled) {
    setIntegrationId(integrationId);
    setChannelType(channelType);
    setChannelAddress(channelAddress);
    setDefaultFrom(defaultFrom);
    setEnabled(enabled);
  }

  public Long getIntegrationId() {
    return integrationId;
  }

  public AddIntegrationCommunicationConfigDto setIntegrationId(Long integrationId) {
    this.integrationId = integrationId;
    return this;
  }

  public ChannelType getChannelType() {
    return channelType;
  }

  public AddIntegrationCommunicationConfigDto setChannelType(ChannelType channelType) {
    this.channelType = channelType;
    return this;
  }

  public String getChannelAddress() {
    return channelAddress;
  }

  public AddIntegrationCommunicationConfigDto setChannelAddress(String channelAddress) {
    this.channelAddress = channelAddress;
    return this;
  }

  public String getDefaultFrom() {
    return defaultFrom;
  }

  public AddIntegrationCommunicationConfigDto setDefaultFrom(String defaultFrom) {
    this.defaultFrom = defaultFrom;
    return this;
  }

  public Boolean getEnabled() {
    return enabled;
  }

  public AddIntegrationCommunicationConfigDto setEnabled(Boolean enabled) {
    this.enabled = enabled;
    return this;
  }
}
