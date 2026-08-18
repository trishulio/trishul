package io.trishul.ai.service.agent;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.TokenStream;
import dev.langchain4j.service.UserMessage;

/**
 * Generic LangChain4j AI service interface. This is the interface passed to
 * AiServices.builder(Assistant.class) to produce a dynamic proxy that wires a ChatModel,
 * ChatMemory, and any registered tools together.
 *
 * <p>
 * The {@code @MemoryId} parameter scopes message history per conversation/session, so multiple
 * users or sessions can share the same agent instance while maintaining independent memories.
 */
public interface Assistant {

  /**
   * Send a user message and receive the model's response. Message history is scoped to the provided
   * memoryId.
   *
   * @param memoryId a unique identifier for the conversation (e.g., session ID or user ID).
   * @param message the user's input message.
   * @return the model's text response.
   */
  String chat(@MemoryId Object memoryId, @UserMessage Object message);

  /**
   * Send a user message and receive the model's response as a stream of tokens. Message history is
   * scoped to the provided memoryId.
   *
   * @param memoryId a unique identifier for the conversation.
   * @param message the user's input message.
   * @return a TokenStream to handle the streaming response.
   */
  TokenStream streamChat(@MemoryId Object memoryId, @UserMessage Object message);
}
