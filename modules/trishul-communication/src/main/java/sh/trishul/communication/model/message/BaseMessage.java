package sh.trishul.communication.model.message;

import java.util.List;
import sh.trishul.communication.model.channel.ChannelType;

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
