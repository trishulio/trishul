package sh.trishul.iaas.repository.provider;

import sh.trishul.base.types.base.pojo.Identified;
import sh.trishul.iaas.repository.IaasRepository;

public interface IaasRepositoryProvider<ID, Entity extends Identified<ID>, BaseEntity, UpdateEntity> {
  IaasRepository<ID, Entity, BaseEntity, UpdateEntity> getIaasRepository();
}
