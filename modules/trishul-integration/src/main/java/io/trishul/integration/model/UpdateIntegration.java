package io.trishul.integration.model;

import io.trishul.base.types.base.pojo.UpdatableEntity;

public interface UpdateIntegration<T extends UpdateIntegration<T>>
    extends BaseIntegration<T>, UpdatableEntity<Long, T> {
}
