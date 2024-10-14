package io.trishul.iaas.repository.repository;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IaasRepository<ID, Entity, BaseEntity, UpdateEntity> {
    List<Entity> get(Set<ID> ids);

    <BE extends BaseEntity> List<Entity> add(List<BE> additions);

    <UE extends UpdateEntity> List<Entity> put(List<UE> updates);

    long delete(Set<ID> ids);

    Map<ID, Boolean> exists(Set<ID> ids);
}
