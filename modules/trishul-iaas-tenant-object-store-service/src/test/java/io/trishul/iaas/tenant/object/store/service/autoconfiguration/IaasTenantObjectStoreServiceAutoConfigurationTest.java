package io.trishul.iaas.tenant.object.store.service.autoconfiguration;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.trishul.iaas.access.service.policy.service.IaasPolicyService;
import io.trishul.iaas.access.service.role.policy.attachment.service.IaasRolePolicyAttachmentService;
import io.trishul.iaas.tenant.object.store.builder.TenantObjectStoreResourceBuilder;
import io.trishul.iaas.tenant.object.store.service.service.TenantIaasVfsService;
import io.trishul.object.store.service.IaasObjectStoreService;
import io.trishul.object.store.service.cors.config.service.IaasObjectStoreAccessConfigService;
import io.trishul.object.store.service.cors.config.service.IaasObjectStoreCorsConfigService;

class IaasTenantObjectStoreServiceAutoConfigurationTest {

  private IaasTenantObjectStoreServiceAutoConfiguration config;

  @BeforeEach
  void setUp() {
    config = new IaasTenantObjectStoreServiceAutoConfiguration();
  }

  @Test
  void testIaasVfsService_ReturnsNonNull() {
    IaasPolicyService mockPolicyService = mock(IaasPolicyService.class);
    IaasObjectStoreService mockObjectStoreService = mock(IaasObjectStoreService.class);
    IaasRolePolicyAttachmentService mockRolePolicyAttachmentService
        = mock(IaasRolePolicyAttachmentService.class);
    IaasObjectStoreCorsConfigService mockCorsConfigService
        = mock(IaasObjectStoreCorsConfigService.class);
    IaasObjectStoreAccessConfigService mockAccessConfigService
        = mock(IaasObjectStoreAccessConfigService.class);
    TenantObjectStoreResourceBuilder mockResourceBuilder
        = mock(TenantObjectStoreResourceBuilder.class);

    TenantIaasVfsService result = config.iaasVfsService(mockPolicyService, mockObjectStoreService,
        mockRolePolicyAttachmentService, mockCorsConfigService, mockAccessConfigService,
        mockResourceBuilder);

    assertNotNull(result);
  }
}
