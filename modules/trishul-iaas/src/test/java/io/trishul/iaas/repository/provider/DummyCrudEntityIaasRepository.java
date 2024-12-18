package io.trishul.iaas.repository.provider;

import io.trishul.iaas.repository.IaasRepository;
import io.trishul.test.model.BaseDummyCrudEntity;
import io.trishul.test.model.DummyCrudEntity;
import io.trishul.test.model.UpdateDummyCrudEntity;

public interface DummyCrudEntityIaasRepository
        extends IaasRepository<Long, DummyCrudEntity, BaseDummyCrudEntity, UpdateDummyCrudEntity> {}
