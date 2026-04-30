package io.trishul.iaas.access.aws.autoconfiguration;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import com.amazonaws.services.identitymanagement.AmazonIdentityManagement;
import com.amazonaws.services.identitymanagement.AmazonIdentityManagementClient;
import io.trishul.iaas.access.aws.AwsArnMapper;
import io.trishul.iaas.access.aws.AwsIamPolicyClient;
import io.trishul.iaas.access.aws.AwsIamRoleClient;
import io.trishul.iaas.access.aws.AwsIamRolePolicyAttachmentClient;
import io.trishul.iaas.access.aws.IaasAccessAwsFactory;
import io.trishul.iaas.client.IaasClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class IaasAccessAwsAutoConfigurationTest {
  private IaasAccessAwsAutoConfiguration config;

  @BeforeEach
  void init() {
    config = new IaasAccessAwsAutoConfiguration();
  }

  @Test
  void testIaasAccessAwsFactory_ReturnsNonNullInstance() {
    IaasAccessAwsFactory factory = config.iaasAccessAwsFactory();
    assertNotNull(factory);
  }

  @Test
  void testIaasAccessAwsFactory_ReturnsInstanceOfIaasAccessAwsFactory() {
    IaasAccessAwsFactory factory = config.iaasAccessAwsFactory();
    assertTrue(factory instanceof IaasAccessAwsFactory);
  }

  @Test
  void testArnMapper_ReturnsNonNullInstance() {
    AwsArnMapper mapper = config.arnMapper("123456789012", "aws");
    assertNotNull(mapper);
  }

  @Test
  void testArnMapper_ReturnsInstanceOfAwsArnMapper() {
    AwsArnMapper mapper = config.arnMapper("123456789012", "aws");
    assertTrue(mapper instanceof AwsArnMapper);
  }

  @Test
  void testIaasPolicyClient_ReturnsNonNullInstance() {
    AmazonIdentityManagement mIamClient = mock(AmazonIdentityManagement.class);
    AwsArnMapper mArnMapper = mock(AwsArnMapper.class);

    IaasClient<?, ?, ?, ?> client = config.iaasPolicyClient(mIamClient, mArnMapper);

    assertNotNull(client);
  }

  @Test
  void testIaasPolicyClient_ReturnsInstanceOfAwsIamPolicyClient() {
    AmazonIdentityManagement mIamClient = mock(AmazonIdentityManagement.class);
    AwsArnMapper mArnMapper = mock(AwsArnMapper.class);

    IaasClient<?, ?, ?, ?> client = config.iaasPolicyClient(mIamClient, mArnMapper);

    assertTrue(client instanceof AwsIamPolicyClient);
  }

  @Test
  void testIaasRoleClient_ReturnsNonNullInstance() {
    AmazonIdentityManagement mIamClient = mock(AmazonIdentityManagement.class);

    IaasClient<?, ?, ?, ?> client = config.iaasRoleClient(mIamClient);

    assertNotNull(client);
  }

  @Test
  void testIaasRoleClient_ReturnsInstanceOfAwsIamRoleClient() {
    AmazonIdentityManagement mIamClient = mock(AmazonIdentityManagement.class);

    IaasClient<?, ?, ?, ?> client = config.iaasRoleClient(mIamClient);

    assertTrue(client instanceof AwsIamRoleClient);
  }

  @Test
  void testAwsIamRolePolicyClientClient_ReturnsNonNullInstance() {
    AmazonIdentityManagementClient mIamClient = mock(AmazonIdentityManagementClient.class);
    AwsArnMapper mArnMapper = mock(AwsArnMapper.class);

    IaasClient<?, ?, ?, ?> client = config.awsIamRolePolicyClientClient(mIamClient, mArnMapper);

    assertNotNull(client);
  }

  @Test
  void testAwsIamRolePolicyClientClient_ReturnsInstanceOfAwsIamRolePolicyAttachmentClient() {
    AmazonIdentityManagementClient mIamClient = mock(AmazonIdentityManagementClient.class);
    AwsArnMapper mArnMapper = mock(AwsArnMapper.class);

    IaasClient<?, ?, ?, ?> client = config.awsIamRolePolicyClientClient(mIamClient, mArnMapper);

    assertTrue(client instanceof AwsIamRolePolicyAttachmentClient);
  }
}
