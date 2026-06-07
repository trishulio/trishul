package io.trishul.ai.skill.model;

import io.trishul.base.types.base.pojo.UpdatableEntity;

public interface UpdateAiSkill<T extends UpdateAiSkill<T>>
    extends BaseAiSkill<T>, UpdatableEntity<Long, T> {
}
