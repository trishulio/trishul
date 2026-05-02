package io.trishul.iaas.tenant.service.autoconfiguration;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.trishul.iaas.tenant.idp.management.service.TenantIaasAuthService;
import io.trishul.iaas.tenant.idp.management.service.TenantIaasIdpService;
import io.trishul.iaas.tenant.object.store.service.service.TenantIaasVfsService;
import io.trishul.iaas.tenant.service.TenantIaasService;

class IaasTenantServiceAutoConfigurationTest {

  private IaasTenantServiceAutoConfiguration config;

  @BeforeEach
  void setUp() {
    config = new IaasTenantServiceAutoConfiguration();
  }

  @Test
  void testTenantIaasService_ReturnsNonNull() {
    TenantIaasAuthService mockAuthService = mock(TenantIaasAuthService.class);
    TenantIaasIdpService mockIdpService = mock(TenantIaasIdpService.class);
    TenantIaasVfsService mockVfsService = mock(TenantIaasVfsService.class);

    TenantIaasService result
        = config.tenantIaasService(mockAuthService, mockIdpService, mockVfsService);

    assertNotNull(result);
  }
}
