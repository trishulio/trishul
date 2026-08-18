package sh.trishul.iaas.client;

import sh.trishul.test.model.BaseDummyCrudEntity;
import sh.trishul.test.model.DummyCrudEntity;
import sh.trishul.test.model.UpdateDummyCrudEntity;

public interface DummyCrudEntityIaasClient
    extends IaasClient<Long, DummyCrudEntity, BaseDummyCrudEntity<?>, UpdateDummyCrudEntity<?>> {
}
