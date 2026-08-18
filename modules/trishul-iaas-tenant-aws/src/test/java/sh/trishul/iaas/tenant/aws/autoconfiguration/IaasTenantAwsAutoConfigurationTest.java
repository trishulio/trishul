package sh.trishul.iaas.tenant.aws.autoconfiguration;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sh.trishul.config.AppConfig;
import sh.trishul.iaas.tenant.aws.AwsDocumentTemplates;
import sh.trishul.iaas.tenant.resource.TenantIaasResourceBuilder;
import sh.trishul.object.store.file.service.service.IaasObjectStoreNameProvider;
import sh.trishul.tenant.auth.model.ContextHolderTenantIdProvider;

class IaasTenantAwsAutoConfigurationTest {

  private IaasTenantAwsAutoConfiguration config;

  @BeforeEach
  void setUp() {
    config = new IaasTenantAwsAutoConfiguration();
  }

  @Test
  void testAwsDocumentTemplates_ReturnsNonNull() {
    String cognitoIdPoolId = "test-pool-id";
    AppConfig appConfig = mock(AppConfig.class);
    doReturn("test-app").when(appConfig).getName();

    AwsDocumentTemplates result = config.awsDocumentTemplates(cognitoIdPoolId, appConfig);

    assertNotNull(result);
  }

  @Test
  void testIaasObjectStoreNameProvider_ReturnsNonNull() {
    AwsDocumentTemplates mockDocumentTemplates = mock(AwsDocumentTemplates.class);
    ContextHolderTenantIdProvider mockTenantIdProvider = mock(ContextHolderTenantIdProvider.class);
    String appBucketName = "test-bucket";

    IaasObjectStoreNameProvider result = config.iaasObjectStoreNameProvider(mockDocumentTemplates,
        mockTenantIdProvider, appBucketName);

    assertNotNull(result);
  }

  @Test
  void testTenantIaasResourceBuilder_ReturnsNonNull() {
    AwsDocumentTemplates mockDocumentTemplates = mock(AwsDocumentTemplates.class);
    List<String> allowedHeaders = List.of("Content-Type");
    List<String> allowedMethods = List.of("GET", "PUT");
    List<String> allowedOrigins = List.of("*");

    TenantIaasResourceBuilder result = config.tenantIaasResourceBuilder(mockDocumentTemplates,
        allowedHeaders, allowedMethods, allowedOrigins, true, // blockPublicAcls
        true, // ignorePublicAcls
        true, // blockPublicPolicy
        true // restrictPublicBuckets
    );

    assertNotNull(result);
  }
}
