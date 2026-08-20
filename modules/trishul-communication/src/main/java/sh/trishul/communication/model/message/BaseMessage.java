package sh.trishul.communication.model.message;

import java.util.List;
import sh.trishul.communication.model.channel.ChannelType;

public interface BaseMessage<T extends BaseMessage<T>> {
  String ATTR_FROM = "from";
  String ATTR_TO = "to";
  String ATTR_BODY = "body";
  String ATTR_CHANNEL_TYPE = "channelType";
  String ATTR_MEDIA_URLS = "mediaUrls";

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
