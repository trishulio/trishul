package io.trishul.communication.service.message;

import io.trishul.communication.model.message.Message;

public interface MessageAccessor<T extends MessageAccessor<T>> {
  final String ATTR_MESSAGE = "message";

  Message getMessage();

  T setMessage(Message message);
}
