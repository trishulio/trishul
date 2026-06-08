package io.trishul.ai.service.memory.store;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import dev.langchain4j.data.message.ChatMessage;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TenantChatMemoryStoreTest {

  private TenantChatMemoryStore store;

  @BeforeEach
  void setUp() {
    store = new TenantChatMemoryStore();
  }

  @Test
  void testGetMessages_ReturnsEmptyList_WhenKeyDoesNotExist() {
    List<ChatMessage> messages = store.getMessages("non-existent");
    assertTrue(messages.isEmpty());
  }

  @Test
  void testUpdateAndGetMessages_ReturnsUpdatedMessages() {
    ChatMessage msg = mock(ChatMessage.class);
    List<ChatMessage> list = List.of(msg);

    store.updateMessages("session1", list);
    List<ChatMessage> retrieved = store.getMessages("session1");

    assertEquals(1, retrieved.size());
    assertEquals(msg, retrieved.get(0));
  }

  @Test
  void testDeleteMessages_RemovesMessages() {
    ChatMessage msg = mock(ChatMessage.class);
    store.updateMessages("session1", List.of(msg));

    store.deleteMessages("session1");
    List<ChatMessage> retrieved = store.getMessages("session1");

    assertTrue(retrieved.isEmpty());
  }
}
