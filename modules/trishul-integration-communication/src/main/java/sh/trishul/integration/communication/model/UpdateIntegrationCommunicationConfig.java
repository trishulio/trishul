package sh.trishul.integration.communication.model;

import sh.trishul.base.types.base.pojo.UpdatableEntity;

public interface UpdateIntegrationCommunicationConfig<T extends UpdateIntegrationCommunicationConfig<T>>
    extends BaseIntegrationCommunicationConfig<T>, UpdatableEntity<Long, T> {
}
