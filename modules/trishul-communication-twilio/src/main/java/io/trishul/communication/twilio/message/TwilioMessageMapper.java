package io.trishul.communication.twilio.message;

import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.Message.Direction;
import com.twilio.rest.api.v2010.account.Message.Status;
import io.trishul.communication.model.channel.ChannelType;
import io.trishul.communication.model.message.MessageDirection;
import io.trishul.communication.model.message.MessageStatus;
import io.trishul.iaas.mapper.IaasEntityMapper;

public class TwilioMessageMapper implements IaasEntityMapper<Message, TrishulMessage> {
  public static final TwilioMessageMapper INSTANCE = new TwilioMessageMapper();

  @Override
  public TrishulMessage fromIaasEntity(Message twilioMessage) {
    if (twilioMessage == null) {
      return null;
    }

    return new TrishulMessage(twilioMessage.getSid(), twilioMessage.getFrom().toString(),
        twilioMessage.getTo(), twilioMessage.getBody(), mapChannelType(twilioMessage),
        mapStatus(twilioMessage.getStatus()), mapDirection(twilioMessage.getDirection()), null, // mediaUrls
        twilioMessage.getErrorCode() != null ? twilioMessage.getErrorCode().toString() : null,
        twilioMessage.getErrorMessage(), twilioMessage.getPrice(),
        twilioMessage.getPriceUnit().toString(),
        twilioMessage.getDateCreated() != null ? twilioMessage.getDateCreated().toLocalDateTime()
            : null,
        twilioMessage.getDateUpdated() != null ? twilioMessage.getDateUpdated().toLocalDateTime()
            : null);
  }

  private ChannelType mapChannelType(Message twilioMessage) {
    String from = twilioMessage.getFrom() != null ? twilioMessage.getFrom().toString() : "";
    if (from.startsWith("whatsapp:")) {
      return ChannelType.WHATSAPP;
    }
    return ChannelType.SMS;
  }

  private MessageStatus mapStatus(Status twilioStatus) {
    if (twilioStatus == null) {
      return null;
    }
    return switch (twilioStatus) {
      case QUEUED -> MessageStatus.QUEUED;
      case SENDING -> MessageStatus.SENDING;
      case SENT -> MessageStatus.SENT;
      case DELIVERED -> MessageStatus.DELIVERED;
      case FAILED -> MessageStatus.FAILED;
      case UNDELIVERED -> MessageStatus.UNDELIVERED;
      case RECEIVED -> MessageStatus.RECEIVED;
      default -> null;
    };
  }

  private MessageDirection mapDirection(Direction twilioDirection) {
    if (twilioDirection == null) {
      return null;
    }
    return switch (twilioDirection) {
      case INBOUND -> MessageDirection.INBOUND;
      case OUTBOUND_API, OUTBOUND_CALL, OUTBOUND_REPLY -> MessageDirection.OUTBOUND;
      default -> null;
    };
  }
}
