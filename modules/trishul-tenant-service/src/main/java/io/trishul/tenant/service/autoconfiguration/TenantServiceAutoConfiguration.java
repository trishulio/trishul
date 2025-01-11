package io.trishul.tenant.service.autoconfiguration;

import java.util.Set;
import java.util.UUID;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.trishul.base.types.base.pojo.Refresher;
import io.trishul.crud.service.CrudEntityMergerService;
import io.trishul.crud.service.CrudRepoService;
import io.trishul.crud.service.EntityMergerService;
import io.trishul.crud.service.LockService;
import io.trishul.iaas.tenant.service.TenantIaasService;
import io.trishul.model.validator.UtilityProvider;
import io.trishul.repo.jpa.repository.service.RepoService;
import io.trishul.tenant.entity.BaseTenant;
import io.trishul.tenant.entity.Tenant;
import io.trishul.tenant.entity.TenantAccessor;
import io.trishul.tenant.entity.UpdateTenant;
import io.trishul.tenant.persistence.management.migration.manager.MigrationManager;
import io.trishul.tenant.persistence.management.migration.register.TenantRegister;
import io.trishul.tenant.service.repository.TenantRepository;
import io.trishul.tenant.service.service.TenantService;

@Configuration
public class TenantServiceAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean(TenantService.class)
    public TenantService tenantService(LockService lockService, TenantRepository tenantRepository, MigrationManager migrationManager, TenantRegister tenantRegister, TenantIaasService tenantIaasService, Refresher<Tenant, TenantAccessor<?>> tenantRefresher, UtilityProvider utilProvider, Tenant adminTenant) {
        RepoService<UUID, Tenant, TenantAccessor<?>> repoService = new CrudRepoService<>(tenantRepository, tenantRefresher);
        EntityMergerService<UUID, Tenant, BaseTenant<?>, UpdateTenant<?>> updateService = new CrudEntityMergerService<>(utilProvider, lockService, BaseTenant.class, UpdateTenant.class, Tenant.class, Set.of(""));

        final TenantService tenantService = new TenantService(
            adminTenant,
            repoService,
            updateService,
            tenantRepository,
            migrationManager,
            tenantIaasService
        );
        return tenantService;
    }
}
