package io.trishul.ai.tool.model;

import io.trishul.base.types.base.pojo.UpdatableEntity;

public interface UpdateAiTool<T extends UpdateAiTool<T>>
    extends BaseAiTool<T>, UpdatableEntity<Long, T> {
}
