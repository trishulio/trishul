package io.trishul.iaas.client;

import io.trishul.test.model.BaseDummyCrudEntity;
import io.trishul.test.model.DummyCrudEntity;
import io.trishul.test.model.UpdateDummyCrudEntity;

public interface DummyCrudEntityIaasClient
    extends IaasClient<Long, DummyCrudEntity, BaseDummyCrudEntity<?>, UpdateDummyCrudEntity<?>> {
}
