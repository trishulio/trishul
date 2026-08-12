package sh.trishul.ai.service.memory.store;

import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.store.memory.chat.ChatMemoryStore;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Tenant-aware ChatMemoryStore implementation. For Phase 1, using an in-memory map. In the future,
 * this should be backed by a PostgreSQL JSONB column per tenant.
 */
public class TenantChatMemoryStore implements ChatMemoryStore {

  // Temporary in-memory store. In a real scenario, this would use a JPA repository
  // to store chat messages per tenant.
  private final Map<Object, List<ChatMessage>> store = new ConcurrentHashMap<>();

  @Override
  public List<ChatMessage> getMessages(Object memoryId) {
    return store.getOrDefault(memoryId, new ArrayList<>());
  }

  @Override
  public void updateMessages(Object memoryId, List<ChatMessage> messages) {
    store.put(memoryId, new ArrayList<>(messages));
  }

  @Override
  public void deleteMessages(Object memoryId) {
    store.remove(memoryId);
  }
}
