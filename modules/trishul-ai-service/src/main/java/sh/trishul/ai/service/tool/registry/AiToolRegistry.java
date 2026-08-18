package sh.trishul.ai.service.tool.registry;

import java.util.Collections;
import java.util.List;

/**
 * AiToolRegistry holds all available Java methods annotated with @Tool and registers them
 * dynamically based on the Agent's configuration of enabled tool IDs.
 */
public class AiToolRegistry {

  public List<Object> getToolsByIds(List<Long> toolIds) {
    return Collections.emptyList();
  }
}
