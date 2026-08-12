package sh.trishul.iaas.tenant.idp.management.service.autoconfiguration;

import static java.util.Set.of;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sh.trishul.crud.service.CrudEntityMergerService;
import sh.trishul.crud.service.EntityMergerService;
import sh.trishul.crud.service.LockService;
import sh.trishul.iaas.access.service.role.service.IaasRoleService;
import sh.trishul.iaas.client.BulkIaasClient;
import sh.trishul.iaas.client.IaasClient;
import sh.trishul.iaas.idp.tenant.model.BaseIaasIdpTenant;
import sh.trishul.iaas.idp.tenant.model.IaasIdpTenant;
import sh.trishul.iaas.idp.tenant.model.TenantIaasAuthResourceMapper;
import sh.trishul.iaas.idp.tenant.model.UpdateIaasIdpTenant;
import sh.trishul.iaas.idp.tenant.model.mapper.TenantIaasIdpResourcesMapper;
import sh.trishul.iaas.repository.IaasRepository;
import sh.trishul.iaas.tenant.idp.management.service.IaasIdpTenantService;
import sh.trishul.iaas.tenant.idp.management.service.TenantIaasAuthService;
import sh.trishul.iaas.tenant.idp.management.service.TenantIaasIdpService;
import sh.trishul.iaas.tenant.resource.TenantIaasResourceBuilder;
import sh.trishul.model.executor.BlockingAsyncExecutor;

@Configuration
public class IaasTenantIdpManagementServiceAutoConfiguration {
  @Bean
  @ConditionalOnMissingBean(IaasIdpTenantService.class)
  public IaasIdpTenantService iaasIdpTenantService(LockService lockService,
      BlockingAsyncExecutor executor,
      IaasClient<String, IaasIdpTenant, BaseIaasIdpTenant<?>, UpdateIaasIdpTenant<?>> iaasIdpTenantClient) {
    EntityMergerService<String, IaasIdpTenant, BaseIaasIdpTenant<?>, UpdateIaasIdpTenant<?>> entityMergerService
        = new CrudEntityMergerService<>(lockService, BaseIaasIdpTenant.class,
            UpdateIaasIdpTenant.class, IaasIdpTenant.class, of());
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
