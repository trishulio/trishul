package io.trishul.iaas.repository.provider;

import io.trishul.base.types.base.pojo.Identified;
import io.trishul.iaas.repository.IaasRepository;

public interface IaasRepositoryProvider<
        ID, Entity extends Identified<ID>, BaseEntity, UpdateEntity> {
    IaasRepository<ID, Entity, BaseEntity, UpdateEntity> getIaasRepository();
}
