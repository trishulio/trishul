package io.trishul.integration.communication.model;

import io.trishul.communication.model.channel.ChannelType;
import io.trishul.integration.model.IntegrationAccessor;

public interface BaseIntegrationCommunicationConfig<T extends BaseIntegrationCommunicationConfig<T>>
    extends IntegrationAccessor<T> {
  final String ATTR_CHANNEL_TYPE = "channelType";
  final String ATTR_CHANNEL_ADDRESS = "channelAddress";
  final String ATTR_DEFAULT_FROM = "defaultFrom";
  final String ATTR_ENABLED = "enabled";

  ChannelType getChannelType();

  T setChannelType(ChannelType channelType);

  String getChannelAddress();

  T setChannelAddress(String channelAddress);

  String getDefaultFrom();

  T setDefaultFrom(String defaultFrom);

  Boolean getEnabled();

  T setEnabled(Boolean enabled);
}
