package sh.trishul.iaas.repository.provider;

import sh.trishul.test.model.BaseDummyCrudEntity;
import sh.trishul.test.model.DummyCrudEntity;
import sh.trishul.test.model.UpdateDummyCrudEntity;

public interface DummyCrudEntityIaasRepositoryProvider extends
    IaasRepositoryProvider<Long, DummyCrudEntity, BaseDummyCrudEntity<?>, UpdateDummyCrudEntity<?>> {
}
