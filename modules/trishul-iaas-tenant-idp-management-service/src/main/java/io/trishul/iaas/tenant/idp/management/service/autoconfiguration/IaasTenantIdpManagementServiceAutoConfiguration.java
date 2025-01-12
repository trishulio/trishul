package io.trishul.iaas.tenant.idp.management.service.autoconfiguration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.trishul.crud.service.CrudEntityMergerService;
import io.trishul.crud.service.EntityMergerService;
import io.trishul.crud.service.LockService;
import io.trishul.iaas.access.service.role.service.IaasRoleService;
import io.trishul.iaas.client.BulkIaasClient;
import io.trishul.iaas.client.IaasClient;
import io.trishul.iaas.idp.tenant.model.BaseIaasIdpTenant;
import io.trishul.iaas.idp.tenant.model.IaasIdpTenant;
import io.trishul.iaas.idp.tenant.model.TenantIaasAuthResourceMapper;
import io.trishul.iaas.idp.tenant.model.UpdateIaasIdpTenant;
import io.trishul.iaas.idp.tenant.model.mapper.TenantIaasIdpResourcesMapper;
import io.trishul.iaas.repository.IaasRepository;
import io.trishul.iaas.tenant.idp.management.service.IaasIdpTenantService;
import io.trishul.iaas.tenant.idp.management.service.TenantIaasAuthService;
import io.trishul.iaas.tenant.idp.management.service.TenantIaasIdpService;
import io.trishul.iaas.tenant.resource.TenantIaasResourceBuilder;
import io.trishul.model.executor.BlockingAsyncExecutor;
import io.trishul.model.validator.UtilityProvider;

@Configuration
public class IaasTenantIdpManagementServiceAutoConfiguration {
  @Bean
  @ConditionalOnMissingBean(IaasIdpTenantService.class)
  public IaasIdpTenantService iaasIdpTenantService(UtilityProvider utilProvider,
      LockService lockService, BlockingAsyncExecutor executor,
      IaasClient<String, IaasIdpTenant, BaseIaasIdpTenant<?>, UpdateIaasIdpTenant<?>> iaasIdpTenantClient) {
    EntityMergerService<String, IaasIdpTenant, BaseIaasIdpTenant<?>, UpdateIaasIdpTenant<?>> entityMergerService
        = new CrudEntityMergerService<>(utilProvider, lockService, BaseIaasIdpTenant.class,
            UpdateIaasIdpTenant.class, IaasIdpTenant.class, java.util.Set.of());
    IaasRepository<String, IaasIdpTenant, BaseIaasIdpTenant<?>, UpdateIaasIdpTenant<?>> iaasRepo
        = new BulkIaasClient<>(executor, iaasIdpTenantClient);

    return new IaasIdpTenantService(entityMergerService, iaasRepo);
  }

  @Bean
  @ConditionalOnMissingBean(TenantIaasIdpService.class)
  public TenantIaasIdpService tenantIaasIdpService(IaasIdpTenantService iaasIdpTenantService) {
    return new TenantIaasIdpService(iaasIdpTenantService, TenantIaasIdpResourcesMapper.INSTANCE);
  }

  @Bean
  @ConditionalOnMissingBean(TenantIaasAuthService.class)
  public TenantIaasAuthService tenantIaasAuthService(IaasRoleService roleService,
      TenantIaasResourceBuilder resourceBuilder) {
    return new TenantIaasAuthService(TenantIaasAuthResourceMapper.INSTANCE, roleService,
        resourceBuilder);
  }
}
