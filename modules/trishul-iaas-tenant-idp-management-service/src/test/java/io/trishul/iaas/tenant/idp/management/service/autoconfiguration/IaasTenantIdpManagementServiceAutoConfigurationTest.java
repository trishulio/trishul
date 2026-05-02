package io.trishul.iaas.tenant.idp.management.service.autoconfiguration;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.trishul.crud.service.LockService;
import io.trishul.iaas.access.service.role.service.IaasRoleService;
import io.trishul.iaas.client.IaasClient;
import io.trishul.iaas.idp.tenant.model.BaseIaasIdpTenant;
import io.trishul.iaas.idp.tenant.model.IaasIdpTenant;
import io.trishul.iaas.idp.tenant.model.UpdateIaasIdpTenant;
import io.trishul.iaas.tenant.idp.management.service.IaasIdpTenantService;
import io.trishul.iaas.tenant.idp.management.service.TenantIaasAuthService;
import io.trishul.iaas.tenant.idp.management.service.TenantIaasIdpService;
import io.trishul.iaas.tenant.resource.TenantIaasResourceBuilder;
import io.trishul.model.executor.BlockingAsyncExecutor;

class IaasTenantIdpManagementServiceAutoConfigurationTest {

  private IaasTenantIdpManagementServiceAutoConfiguration config;

  @BeforeEach
  void setUp() {
    config = new IaasTenantIdpManagementServiceAutoConfiguration();
  }

  @Test
  @SuppressWarnings("unchecked")
  void testIaasIdpTenantService_ReturnsNonNull() {
    LockService mockLockService = mock(LockService.class);
    BlockingAsyncExecutor mockExecutor = mock(BlockingAsyncExecutor.class);
    IaasClient<String, IaasIdpTenant, BaseIaasIdpTenant<?>, UpdateIaasIdpTenant<?>> mockClient
        = mock(IaasClient.class);

    IaasIdpTenantService result
        = config.iaasIdpTenantService(mockLockService, mockExecutor, mockClient);

    assertNotNull(result);
  }

  @Test
  void testTenantIaasIdpService_ReturnsNonNull() {
    IaasIdpTenantService mockIaasIdpTenantService = mock(IaasIdpTenantService.class);

    TenantIaasIdpService result = config.tenantIaasIdpService(mockIaasIdpTenantService);

    assertNotNull(result);
  }

  @Test
  void testTenantIaasAuthService_ReturnsNonNull() {
    IaasRoleService mockRoleService = mock(IaasRoleService.class);
    TenantIaasResourceBuilder mockResourceBuilder = mock(TenantIaasResourceBuilder.class);

    TenantIaasAuthService result
        = config.tenantIaasAuthService(mockRoleService, mockResourceBuilder);

    assertNotNull(result);
  }
}
