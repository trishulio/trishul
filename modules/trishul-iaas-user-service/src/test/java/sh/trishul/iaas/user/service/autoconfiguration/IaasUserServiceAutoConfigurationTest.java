package sh.trishul.iaas.user.service.autoconfiguration;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sh.trishul.iaas.client.IaasClient;
import sh.trishul.iaas.user.model.BaseIaasUser;
import sh.trishul.iaas.user.model.BaseIaasUserTenantMembership;
import sh.trishul.iaas.user.model.IaasUser;
import sh.trishul.iaas.user.model.IaasUserTenantMembership;
import sh.trishul.iaas.user.model.IaasUserTenantMembershipId;
import sh.trishul.iaas.user.model.UpdateIaasUser;
import sh.trishul.iaas.user.model.UpdateIaasUserTenantMembership;
import sh.trishul.iaas.user.service.TenantIaasUserService;
import sh.trishul.model.executor.BlockingAsyncExecutor;
import sh.trishul.tenant.entity.TenantIdProvider;

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
    IaasClient<String, IaasUser, BaseIaasUser<?>, UpdateIaasUser<?>> mockUserClient
        = mock(IaasClient.class);
    IaasClient<IaasUserTenantMembershipId, IaasUserTenantMembership, BaseIaasUserTenantMembership<?>, UpdateIaasUserTenantMembership<?>> mockMembershipClient
        = mock(IaasClient.class);
    TenantIdProvider mockTenantIdProvider = mock(TenantIdProvider.class);

    TenantIaasUserService result = config.tenantIaasUserService(mockExecutor, mockUserClient,
        mockMembershipClient, mockTenantIdProvider);

    assertNotNull(result);
  }
}
