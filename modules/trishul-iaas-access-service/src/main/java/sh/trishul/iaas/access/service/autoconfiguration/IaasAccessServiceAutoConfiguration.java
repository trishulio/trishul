package sh.trishul.iaas.access.service.autoconfiguration;

import java.util.Set;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sh.trishul.crud.service.CrudEntityMergerService;
import sh.trishul.crud.service.EntityMergerService;
import sh.trishul.crud.service.LockService;
import sh.trishul.iaas.access.policy.model.BaseIaasPolicy;
import sh.trishul.iaas.access.policy.model.IaasPolicy;
import sh.trishul.iaas.access.policy.model.UpdateIaasPolicy;
import sh.trishul.iaas.access.role.attachment.policy.BaseIaasRolePolicyAttachment;
import sh.trishul.iaas.access.role.attachment.policy.IaasRolePolicyAttachment;
import sh.trishul.iaas.access.role.attachment.policy.IaasRolePolicyAttachmentId;
import sh.trishul.iaas.access.role.attachment.policy.UpdateIaasRolePolicyAttachment;
import sh.trishul.iaas.access.role.model.BaseIaasRole;
import sh.trishul.iaas.access.role.model.IaasRole;
import sh.trishul.iaas.access.role.model.UpdateIaasRole;
import sh.trishul.iaas.access.service.policy.service.IaasPolicyService;
import sh.trishul.iaas.access.service.role.policy.attachment.service.IaasRolePolicyAttachmentService;
import sh.trishul.iaas.access.service.role.service.IaasRoleService;
import sh.trishul.iaas.client.BulkIaasClient;
import sh.trishul.iaas.client.IaasClient;
import sh.trishul.iaas.repository.IaasRepository;
import sh.trishul.model.executor.BlockingAsyncExecutor;

@Configuration
public class IaasAccessServiceAutoConfiguration {

  @Bean
  @ConditionalOnMissingBean(IaasRoleService.class)
  public IaasRoleService iaasRoleService(LockService lockService, BlockingAsyncExecutor executor,
      IaasClient<String, IaasRole, BaseIaasRole<?>, UpdateIaasRole<?>> iaasRoleClient) {
    EntityMergerService<String, IaasRole, BaseIaasRole<?>, UpdateIaasRole<?>> entityMergerService
        = new CrudEntityMergerService<>(lockService, BaseIaasRole.class, UpdateIaasRole.class,
            IaasRole.class, Set.of());
    IaasRepository<String, IaasRole, BaseIaasRole<?>, UpdateIaasRole<?>> iaasRepo
        = new BulkIaasClient<>(executor, iaasRoleClient);

    return new IaasRoleService(entityMergerService, iaasRepo);
  }

  @Bean
  @ConditionalOnMissingBean(IaasPolicyService.class)
  public IaasPolicyService iaasPolicyService(LockService lockService,
      BlockingAsyncExecutor executor,
      IaasClient<String, IaasPolicy, BaseIaasPolicy<?>, UpdateIaasPolicy<?>> iaasPolicyClient) {
    EntityMergerService<String, IaasPolicy, BaseIaasPolicy<?>, UpdateIaasPolicy<?>> entityMergerService
        = new CrudEntityMergerService<>(lockService, BaseIaasPolicy.class, UpdateIaasPolicy.class,
            IaasPolicy.class, Set.of());
    IaasRepository<String, IaasPolicy, BaseIaasPolicy<?>, UpdateIaasPolicy<?>> iaasRepo
        = new BulkIaasClient<>(executor, iaasPolicyClient);

    return new IaasPolicyService(entityMergerService, iaasRepo);
  }

  @Bean
  @ConditionalOnMissingBean(IaasRolePolicyAttachmentService.class)
  public IaasRolePolicyAttachmentService iaasRolePolicyAttachmentService(LockService lockService,
      BlockingAsyncExecutor executor,
      IaasClient<IaasRolePolicyAttachmentId, IaasRolePolicyAttachment, BaseIaasRolePolicyAttachment<?>, UpdateIaasRolePolicyAttachment<?>> iaasRolePolicyAttachmentClient) {
    EntityMergerService<IaasRolePolicyAttachmentId, IaasRolePolicyAttachment, BaseIaasRolePolicyAttachment<?>, UpdateIaasRolePolicyAttachment<?>> updateService
        = new CrudEntityMergerService<>(lockService, BaseIaasRolePolicyAttachment.class,
            UpdateIaasRolePolicyAttachment.class, IaasRolePolicyAttachment.class, Set.of());
    IaasRepository<IaasRolePolicyAttachmentId, IaasRolePolicyAttachment, BaseIaasRolePolicyAttachment<?>, UpdateIaasRolePolicyAttachment<?>> iaasRepo
        = new BulkIaasClient<>(executor, iaasRolePolicyAttachmentClient);
    return new IaasRolePolicyAttachmentService(updateService, iaasRepo);
  }
}
