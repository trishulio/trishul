package sh.trishul.ai.agent.model;

import java.util.Collection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sh.trishul.ai.chat.model.AiChatModelConfig;
import sh.trishul.ai.chat.model.AiChatModelConfigAccessor;
import sh.trishul.ai.memory.model.AiChatMemoryConfig;
import sh.trishul.ai.memory.model.AiChatMemoryConfigAccessor;
import sh.trishul.base.types.base.pojo.Refresher;
import sh.trishul.model.base.pojo.refresher.accessor.AccessorRefresher;

public class AiAgentConfigRefresher implements Refresher<AiAgentConfig, AiAgentConfigAccessor<?>> {
  @SuppressWarnings("unused")
  private static final Logger log = LoggerFactory.getLogger(AiAgentConfigRefresher.class);

  private final AccessorRefresher<Long, AiAgentConfigAccessor<?>, AiAgentConfig> refresher;
  private final Refresher<AiChatModelConfig, AiChatModelConfigAccessor<?>> chatModelConfigRefresher;
  private final Refresher<AiChatMemoryConfig, AiChatMemoryConfigAccessor<?>> chatMemoryConfigRefresher;

  public AiAgentConfigRefresher(
      AccessorRefresher<Long, AiAgentConfigAccessor<?>, AiAgentConfig> refresher,
      Refresher<AiChatModelConfig, AiChatModelConfigAccessor<?>> chatModelConfigRefresher,
      Refresher<AiChatMemoryConfig, AiChatMemoryConfigAccessor<?>> chatMemoryConfigRefresher) {
    this.refresher = refresher;
    this.chatModelConfigRefresher = chatModelConfigRefresher;
    this.chatMemoryConfigRefresher = chatMemoryConfigRefresher;
  }

  @Override
  public void refresh(Collection<AiAgentConfig> entities) {
    this.chatModelConfigRefresher.refreshAccessors(entities);
    this.chatMemoryConfigRefresher.refreshAccessors(entities);
  }

  @Override
  public void refreshAccessors(Collection<? extends AiAgentConfigAccessor<?>> accessors) {
    this.refresher.refreshAccessors(accessors);
  }
}
