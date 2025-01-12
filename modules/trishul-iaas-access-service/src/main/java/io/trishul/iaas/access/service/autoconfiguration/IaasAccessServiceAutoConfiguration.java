package io.trishul.iaas.access.service.autoconfiguration;

import java.util.Set;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.trishul.crud.service.CrudEntityMergerService;
import io.trishul.crud.service.EntityMergerService;
import io.trishul.crud.service.LockService;
import io.trishul.iaas.access.policy.model.BaseIaasPolicy;
import io.trishul.iaas.access.policy.model.IaasPolicy;
import io.trishul.iaas.access.policy.model.UpdateIaasPolicy;
import io.trishul.iaas.access.role.attachment.policy.BaseIaasRolePolicyAttachment;
import io.trishul.iaas.access.role.attachment.policy.IaasRolePolicyAttachment;
import io.trishul.iaas.access.role.attachment.policy.IaasRolePolicyAttachmentId;
import io.trishul.iaas.access.role.attachment.policy.UpdateIaasRolePolicyAttachment;
import io.trishul.iaas.access.role.model.BaseIaasRole;
import io.trishul.iaas.access.role.model.IaasRole;
import io.trishul.iaas.access.role.model.UpdateIaasRole;
import io.trishul.iaas.access.service.policy.service.IaasPolicyService;
import io.trishul.iaas.access.service.role.policy.attachment.service.IaasRolePolicyAttachmentService;
import io.trishul.iaas.access.service.role.service.IaasRoleService;
import io.trishul.iaas.client.BulkIaasClient;
import io.trishul.iaas.client.IaasClient;
import io.trishul.iaas.repository.IaasRepository;
import io.trishul.model.executor.BlockingAsyncExecutor;
import io.trishul.model.validator.UtilityProvider;

@Configuration
public class IaasAccessServiceAutoConfiguration {
    
    @Bean
    @ConditionalOnMissingBean(IaasRoleService.class)
    public IaasRoleService iaasRoleService(
    UtilityProvider utilProvider,    
    LockService lockService,
    BlockingAsyncExecutor executor, IaasClient<String, IaasRole, BaseIaasRole<?>, UpdateIaasRole<?>> iaasRoleClient
    ) {
        EntityMergerService<String, IaasRole, BaseIaasRole<?>, UpdateIaasRole<?>> entityMergerService = new CrudEntityMergerService<>(utilProvider, lockService, BaseIaasRole.class, UpdateIaasRole.class, IaasRole.class, Set.of());
        IaasRepository<String, IaasRole, BaseIaasRole<?>, UpdateIaasRole<?>> iaasRepo = new BulkIaasClient<>(executor, iaasRoleClient);
        
        return new IaasRoleService(entityMergerService, iaasRepo);
    }

    @Bean
    @ConditionalOnMissingBean(IaasPolicyService.class)
    public IaasPolicyService iaasPolicyService(
    UtilityProvider utilProvider,    
    LockService lockService,
    BlockingAsyncExecutor executor, IaasClient<String, IaasPolicy, BaseIaasPolicy<?>, UpdateIaasPolicy<?>> iaasPolicyClient
    ) {
        EntityMergerService<String, IaasPolicy, BaseIaasPolicy<?>, UpdateIaasPolicy<?>> entityMergerService = new CrudEntityMergerService<>(utilProvider, lockService, BaseIaasPolicy.class, UpdateIaasPolicy.class, IaasPolicy.class, Set.of());
        IaasRepository<String, IaasPolicy, BaseIaasPolicy<?>, UpdateIaasPolicy<?>> iaasRepo = new BulkIaasClient<>(executor, iaasPolicyClient);
        
        return new IaasPolicyService(entityMergerService, iaasRepo);
    }

    @Bean
    @ConditionalOnMissingBean(IaasRolePolicyAttachmentService.class)
    public IaasRolePolicyAttachmentService iaasRolePolicyAttachmentService(UtilityProvider utilProvider, LockService lockService, BlockingAsyncExecutor executor, IaasClient<IaasRolePolicyAttachmentId, IaasRolePolicyAttachment, BaseIaasRolePolicyAttachment<?>, UpdateIaasRolePolicyAttachment<?>> iaasRolePolicyAttachmentClient) {
        EntityMergerService<IaasRolePolicyAttachmentId, IaasRolePolicyAttachment, BaseIaasRolePolicyAttachment<?>, UpdateIaasRolePolicyAttachment<?>> updateService = new CrudEntityMergerService<>(utilProvider, lockService, BaseIaasRolePolicyAttachment.class, UpdateIaasRolePolicyAttachment.class, IaasRolePolicyAttachment.class, Set.of());
        IaasRepository<IaasRolePolicyAttachmentId, IaasRolePolicyAttachment, BaseIaasRolePolicyAttachment<?>, UpdateIaasRolePolicyAttachment<?>> iaasRepo = new BulkIaasClient<>(executor, iaasRolePolicyAttachmentClient);
        return new IaasRolePolicyAttachmentService(updateService, iaasRepo);
    }
}
