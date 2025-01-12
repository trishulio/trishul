package io.trishul.iaas.tenant.object.store.service.autoconfiguration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.trishul.iaas.access.service.policy.service.IaasPolicyService;
import io.trishul.iaas.access.service.role.policy.attachment.service.IaasRolePolicyAttachmentService;
import io.trishul.iaas.tenant.object.store.TenantIaasVfsResourceMapper;
import io.trishul.iaas.tenant.object.store.builder.TenantObjectStoreResourceBuilder;
import io.trishul.iaas.tenant.object.store.service.service.TenantIaasVfsService;
import io.trishul.object.store.service.IaasObjectStoreService;
import io.trishul.object.store.service.cors.config.service.IaasObjectStoreAccessConfigService;
import io.trishul.object.store.service.cors.config.service.IaasObjectStoreCorsConfigService;

@Configuration
public class IaasTenantObjectStoreServiceAutoConfiguration {
    
    @Bean
    @ConditionalOnMissingBean(TenantIaasVfsService.class)
    public TenantIaasVfsService iaasVfsService(IaasPolicyService iaasPolicyService, IaasObjectStoreService iaasObjectStoreService, IaasRolePolicyAttachmentService iaasRolePolicyAttachmentService, IaasObjectStoreCorsConfigService iaasObjectStoreCorsConfigService, IaasObjectStoreAccessConfigService iaasPublicAccessBlockService, TenantObjectStoreResourceBuilder objectStoreResourceBuilder) {
        return new TenantIaasVfsService(
            TenantIaasVfsResourceMapper.INSTANCE,
            iaasPolicyService,
            iaasObjectStoreService,
            iaasRolePolicyAttachmentService,
            iaasObjectStoreCorsConfigService,
            iaasPublicAccessBlockService,
            objectStoreResourceBuilder
        );
    }
}
