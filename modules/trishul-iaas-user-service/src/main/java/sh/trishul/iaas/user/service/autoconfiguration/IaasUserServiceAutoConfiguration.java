package sh.trishul.iaas.user.service.autoconfiguration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sh.trishul.iaas.client.BulkIaasClient;
import sh.trishul.iaas.client.IaasClient;
import sh.trishul.iaas.repository.IaasRepository;
import sh.trishul.iaas.user.model.BaseIaasUser;
import sh.trishul.iaas.user.model.BaseIaasUserTenantMembership;
import sh.trishul.iaas.user.model.IaasUser;
import sh.trishul.iaas.user.model.IaasUserTenantMembership;
import sh.trishul.iaas.user.model.IaasUserTenantMembershipId;
import sh.trishul.iaas.user.model.TenantIaasUserMapper;
import sh.trishul.iaas.user.model.UpdateIaasUser;
import sh.trishul.iaas.user.model.UpdateIaasUserTenantMembership;
import sh.trishul.iaas.user.service.TenantIaasUserService;
import sh.trishul.model.executor.BlockingAsyncExecutor;
import sh.trishul.tenant.entity.TenantIdProvider;

@Configuration
public class IaasUserServiceAutoConfiguration {
  @Bean
  @ConditionalOnMissingBean(TenantIaasUserService.class)
  public TenantIaasUserService tenantIaasUserService(BlockingAsyncExecutor executor,
      IaasClient<String, IaasUser, BaseIaasUser<?>, UpdateIaasUser<?>> userClient,
      IaasClient<IaasUserTenantMembershipId, IaasUserTenantMembership, BaseIaasUserTenantMembership<?>, UpdateIaasUserTenantMembership<?>> membershipClient,
      TenantIdProvider tenantIdProvider) {
    IaasRepository<String, IaasUser, BaseIaasUser<?>, UpdateIaasUser<?>> userRepository
        = new BulkIaasClient<>(executor, userClient);
    IaasRepository<IaasUserTenantMembershipId, IaasUserTenantMembership, BaseIaasUserTenantMembership<?>, UpdateIaasUserTenantMembership<?>> membershipRepository
        = new BulkIaasClient<>(executor, membershipClient);

    return new TenantIaasUserService(userRepository, membershipRepository,
        TenantIaasUserMapper.INSTANCE, tenantIdProvider);
  }
}
