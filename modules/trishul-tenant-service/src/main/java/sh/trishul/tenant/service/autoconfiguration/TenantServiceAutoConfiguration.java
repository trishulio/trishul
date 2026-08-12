package sh.trishul.tenant.service.autoconfiguration;

import java.util.Set;
import java.util.UUID;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sh.trishul.base.types.base.pojo.Refresher;
import sh.trishul.crud.service.CrudEntityMergerService;
import sh.trishul.crud.service.CrudRepoService;
import sh.trishul.crud.service.EntityMergerService;
import sh.trishul.crud.service.LockService;
import sh.trishul.iaas.tenant.service.TenantIaasService;
import sh.trishul.model.base.pojo.refresher.accessor.AccessorRefresher;
import sh.trishul.repo.jpa.repository.service.RepoService;
import sh.trishul.tenant.entity.BaseTenant;
import sh.trishul.tenant.entity.Tenant;
import sh.trishul.tenant.entity.TenantAccessor;
import sh.trishul.tenant.entity.TenantRefresher;
import sh.trishul.tenant.entity.UpdateTenant;
import sh.trishul.tenant.persistence.management.migration.manager.MigrationManager;
import sh.trishul.tenant.persistence.management.migration.register.TenantRegister;
import sh.trishul.tenant.service.repository.TenantRepository;
import sh.trishul.tenant.service.service.TenantService;

@Configuration
public class TenantServiceAutoConfiguration {
  @Bean
  @ConditionalOnMissingBean(TenantService.class)
  public TenantService tenantService(LockService lockService, TenantRepository tenantRepository,
      MigrationManager migrationManager, TenantRegister tenantRegister,
      TenantIaasService tenantIaasService, Refresher<Tenant, TenantAccessor<?>> tenantRefresher) {
    RepoService<UUID, Tenant, TenantAccessor<?>> repoService
        = new CrudRepoService<>(tenantRepository, tenantRefresher);
    EntityMergerService<UUID, Tenant, BaseTenant<?>, UpdateTenant<?>> updateService
        = new CrudEntityMergerService<>(lockService, BaseTenant.class, UpdateTenant.class,
            Tenant.class, Set.of(""));

    final TenantService tenantService = new TenantService(repoService, updateService,
        tenantRepository, migrationManager, tenantIaasService);
    return tenantService;
  }

  @Bean
  public AccessorRefresher<UUID, TenantAccessor<?>, Tenant> tenantAccessorRefresher(
      TenantRepository repo) {
    return new AccessorRefresher<>(Tenant.class, TenantAccessor::getTenant,
        (accessor, tenant) -> accessor.setTenant(tenant), ids -> repo.findAllById(ids));
  }

  @Bean
  public Refresher<Tenant, TenantAccessor<?>> tenantRefresher(
      AccessorRefresher<UUID, TenantAccessor<?>, Tenant> tenantAccessRefresher) {
    return new TenantRefresher(tenantAccessRefresher);
  }
}
