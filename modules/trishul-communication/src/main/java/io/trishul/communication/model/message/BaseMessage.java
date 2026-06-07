package io.trishul.communication.model.message;

import io.trishul.communication.model.channel.ChannelType;
import java.util.List;

public interface BaseMessage<T extends BaseMessage<T>> {
  final String ATTR_FROM = "from";
  final String ATTR_TO = "to";
  final String ATTR_BODY = "body";
  final String ATTR_CHANNEL_TYPE = "channelType";
  final String ATTR_MEDIA_URLS = "mediaUrls";

  String getFrom();

  T setFrom(String from);

  String getTo();

  T setTo(String to);

  String getBody();

  T setBody(String body);

  ChannelType getChannelType();

  T setChannelType(ChannelType channelType);

  List<String> getMediaUrls();

  T setMediaUrls(List<String> mediaUrls);
}
