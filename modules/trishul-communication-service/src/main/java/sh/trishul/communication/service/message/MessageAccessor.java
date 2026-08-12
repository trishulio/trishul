package sh.trishul.communication.service.message;

import sh.trishul.communication.model.message.Message;

public interface MessageAccessor<T extends MessageAccessor<T>> {
  final String ATTR_MESSAGE = "message";

  Message getMessage();

  T setMessage(Message message);
}
