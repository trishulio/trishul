package io.trishul.iaas.repository.provider;

import java.util.List;
import java.util.Map;
import java.util.Set;

import io.trishul.iaas.repository.IaasRepository;
import io.trishul.base.types.base.pojo.Identified;

public class IaasRepositoryProviderProxy<ID, Entity extends Identified<ID>, BaseEntity, UpdateEntity> implements IaasRepository<ID, Entity, BaseEntity, UpdateEntity> {
    private final IaasRepositoryProvider<ID, Entity, BaseEntity, UpdateEntity> provider;

    public IaasRepositoryProviderProxy(IaasRepositoryProvider<ID, Entity, BaseEntity, UpdateEntity> provider) {
        this.provider = provider;
    }

    @Override
    public List<Entity> get(Set<ID> ids) {
        return provider.getIaasRepository().get(ids);
    }

    @Override
    public <BE extends BaseEntity> List<Entity> add(List<BE> additions) {
        return provider.getIaasRepository().add(additions);
    }

    @Override
    public <UE extends UpdateEntity> List<Entity> put(List<UE> updates) {
        return provider.getIaasRepository().put(updates);
    }

    @Override
    public long delete(Set<ID> ids) {
        return provider.getIaasRepository().delete(ids);
    }

    @Override
    public Map<ID, Boolean> exists(Set<ID> ids) {
        return provider.getIaasRepository().exists(ids);
    }
}
