package sh.trishul.ai.tool.model;

import sh.trishul.base.types.base.pojo.UpdatableEntity;

public interface UpdateAiTool<T extends UpdateAiTool<T>>
    extends BaseAiTool<T>, UpdatableEntity<Long, T> {
}
