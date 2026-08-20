package sh.trishul.ai.session.model;

import java.util.Collection;

import sh.trishul.ai.agent.model.AiAgentConfig;
import sh.trishul.ai.agent.model.AiAgentConfigAccessor;
import sh.trishul.ai.memory.model.AiChatMemoryConfig;
import sh.trishul.ai.memory.model.AiChatMemoryConfigAccessor;
import sh.trishul.base.types.base.pojo.Refresher;
import sh.trishul.model.base.pojo.refresher.accessor.AccessorRefresher;

public class AiChatSessionRefresher implements Refresher<AiChatSession, AiChatSessionAccessor<?>> {
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
