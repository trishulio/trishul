package io.trishul.iaas.tenant.idp.service.aws.autoconfiguration;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;

import io.trishul.iaas.access.aws.AwsArnMapper;
import io.trishul.iaas.access.service.role.service.IaasRoleService;
import io.trishul.iaas.client.IaasClient;

class IaasTenantIdpServiceAwsAutoConfigurationTest {

  private IaasTenantIdpServiceAwsAutoConfiguration config;

  @BeforeEach
  void setUp() {
    config = new IaasTenantIdpServiceAwsAutoConfiguration();
  }

  @Test
  void testIaasIdpTenantClient_ReturnsNonNull() {
    AWSCognitoIdentityProvider mockIdp = mock(AWSCognitoIdentityProvider.class);
    String userPoolId = "test-user-pool-id";
    AwsArnMapper mockArnMapper = mock(AwsArnMapper.class);
    IaasRoleService mockRoleService = mock(IaasRoleService.class);

    IaasClient<?, ?, ?, ?> result
        = config.iaasIdpTenantClient(mockIdp, userPoolId, mockArnMapper, mockRoleService);

    assertNotNull(result);
  }

  @Test
  void testAwsCognitoUserGroupMembership_ReturnsNonNull() {
    AWSCognitoIdentityProvider mockIdp = mock(AWSCognitoIdentityProvider.class);
    String userPoolId = "test-user-pool-id";

    IaasClient<?, ?, ?, ?> result = config.awsCognitoUserGroupMembership(mockIdp, userPoolId);

    assertNotNull(result);
  }
}
