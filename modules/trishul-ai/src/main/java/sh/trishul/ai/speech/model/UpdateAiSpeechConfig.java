package sh.trishul.ai.speech.model;

import sh.trishul.base.types.base.pojo.UpdatableEntity;

public interface UpdateAiSpeechConfig<T extends UpdateAiSpeechConfig<T>>
    extends BaseAiSpeechConfig<T>, UpdatableEntity<Long, T> {
}
