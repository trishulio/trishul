package io.trishul.iaas.user.service.autoconfiguration;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.trishul.iaas.client.IaasClient;
import io.trishul.iaas.user.model.BaseIaasUser;
import io.trishul.iaas.user.model.BaseIaasUserTenantMembership;
import io.trishul.iaas.user.model.IaasUser;
import io.trishul.iaas.user.model.IaasUserTenantMembership;
import io.trishul.iaas.user.model.IaasUserTenantMembershipId;
import io.trishul.iaas.user.model.UpdateIaasUser;
import io.trishul.iaas.user.model.UpdateIaasUserTenantMembership;
import io.trishul.iaas.user.service.TenantIaasUserService;
import io.trishul.model.executor.BlockingAsyncExecutor;
import io.trishul.tenant.entity.TenantIdProvider;

class IaasUserServiceAutoConfigurationTest {

  private IaasUserServiceAutoConfiguration config;

  @BeforeEach
  void setUp() {
    config = new IaasUserServiceAutoConfiguration();
  }

  @Test
  @SuppressWarnings("unchecked")
  void testTenantIaasUserService_ReturnsNonNull() {
    BlockingAsyncExecutor mockExecutor = mock(BlockingAsyncExecutor.class);
    IaasClient<String, IaasUser, BaseIaasUser<?>, UpdateIaasUser<?>> mockUserClient = mock(IaasClient.class);
    IaasClient<IaasUserTenantMembershipId, IaasUserTenantMembership, BaseIaasUserTenantMembership<?>, UpdateIaasUserTenantMembership<?>> mockMembershipClient = mock(IaasClient.class);
    TenantIdProvider mockTenantIdProvider = mock(TenantIdProvider.class);

    TenantIaasUserService result = config.tenantIaasUserService(
        mockExecutor, mockUserClient, mockMembershipClient, mockTenantIdProvider);

    assertNotNull(result);
  }
}
