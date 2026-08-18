package sh.trishul.ai.guardrail.model;

import sh.trishul.base.types.base.pojo.UpdatableEntity;

public interface UpdateAiGuardrail<T extends UpdateAiGuardrail<T>>
    extends BaseAiGuardrail<T>, UpdatableEntity<Long, T> {
}
