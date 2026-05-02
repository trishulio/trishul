package io.trishul.iaas.tenant.aws;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import com.amazonaws.services.s3.model.CORSRule.AllowedMethods;
import io.trishul.iaas.access.policy.model.IaasPolicy;
import io.trishul.iaas.access.role.attachment.policy.IaasRolePolicyAttachment;
import io.trishul.iaas.access.role.attachment.policy.IaasRolePolicyAttachmentId;
import io.trishul.iaas.access.role.model.IaasRole;
import io.trishul.iaas.idp.tenant.model.IaasIdpTenant;
import io.trishul.object.store.configuration.access.model.IaasObjectStoreAccessConfig;
import io.trishul.object.store.configuration.cors.model.IaasObjectStoreCorsConfiguration;
import io.trishul.object.store.model.IaasObjectStore;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AwsTenantIaasResourceBuilderTest {
  private AwsTenantIaasResourceBuilder builder;
  private AwsDocumentTemplates mTemplates;

  @BeforeEach
  void init() {
    mTemplates = mock(AwsDocumentTemplates.class);
    this.builder = new AwsTenantIaasResourceBuilder(mTemplates, List.of("*"),
        List.of(AllowedMethods.PUT.toString(), AllowedMethods.POST.toString(),
            AllowedMethods.DELETE.toString()),
        List.of("http://wwww.localhost:3000/", "https://locahost//", "https://locahost//path",
            "https://locahost/path//"),
        true, true, true, true);
  }

  @Test
  void testConstructor_WithNullLists_DoesNotThrowException() {
    AwsTenantIaasResourceBuilder nullListBuilder
        = new AwsTenantIaasResourceBuilder(mTemplates, null, null, null, true, true, true, true);
    assertNotNull(nullListBuilder);
  }

  @Test
  void testGetRoleName_ReturnsRoleNameFromTemplateWithTenantName() {
    doAnswer(inv -> inv.getArgument(0, String.class) + "_ROLE_NAME").when(mTemplates)
        .getTenantIaasRoleName(anyString());

    String roleName = builder.getRoleId("T1");

    assertEquals("T1_ROLE_NAME", roleName);
  }

  @Test
  void testBuildRole_ReturnsRoleObjectFromTenant() {
    doAnswer(inv -> inv.getArgument(0, String.class) + "_ROLE_NAME").when(mTemplates)
        .getTenantIaasRoleName(anyString());
    doAnswer(inv -> inv.getArgument(0, String.class) + "_ROLE_DESCRIPTION").when(mTemplates)
        .getTenantIaasRoleDescription(anyString());
    doReturn("ROLE_DOC").when(mTemplates).getCognitoIdAssumeRolePolicyDoc();

    IaasRole role = builder.buildRole(new IaasIdpTenant("T1"));

    assertEquals("T1_ROLE_NAME", role.getName());
    assertEquals("T1_ROLE_DESCRIPTION", role.getDescription());
    assertEquals("ROLE_DOC", role.getAssumePolicyDocument());
  }

  @Test
  void testGetVfsPolicyName_ReturnsVfsPolicyNameFromTenant() {
    doAnswer(inv -> inv.getArgument(0, String.class) + "_POLICY_NAME").when(mTemplates)
        .getTenantVfsPolicyName(anyString());

    String policyName = builder.getVfsPolicyId("T1");

    assertEquals("T1_POLICY_NAME", policyName);
  }

  @Test
  void testBuildVfsPolicy_ReturnsVfsPolicyObjectFromTenant() {
    doAnswer(inv -> inv.getArgument(0, String.class) + "_POLICY_NAME").when(mTemplates)
        .getTenantVfsPolicyName(anyString());
    doAnswer(inv -> inv.getArgument(0, String.class) + "_POLICY_DESCRIPTION").when(mTemplates)
        .getTenantVfsPolicyDescription(anyString());
    doAnswer(inv -> inv.getArgument(0, String.class) + "_POLICY_DOC").when(mTemplates)
        .getTenantBucketPolicyDoc(anyString());

    IaasPolicy policy = builder.buildVfsPolicy(new IaasIdpTenant("T1"));

    assertEquals("T1_POLICY_NAME", policy.getName());
    assertEquals("T1_POLICY_DESCRIPTION", policy.getDescription());
    assertEquals("T1_POLICY_DOC", policy.getDocument());
  }

  @Test
  void testGetObjectStoreName_ReturnsObjectStoreNameFromTenant() {
    doAnswer(inv -> inv.getArgument(0, String.class) + "_OBJECT_STORE_NAME").when(mTemplates)
        .getTenantVfsBucketName(anyString());

    String objectStoreName = builder.getObjectStoreId("T1");

    assertEquals("T1_OBJECT_STORE_NAME", objectStoreName);
  }

  @Test
  void testBuildObjectStore_ReturnsObjectStoreObjectFromTenant() {
    doAnswer(inv -> inv.getArgument(0, String.class) + "_OBJECT_STORE_NAME").when(mTemplates)
        .getTenantVfsBucketName(anyString());

    IaasObjectStore objectStore = builder.buildObjectStore(new IaasIdpTenant("T1"));

    assertEquals("T1_OBJECT_STORE_NAME", objectStore.getName());
  }

  @Test
  void testBuildVfsAttachmentId_ReturnsAttachmentIdFromTenant() {
    doAnswer(inv -> inv.getArgument(0, String.class) + "_ROLE_NAME").when(mTemplates)
        .getTenantIaasRoleName(anyString());
    doAnswer(inv -> inv.getArgument(0, String.class) + "_POLICY_NAME").when(mTemplates)
        .getTenantVfsPolicyName(anyString());

    IaasRolePolicyAttachmentId id = builder.buildVfsAttachmentId("T1");

    assertEquals("T1_ROLE_NAME", id.getRoleId());
    assertEquals("T1_POLICY_NAME", id.getPolicyId());
  }

  @Test
  void testBuildAttachment_ReturnsAttachmentFromRoleAndPolicy() {
    IaasRole role = new IaasRole("T1_ROLE");
    IaasPolicy policy = new IaasPolicy("T1_POLICY");

    IaasRolePolicyAttachment attachment = builder.buildAttachment(role, policy);

    assertEquals("T1_ROLE", attachment.getIaasRole().getName());
    assertEquals("T1_POLICY", attachment.getIaasPolicy().getName());
  }

  @Test
  void testBuildObjectStoreCorsConfiguration_ReturnsCrossOriginConfig() {
    doAnswer(inv -> inv.getArgument(0, String.class) + "_OBJECT_STORE_NAME").when(mTemplates)
        .getTenantVfsBucketName(anyString());

    IaasObjectStoreCorsConfiguration actual
        = builder.buildObjectStoreCorsConfiguration(new IaasIdpTenant("T1"));

    assertEquals("T1_OBJECT_STORE_NAME", actual.getId());
    assertEquals("T1_OBJECT_STORE_NAME", actual.getBucketName());

    assertEquals(1, actual.getBucketCrossOriginConfiguration().getRules().size());
    assertEquals(List.of("*"),
        actual.getBucketCrossOriginConfiguration().getRules().get(0).getAllowedHeaders());
    assertEquals(List.of(AllowedMethods.PUT, AllowedMethods.POST, AllowedMethods.DELETE),
        actual.getBucketCrossOriginConfiguration().getRules().get(0).getAllowedMethods());
    // Trailing slashes are stripped except for double slashes in paths
    assertEquals(
        List.of("http://wwww.localhost:3000", "https://locahost", "https://locahost//path",
            "https://locahost/path"),
        actual.getBucketCrossOriginConfiguration().getRules().get(0).getAllowedOrigins());
  }

  @Test
  void testBuildPublicAccessBlock_ReturnsPublicAccessBlock() {
    doAnswer(inv -> inv.getArgument(0, String.class) + "_OBJECT_STORE_NAME").when(mTemplates)
        .getTenantVfsBucketName(anyString());

    IaasObjectStoreAccessConfig actual = builder.buildPublicAccessBlock(new IaasIdpTenant("T1"));

    assertEquals("T1_OBJECT_STORE_NAME", actual.getId());
    assertEquals("T1_OBJECT_STORE_NAME", actual.getObjectStoreName());
    assertEquals(true, actual.getPublicAccessBlockConfig().getBlockPublicAcls());
    assertEquals(true, actual.getPublicAccessBlockConfig().getBlockPublicPolicy());
    assertEquals(true, actual.getPublicAccessBlockConfig().getIgnorePublicAcls());
    assertEquals(true, actual.getPublicAccessBlockConfig().getRestrictPublicBuckets());
  }

  @Test
  void testBuildPublicAccessBlock_ReturnsFalseFlags_WhenConstructedWithFalse() {
    AwsTenantIaasResourceBuilder falseBuilder = new AwsTenantIaasResourceBuilder(mTemplates,
        List.of("*"), List.of(AllowedMethods.GET.toString()), List.of("http://localhost"), false,
        false, false, false);

    doAnswer(inv -> inv.getArgument(0, String.class) + "_BUCKET").when(mTemplates)
        .getTenantVfsBucketName(anyString());

    IaasObjectStoreAccessConfig actual
        = falseBuilder.buildPublicAccessBlock(new IaasIdpTenant("T2"));

    assertEquals(false, actual.getPublicAccessBlockConfig().getBlockPublicAcls());
    assertEquals(false, actual.getPublicAccessBlockConfig().getBlockPublicPolicy());
    assertEquals(false, actual.getPublicAccessBlockConfig().getIgnorePublicAcls());
    assertEquals(false, actual.getPublicAccessBlockConfig().getRestrictPublicBuckets());
  }
}
