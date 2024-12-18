package io.trishul.iaas.repository.provider;

import io.trishul.test.model.BaseDummyCrudEntity;
import io.trishul.test.model.DummyCrudEntity;
import io.trishul.test.model.UpdateDummyCrudEntity;

public interface DummyCrudEntityIaasRepositoryProvider
        extends IaasRepositoryProvider<
                Long, DummyCrudEntity, BaseDummyCrudEntity, UpdateDummyCrudEntity> {}
