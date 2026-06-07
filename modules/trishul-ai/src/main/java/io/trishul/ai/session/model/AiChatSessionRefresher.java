package io.trishul.ai.session.model;

import io.trishul.ai.agent.model.AiAgentConfig;
import io.trishul.ai.agent.model.AiAgentConfigAccessor;
import io.trishul.ai.memory.model.AiChatMemoryConfig;
import io.trishul.ai.memory.model.AiChatMemoryConfigAccessor;
import io.trishul.base.types.base.pojo.Refresher;
import io.trishul.model.base.pojo.refresher.accessor.AccessorRefresher;
import java.util.Collection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AiChatSessionRefresher implements Refresher<AiChatSession, AiChatSessionAccessor<?>> {
  @SuppressWarnings("unused")
  private static final Logger log = LoggerFactory.getLogger(AiChatSessionRefresher.class);

  private final AccessorRefresher<Long, AiChatSessionAccessor<?>, AiChatSession> refresher;
  private final Refresher<AiAgentConfig, AiAgentConfigAccessor<?>> agentConfigRefresher;
  private final Refresher<AiChatMemoryConfig, AiChatMemoryConfigAccessor<?>> chatMemoryConfigRefresher;

  public AiChatSessionRefresher(
      AccessorRefresher<Long, AiChatSessionAccessor<?>, AiChatSession> refresher,
      Refresher<AiAgentConfig, AiAgentConfigAccessor<?>> agentConfigRefresher,
      Refresher<AiChatMemoryConfig, AiChatMemoryConfigAccessor<?>> chatMemoryConfigRefresher) {
    this.refresher = refresher;
    this.agentConfigRefresher = agentConfigRefresher;
    this.chatMemoryConfigRefresher = chatMemoryConfigRefresher;
  }

  @Override
  public void refresh(Collection<AiChatSession> entities) {
    this.agentConfigRefresher.refreshAccessors(entities);
    this.chatMemoryConfigRefresher.refreshAccessors(entities);
  }

  @Override
  public void refreshAccessors(Collection<? extends AiChatSessionAccessor<?>> accessors) {
    this.refresher.refreshAccessors(accessors);
  }
}
