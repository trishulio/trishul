package io.trishul.iaas.user.service.autoconfiguration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.trishul.iaas.client.BulkIaasClient;
import io.trishul.iaas.client.IaasClient;
import io.trishul.iaas.repository.IaasRepository;
import io.trishul.iaas.user.model.BaseIaasUser;
import io.trishul.iaas.user.model.BaseIaasUserTenantMembership;
import io.trishul.iaas.user.model.IaasUser;
import io.trishul.iaas.user.model.IaasUserTenantMembership;
import io.trishul.iaas.user.model.IaasUserTenantMembershipId;
import io.trishul.iaas.user.model.TenantIaasUserMapper;
import io.trishul.iaas.user.model.UpdateIaasUser;
import io.trishul.iaas.user.model.UpdateIaasUserTenantMembership;
import io.trishul.iaas.user.service.TenantIaasUserService;
import io.trishul.model.executor.BlockingAsyncExecutor;
import io.trishul.tenant.entity.TenantIdProvider;

@Configuration
public class IaasUserServiceAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean(TenantIaasUserService.class)
    public TenantIaasUserService tenantIaasUserService(
            BlockingAsyncExecutor executor,
            IaasClient<String, IaasUser, BaseIaasUser<?>, UpdateIaasUser<?>> userClient,
            IaasClient<IaasUserTenantMembershipId, IaasUserTenantMembership, BaseIaasUserTenantMembership<?>, UpdateIaasUserTenantMembership<?>> membershipClient,
            TenantIdProvider tenantIdProvider
    ) {
        IaasRepository<String, IaasUser, BaseIaasUser<?>, UpdateIaasUser<?>> userRepository = new BulkIaasClient<>(executor, userClient);
        IaasRepository<IaasUserTenantMembershipId, IaasUserTenantMembership, BaseIaasUserTenantMembership<?>, UpdateIaasUserTenantMembership<?>> membershipRepository = new BulkIaasClient<>(executor, membershipClient);

        return new TenantIaasUserService(userRepository, membershipRepository, TenantIaasUserMapper.INSTANCE, tenantIdProvider);
    }
}
