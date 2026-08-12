package sh.trishul.iaas.repository.provider;

import sh.trishul.iaas.repository.IaasRepository;
import sh.trishul.test.model.BaseDummyCrudEntity;
import sh.trishul.test.model.DummyCrudEntity;
import sh.trishul.test.model.UpdateDummyCrudEntity;

public interface DummyCrudEntityIaasRepository extends
    IaasRepository<Long, DummyCrudEntity, BaseDummyCrudEntity<?>, UpdateDummyCrudEntity<?>> {
}
