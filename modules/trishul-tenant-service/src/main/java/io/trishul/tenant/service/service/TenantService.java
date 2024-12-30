package io.trishul.tenant.service.service;

import io.trishul.base.types.base.pojo.Identified;
import io.trishul.crud.service.CrudService;
import io.trishul.crud.service.EntityMergerService;
import io.trishul.iaas.tenant.service.TenantIaasService;
import io.trishul.model.base.exception.EntityNotFoundException;
import io.trishul.repo.jpa.query.clause.where.builder.WhereClauseBuilder;
import io.trishul.repo.jpa.repository.service.RepoService;
import io.trishul.tenant.entity.BaseTenant;
import io.trishul.tenant.entity.Tenant;
import io.trishul.tenant.entity.TenantAccessor;
import io.trishul.tenant.entity.TenantData;
import io.trishul.tenant.entity.UpdateTenant;
import io.trishul.tenant.persistence.management.migration.manager.MigrationManager;
import io.trishul.tenant.service.repository.TenantRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.UUID;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;

@Transactional
public class TenantService
    implements CrudService<UUID, Tenant, BaseTenant<?>, UpdateTenant<?>, TenantAccessor<?>> {
  @SuppressWarnings("unused")
  private static final Logger log = LoggerFactory.getLogger(TenantService.class);

  private final TenantData adminTenant;
  private final RepoService<UUID, Tenant, TenantAccessor<?>> repoService;
  private final EntityMergerService<UUID, Tenant, BaseTenant<?>, UpdateTenant<?>> entityMergerService;
  private final TenantRepository tenantRepository;
  private final MigrationManager migrationManager;
  private final TenantIaasService iaasService;

  public TenantService(TenantData adminTenant, RepoService<UUID, Tenant, TenantAccessor<?>> repoService,
      EntityMergerService<UUID, Tenant, BaseTenant<?>, UpdateTenant<?>> entityMergerService,
      TenantRepository tenantRepository, MigrationManager migrationManager,
      TenantIaasService iaasService) {
    this.adminTenant = adminTenant;
    this.repoService = repoService;
    this.entityMergerService = entityMergerService;
    this.tenantRepository = tenantRepository;
    this.migrationManager = migrationManager;
    this.iaasService = iaasService;
  }

  @PostConstruct
  public void migrateTenants() {
    List<TenantData> testTenants = new ArrayList<>();
    testTenants.add(adminTenant);

    // Mock data
    testTenants.add(new Tenant(UUID.fromString("eae07f11-4c9a-4a3b-8b23-9c05d695ab67")));

    this.migrationManager.migrateAll(testTenants);
  }

  public Page<Tenant> getAll(Set<UUID> ids, Set<String> names, Set<URL> urls, Boolean isReady,
      SortedSet<String> sort, boolean orderAscending, int page, int size) {
    Specification<Tenant> spec = WhereClauseBuilder.builder().in(new String[] {Tenant.ATTR_ID}, ids)
        .in(new String[] {Tenant.ATTR_NAME}, names).in(new String[] {Tenant.ATTR_URL}, urls)
        .is(new String[] {Tenant.ATTR_IS_READY}, isReady).build();
    return this.repoService.getAll(spec, sort, orderAscending, page, size);
  }

  @Override
  public boolean exists(Set<UUID> ids) {
    return this.repoService.exists(ids);
  }

  @Override
  public boolean exist(UUID id) {
    return this.repoService.exists(id);
  }

  @Override
  public long delete(Set<UUID> ids) {
    List<Tenant> tenants = this.tenantRepository.findAllById(ids);
    tenants.forEach(tenant -> tenant.setIsReady(false));
    this.repoService.saveAll(tenants);

    long deleteCount = this.repoService.delete(ids);

    this.iaasService.delete(ids);

    return deleteCount;
  }

  @Override
  public long delete(UUID id) {
    return this.delete(Set.of(id));
  }

  @Override
  public Tenant get(UUID id) {
    return this.repoService.get(id);
  }

  @Override
  public List<Tenant> getByIds(Collection<? extends Identified<UUID>> idProviders) {
    return this.repoService.getByIds(idProviders);
  }

  @Override
  public List<Tenant> getByAccessorIds(Collection<? extends TenantAccessor<?>> accessors) {
    return this.repoService.getByAccessorIds(accessors, accessor -> accessor.getTenant());
  }

  @Override
  public List<Tenant> add(final List<? extends BaseTenant<?>> additions) {
    if (additions == null) {
      return null;
    }

    final List<Tenant> entities = this.entityMergerService.getAddEntities(additions);

    List<Tenant> tenants = this.repoService.saveAll(entities);

    this.migrationManager.migrateAll(tenants);
    this.iaasService.put(tenants);

    tenants.forEach(tenant -> tenant.setIsReady(true));
    return this.repoService.saveAll(tenants);
  }

  @Override
  public List<Tenant> put(List<? extends UpdateTenant<?>> updates) {
    if (updates == null) {
      return null;
    }

    final List<Tenant> existing = this.repoService.getByIds(updates);
    final List<Tenant> updated = this.entityMergerService.getPutEntities(existing, updates);

    List<Tenant> tenants = this.repoService.saveAll(updated);

    this.migrationManager.migrateAll(tenants);
    this.iaasService.put(tenants);

    tenants.forEach(tenant -> tenant.setIsReady(true));
    return this.repoService.saveAll(tenants);
  }

  @Override
  public List<Tenant> patch(List<? extends UpdateTenant<?>> patches) {
    if (patches == null) {
      return null;
    }

    final List<Tenant> existing = this.repoService.getByIds(patches);

    if (existing.size() != patches.size()) {
      final Set<UUID> existingIds
          = existing.stream().map(tenant -> tenant.getId()).collect(Collectors.toSet());
      final Set<UUID> nonExistingIds = patches.stream().map(patch -> patch.getId())
          .filter(patchId -> !existingIds.contains(patchId)).collect(Collectors.toSet());

      throw new EntityNotFoundException(
          String.format("Cannot find tenants with Ids: %s", nonExistingIds));
    }

    final List<Tenant> updated = this.entityMergerService.getPatchEntities(existing, patches);

    List<Tenant> tenants = this.repoService.saveAll(updated);

    this.migrationManager.migrateAll(tenants);
    this.iaasService.put(tenants);

    tenants.forEach(tenant -> tenant.setIsReady(true));
    return this.repoService.saveAll(tenants);
  }
}
