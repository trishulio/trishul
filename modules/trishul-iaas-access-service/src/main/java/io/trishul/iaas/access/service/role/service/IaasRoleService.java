package io.trishul.iaas.access.service.role.service;

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
import io.trishul.iaas.access.role.model.BaseIaasRole;
import io.trishul.iaas.access.role.model.IaasRole;
import io.trishul.iaas.access.role.model.IaasRoleAccessor;
import io.trishul.iaas.access.role.model.UpdateIaasRole;
import io.trishul.iaas.repository.IaasRepository;
import jakarta.transaction.Transactional;

@Transactional
public class IaasRoleService extends BaseService implements
    CrudService<String, IaasRole, BaseIaasRole<?>, UpdateIaasRole<?>, IaasRoleAccessor<?>> {
  private static final Logger log = LoggerFactory.getLogger(IaasRoleService.class);

  private final IaasRepository<String, IaasRole, BaseIaasRole<?>, UpdateIaasRole<?>> iaasRepo;

  private final EntityMergerService<String, IaasRole, BaseIaasRole<?>, UpdateIaasRole<?>> entityMergerService;

  public IaasRoleService(
      EntityMergerService<String, IaasRole, BaseIaasRole<?>, UpdateIaasRole<?>> entityMergerService,
      IaasRepository<String, IaasRole, BaseIaasRole<?>, UpdateIaasRole<?>> iaasRepo) {
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
  public IaasRole get(String id) {
    IaasRole role = null;

    List<IaasRole> roles = this.iaasRepo.get(Set.of(id));
    if (roles.size() == 1) {
      role = roles.get(0);
    } else {
      log.debug("Get IaasRole: '{}' returned {}", roles);
    }

    return role;
  }

  public List<IaasRole> getAll(Set<String> ids) {
    return this.iaasRepo.get(ids);
  }

  @Override
  public List<IaasRole> getByIds(Collection<? extends Identified<String>> idProviders) {
    Set<String> ids = idProviders.stream().filter(Objects::nonNull)
        .map(provider -> provider.getId()).filter(Objects::nonNull).collect(Collectors.toSet());

    return this.iaasRepo.get(ids);
  }

  @Override
  public List<IaasRole> getByAccessorIds(Collection<? extends IaasRoleAccessor<?>> accessors) {
    List<IaasRole> idProviders = accessors.stream().filter(Objects::nonNull)
        .map(accessor -> accessor.getIaasRole()).filter(Objects::nonNull).toList();
    return getByIds(idProviders);
  }

  @Override
  public List<IaasRole> add(List<? extends BaseIaasRole<?>> additions) {
    if (additions == null) {
      return null;
    }

    List<IaasRole> roles = this.entityMergerService.getAddEntities(additions);

    return iaasRepo.add(roles);
  }

  @Override
  public List<IaasRole> put(List<? extends UpdateIaasRole<?>> updates) {
    if (updates == null) {
      return null;
    }

    List<IaasRole> updated = this.entityMergerService.getPutEntities(null, updates);

    return iaasRepo.put(updated);
  }

  @Override
  public List<IaasRole> patch(List<? extends UpdateIaasRole<?>> updates) {
    if (updates == null) {
      return null;
    }

    List<IaasRole> existing = this.getByIds(updates);

    List<IaasRole> updated = this.entityMergerService.getPatchEntities(existing, updates);

    return iaasRepo.put(updated);
  }
}
