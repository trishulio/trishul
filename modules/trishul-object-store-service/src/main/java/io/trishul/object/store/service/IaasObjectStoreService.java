package io.trishul.object.store.service;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.trishul.base.types.base.pojo.Identified;
import io.trishul.crud.service.BaseService;
import io.trishul.crud.service.CrudService;
import io.trishul.crud.service.EntityMergerService;
import io.trishul.iaas.repository.IaasRepository;
import io.trishul.object.store.model.BaseIaasObjectStore;
import io.trishul.object.store.model.IaasObjectStore;
import io.trishul.object.store.model.IaasObjectStoreAccessor;
import io.trishul.object.store.model.UpdateIaasObjectStore;
import jakarta.transaction.Transactional;

@Transactional
public class IaasObjectStoreService extends BaseService implements
    CrudService<String, IaasObjectStore, BaseIaasObjectStore<?>, UpdateIaasObjectStore<?>, IaasObjectStoreAccessor<?>> {
  private static final Logger log = LoggerFactory.getLogger(IaasObjectStoreService.class);

  private final IaasRepository<String, IaasObjectStore, BaseIaasObjectStore<?>, UpdateIaasObjectStore<?>> iaasRepo;

  private final EntityMergerService<String, IaasObjectStore, BaseIaasObjectStore<?>, UpdateIaasObjectStore<?>> entityMergerService;

  public IaasObjectStoreService(
      EntityMergerService<String, IaasObjectStore, BaseIaasObjectStore<?>, UpdateIaasObjectStore<?>> entityMergerService,
      IaasRepository<String, IaasObjectStore, BaseIaasObjectStore<?>, UpdateIaasObjectStore<?>> iaasRepo) {
    this.entityMergerService = entityMergerService;
    this.iaasRepo = iaasRepo;
  }

  @Override
  public boolean exists(Set<String> ids) {
    return iaasRepo.exists(ids).values().stream().filter(b -> !b).findAny().orElseGet(() -> true);
  }

  @Override
  public boolean exist(String id) {
    return exists(Set.of(id));
  }

  @Override
  public long delete(Set<String> ids) {
    return this.iaasRepo.delete(ids);
  }

  @Override
  public long delete(String id) {
    return this.iaasRepo.delete(Set.of(id));
  }

  @Override
  public IaasObjectStore get(String id) {
    IaasObjectStore objectStore = null;

    List<IaasObjectStore> policies = this.iaasRepo.get(Set.of(id));
    if (policies.size() == 1) {
      objectStore = policies.get(0);
    } else {
      log.debug("Get objectStore: '{}' returned {}", policies);
    }

    return objectStore;
  }

  public List<IaasObjectStore> getAll(Set<String> ids) {
    return this.iaasRepo.get(ids);
  }

  @Override
  public List<IaasObjectStore> getByIds(Collection<? extends Identified<String>> idProviders) {
    Set<String> ids = idProviders.stream().filter(Objects::nonNull)
        .map(provider -> provider.getId()).filter(Objects::nonNull).collect(Collectors.toSet());

    return this.iaasRepo.get(ids);
  }

  @Override
  public List<IaasObjectStore> getByAccessorIds(
      Collection<? extends IaasObjectStoreAccessor<?>> accessors) {
    List<IaasObjectStore> idProviders = accessors.stream().filter(Objects::nonNull)
        .map(accessor -> accessor.getIaasObjectStore()).filter(Objects::nonNull).toList();
    return getByIds(idProviders);
  }

  @Override
  public List<IaasObjectStore> add(List<? extends BaseIaasObjectStore<?>> additions) {
    if (additions == null) {
      return null;
    }

    List<IaasObjectStore> objectStores = this.entityMergerService.getAddEntities(additions);

    return iaasRepo.add(objectStores);
  }

  @Override
  public List<IaasObjectStore> put(List<? extends UpdateIaasObjectStore<?>> updates) {
    if (updates == null) {
      return null;
    }

    List<IaasObjectStore> updated = this.entityMergerService.getPutEntities(null, updates);

    return iaasRepo.put(updated);
  }

  @Override
  public List<IaasObjectStore> patch(List<? extends UpdateIaasObjectStore<?>> updates) {
    if (updates == null) {
      return null;
    }

    List<IaasObjectStore> existing = this.getByIds(updates);

    List<IaasObjectStore> updated = this.entityMergerService.getPatchEntities(existing, updates);

    return iaasRepo.put(updated);
  }
}
