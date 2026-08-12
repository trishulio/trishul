package sh.trishul.communication.twilio.message;

import java.time.LocalDateTime;
import java.util.List;
import sh.trishul.communication.model.channel.ChannelType;
import sh.trishul.communication.model.message.Message;
import sh.trishul.communication.model.message.MessageDirection;
import sh.trishul.communication.model.message.MessageStatus;

public class TrishulMessage extends Message {
  public TrishulMessage() {
    super();
  }

  public TrishulMessage(String id) {
    super(id);
  }

  public TrishulMessage(String sid, String from, String to, String body, ChannelType channelType,
      MessageStatus status, MessageDirection direction, List<String> mediaUrls, String errorCode,
      String errorMessage, String price, String priceUnit, LocalDateTime createdAt,
      LocalDateTime lastUpdated) {
    super(sid, from, to, body, channelType, status, direction, mediaUrls, errorCode, errorMessage,
        price, priceUnit, createdAt, lastUpdated);
  }
}
