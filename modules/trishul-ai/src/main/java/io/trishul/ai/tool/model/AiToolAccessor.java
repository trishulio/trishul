package io.trishul.ai.tool.model;

public interface AiToolAccessor<T extends AiToolAccessor<T>> {
  final String ATTR_TOOL = "tool";

  AiTool getTool();

  T setTool(AiTool tool);
}
