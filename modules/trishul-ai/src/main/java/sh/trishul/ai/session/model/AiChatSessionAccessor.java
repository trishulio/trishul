package sh.trishul.ai.session.model;

public interface AiChatSessionAccessor<T extends AiChatSessionAccessor<T>> {
  String ATTR_CHAT_SESSION = "chatSession";

  AiChatSession getChatSession();

  T setChatSession(AiChatSession chatSession);
}
