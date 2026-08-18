package sh.trishul.iaas.tenant.object.store.service.autoconfiguration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sh.trishul.iaas.access.service.policy.service.IaasPolicyService;
import sh.trishul.iaas.access.service.role.policy.attachment.service.IaasRolePolicyAttachmentService;
import sh.trishul.iaas.tenant.object.store.TenantIaasVfsResourceMapper;
import sh.trishul.iaas.tenant.object.store.builder.TenantObjectStoreResourceBuilder;
import sh.trishul.iaas.tenant.object.store.service.service.TenantIaasVfsService;
import sh.trishul.object.store.service.IaasObjectStoreService;
import sh.trishul.object.store.service.cors.config.service.IaasObjectStoreAccessConfigService;
import sh.trishul.object.store.service.cors.config.service.IaasObjectStoreCorsConfigService;

@Configuration
public class IaasTenantObjectStoreServiceAutoConfiguration {

  @Bean
  @ConditionalOnMissingBean(TenantIaasVfsService.class)
  public TenantIaasVfsService iaasVfsService(IaasPolicyService iaasPolicyService,
      IaasObjectStoreService iaasObjectStoreService,
      IaasRolePolicyAttachmentService iaasRolePolicyAttachmentService,
      IaasObjectStoreCorsConfigService iaasObjectStoreCorsConfigService,
      IaasObjectStoreAccessConfigService iaasPublicAccessBlockService,
      TenantObjectStoreResourceBuilder objectStoreResourceBuilder) {
    return new TenantIaasVfsService(TenantIaasVfsResourceMapper.INSTANCE, iaasPolicyService,
        iaasObjectStoreService, iaasRolePolicyAttachmentService, iaasObjectStoreCorsConfigService,
        iaasPublicAccessBlockService, objectStoreResourceBuilder);
  }
}
