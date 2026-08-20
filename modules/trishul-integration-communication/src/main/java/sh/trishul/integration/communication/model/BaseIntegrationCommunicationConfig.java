package sh.trishul.integration.communication.model;

import sh.trishul.communication.model.channel.ChannelType;
import sh.trishul.integration.model.IntegrationAccessor;

public interface BaseIntegrationCommunicationConfig<T extends BaseIntegrationCommunicationConfig<T>>
    extends IntegrationAccessor<T> {
  String ATTR_CHANNEL_TYPE = "channelType";
  String ATTR_CHANNEL_ADDRESS = "channelAddress";
  String ATTR_DEFAULT_FROM = "defaultFrom";
  String ATTR_ENABLED = "enabled";

  ChannelType getChannelType();

  T setChannelType(ChannelType channelType);

  String getChannelAddress();

  T setChannelAddress(String channelAddress);

  String getDefaultFrom();

  T setDefaultFrom(String defaultFrom);

  Boolean getEnabled();

  T setEnabled(Boolean enabled);
}
