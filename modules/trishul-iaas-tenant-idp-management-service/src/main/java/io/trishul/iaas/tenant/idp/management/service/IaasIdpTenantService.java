package io.trishul.iaas.tenant.idp.management.service;

import io.trishul.base.types.base.pojo.Identified;
import io.trishul.crud.service.BaseService;
import io.trishul.crud.service.CrudService;
import io.trishul.crud.service.EntityMergerService;
import io.trishul.iaas.idp.tenant.model.BaseIaasIdpTenant;
import io.trishul.iaas.idp.tenant.model.IaasIdpTenant;
import io.trishul.iaas.idp.tenant.model.IaasIdpTenantAccessor;
import io.trishul.iaas.idp.tenant.model.UpdateIaasIdpTenant;
import io.trishul.iaas.repository.IaasRepository;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IaasIdpTenantService extends BaseService implements
    CrudService<String, IaasIdpTenant, BaseIaasIdpTenant<?>, UpdateIaasIdpTenant<?>, IaasIdpTenantAccessor<?>> {
  private static final Logger log = LoggerFactory.getLogger(IaasIdpTenantService.class);

  private final IaasRepository<String, IaasIdpTenant, BaseIaasIdpTenant<?>, UpdateIaasIdpTenant<?>> iaasRepo;
  private final EntityMergerService<String, IaasIdpTenant, BaseIaasIdpTenant<?>, UpdateIaasIdpTenant<?>> entityMergerService;

  public IaasIdpTenantService(
      EntityMergerService<String, IaasIdpTenant, BaseIaasIdpTenant<?>, UpdateIaasIdpTenant<?>> entityMergerService,
      IaasRepository<String, IaasIdpTenant, BaseIaasIdpTenant<?>, UpdateIaasIdpTenant<?>> iaasRepo) {
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
  public IaasIdpTenant get(String id) {
    IaasIdpTenant policy = null;

    List<IaasIdpTenant> policies = this.iaasRepo.get(Set.of(id));
    if (policies.size() == 1) {
      policy = policies.get(0);
    } else {
      log.debug("Get policy: '{}' returned {}", policies);
    }

    return policy;
  }

  public List<IaasIdpTenant> getAll(Set<String> ids) {
    return this.iaasRepo.get(ids);
  }

  @Override
  public List<IaasIdpTenant> getByIds(Collection<? extends Identified<String>> idProviders) {
    Set<String> ids = idProviders.stream().filter(Objects::nonNull)
        .map(provider -> provider.getId()).filter(Objects::nonNull).collect(Collectors.toSet());

    return this.iaasRepo.get(ids);
  }

  @Override
  public List<IaasIdpTenant> getByAccessorIds(
      Collection<? extends IaasIdpTenantAccessor<?>> accessors) {
    List<IaasIdpTenant> idProviders = accessors.stream().filter(Objects::nonNull)
        .map(accessor -> accessor.getIdpTenant()).filter(Objects::nonNull).toList();
    return getByIds(idProviders);
  }

  @Override
  public List<IaasIdpTenant> add(List<? extends BaseIaasIdpTenant<?>> additions) {
    if (additions == null) {
      return null;
    }

    List<IaasIdpTenant> policies = this.entityMergerService.getAddEntities(additions);

    return iaasRepo.add(policies);
  }

  @Override
  public List<IaasIdpTenant> put(List<? extends UpdateIaasIdpTenant<?>> updates) {
    if (updates == null) {
      return null;
    }

    List<IaasIdpTenant> updated = this.entityMergerService.getPutEntities(null, updates);

    return iaasRepo.put(updated);
  }

  @Override
  public List<IaasIdpTenant> patch(List<? extends UpdateIaasIdpTenant<?>> updates) {
    if (updates == null) {
      return null;
    }

    List<IaasIdpTenant> existing = this.getByIds(updates);

    List<IaasIdpTenant> updated = this.entityMergerService.getPatchEntities(existing, updates);

    return iaasRepo.put(updated);
  }
}
