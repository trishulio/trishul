// TODO: Reenable 
// package io.trishul.iaas.tenant.aws;

// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.mockito.ArgumentMatchers.anyString;
// import static org.mockito.Mockito.*;

// import java.util.List;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;

// import com.amazonaws.services.s3.model.CORSRule.AllowedMethods;

// import io.trishul.iaas.tenant.aws.AwsDocumentTemplates;
// import io.trishul.iaas.idp.tenant.model.IaasIdpTenant;
// import io.trishul.object.store.model.IaasObjectStore;
// import io.trishul.object.store.configuration.access.model.IaasObjectStoreAccessConfig;
// import io.trishul.object.store.configuration.cors.model.IaasObjectStoreCorsConfiguration;
// import io.trishul.iaas.access.policy.model.IaasPolicy;
// import io.trishul.iaas.access.role.model.IaasRole;
// import io.trishul.iaas.access.role.attachment.policy.IaasRolePolicyAttachment;
// import io.trishul.iaas.access.role.attachment.policy.IaasRolePolicyAttachmentId;
// import io.trishul.iaas.tenant.aws.AwsTenantIaasResourceBuilder;
// import io.trishul.iaas.tenant.resource.TenantIaasResourceBuilder;

// public class AwsTenantIaasResourceBuilderTest {
//     private TenantIaasResourceBuilder builder;
//     private AwsDocumentTemplates mTemplates;

//     @BeforeEach
//     public void init() {
//         mTemplates = mock(AwsDocumentTemplates.class);
//         this.builder = new AwsTenantIaasResourceBuilder(mTemplates, List.of("*"), List.of(AllowedMethods.PUT.toString(), AllowedMethods.POST.toString(), AllowedMethods.DELETE.toString()), List.of("http://wwww.localhost:3000/", "https://locahost//", "https://locahost//path", "https://locahost/path//"), true, true, true, true);
//     }

//     @Test
//     public void testGetRoleName_ReturnsRoleNameFromTemplateWithTenantName() {
//         doAnswer(inv -> inv.getArgument(0, String.class) + "_ROLE_NAME").when(mTemplates).getTenantIaasRoleName(anyString());

//         String roleName = builder.getRoleId("T1");

//         assertEquals("T1_ROLE_NAME", roleName);
//     }

//     @Test
//     public void testBuildRole_ReturnsRoleObjectFromTenant() {
//         doAnswer(inv -> inv.getArgument(0, String.class) + "_ROLE_NAME").when(mTemplates).getTenantIaasRoleName(anyString());
//         doAnswer(inv -> inv.getArgument(0, String.class) + "_ROLE_DESCRIPTION").when(mTemplates).getTenantIaasRoleDescription(anyString());
//         doReturn("ROLE_DOC").when(mTemplates).getCognitoIdAssumeRolePolicyDoc();

//         IaasRole role = builder.buildRole(new IaasIdpTenant("T1"));

//         IaasRole expected = new IaasRole("T1_ROLE_NAME", "T1_ROLE_DESCRIPTION", "ROLE_DOC", null, null, null, null, null);

//         assertEquals(expected, role);
//     }

//     @Test
//     public void testGetVfsPolicyName_ReturnsVfsPolicyNameFromTenant() {
//         doAnswer(inv -> inv.getArgument(0, String.class) + "_POLICY_NAME").when(mTemplates).getTenantVfsPolicyName(anyString());

//         String policyName = builder.getVfsPolicyId("T1");

//         assertEquals("T1_POLICY_NAME", policyName);
//     }

//     @Test
//     public void testBuildVfsPolicy_ReturnsVfsPolicyObjectFromTenant() {
//         doAnswer(inv -> inv.getArgument(0, String.class) + "_POLICY_NAME").when(mTemplates).getTenantVfsPolicyName(anyString());
//         doAnswer(inv -> inv.getArgument(0, String.class) + "_POLICY_DESCRIPTION").when(mTemplates).getTenantVfsPolicyDescription(anyString());
//         doAnswer(inv -> inv.getArgument(0, String.class) + "_POLICY_DOC").when(mTemplates).getTenantBucketPolicyDoc(anyString());

//         IaasPolicy policy = builder.buildVfsPolicy(new IaasIdpTenant("T1"));

//         IaasPolicy expected = new IaasPolicy("T1_POLICY_NAME", "T1_POLICY_DOC", "T1_POLICY_DESCRIPTION", null, null, null, null);

//         assertEquals(expected, policy);
//     }

//     @Test
//     public void testGetObjectStoreName_ReturnsObjectStoreNameFromTenant() {
//         doAnswer(inv -> inv.getArgument(0, String.class) + "_OBJECT_STORE_NAME").when(mTemplates).getTenantVfsBucketName(anyString());

//         String objectStoreName = builder.getObjectStoreId("T1");

//         assertEquals("T1_OBJECT_STORE_NAME", objectStoreName);
//     }

//     @Test
//     public void testBuildObjectStore_ReturnsObjectStoreObjectFromTenant() {
//         doAnswer(inv -> inv.getArgument(0, String.class) + "_OBJECT_STORE_NAME").when(mTemplates).getTenantVfsBucketName(anyString());

//         IaasObjectStore objectStore = builder.buildObjectStore(new IaasIdpTenant("T1"));

//         IaasObjectStore expected = new IaasObjectStore("T1_OBJECT_STORE_NAME");

//         assertEquals(expected, objectStore);
//     }

//     @Test
//     public void testBuildVfsAttachmentId_ReturnsAttachmentIdFromTenant() {
//         doAnswer(inv -> inv.getArgument(0, String.class) + "_ROLE_NAME").when(mTemplates).getTenantIaasRoleName(anyString());
//         doAnswer(inv -> inv.getArgument(0, String.class) + "_POLICY_NAME").when(mTemplates).getTenantVfsPolicyName(anyString());

//         IaasRolePolicyAttachmentId id = builder.buildVfsAttachmentId("T1");

//         IaasRolePolicyAttachmentId expected = new IaasRolePolicyAttachmentId("T1_ROLE_NAME", "T1_POLICY_NAME");
//         assertEquals(expected, id);
//     }

//     @Test
//     public void testBuildAttachment_ReturnsAttachmentFromRoleAndPolicy() {
//         IaasRolePolicyAttachment attachment = builder.buildAttachment(new IaasRole("T1_ROLE"), new IaasPolicy("T1_POLICY"));

//         IaasRolePolicyAttachment expected = new IaasRolePolicyAttachment(new IaasRole("T1_ROLE"), new IaasPolicy("T1_POLICY"));

//         assertEquals(expected, attachment);
//     }

//     @Test
//     public void testBuildObjectStoreCorsConfiguration_ReturnsCrossOriginConfig() {
//         doAnswer(inv -> inv.getArgument(0, String.class) + "_OBJECT_STORE_NAME").when(mTemplates).getTenantVfsBucketName(anyString());

//         IaasObjectStoreCorsConfiguration actual = builder.buildObjectStoreCorsConfiguration(new IaasIdpTenant("T1"));

//         assertEquals("T1_OBJECT_STORE_NAME", actual.getId());
//         assertEquals("T1_OBJECT_STORE_NAME", actual.getBucketName());

//         assertEquals(1, actual.getBucketCrossOriginConfiguration().getRules().size());
//         assertEquals(List.of("*"), actual.getBucketCrossOriginConfiguration().getRules().get(0).getAllowedHeaders());
//         assertEquals(List.of(AllowedMethods.PUT, AllowedMethods.POST, AllowedMethods.DELETE), actual.getBucketCrossOriginConfiguration().getRules().get(0).getAllowedMethods());
//         assertEquals(List.of("http://wwww.localhost:3000", "https://locahost", "https://locahost//path", "https://locahost/path"), actual.getBucketCrossOriginConfiguration().getRules().get(0).getAllowedOrigins());
//     }

//     @Test
//     public void testBuildPublicAccessBlock_ReturnsPublicAccessBlock() {
//         doAnswer(inv -> inv.getArgument(0, String.class) + "_OBJECT_STORE_NAME").when(mTemplates).getTenantVfsBucketName(anyString());

//         IaasObjectStoreAccessConfig actual = builder.buildPublicAccessBlock(new IaasIdpTenant("T1"));

//         assertEquals("T1_OBJECT_STORE_NAME", actual.getId());
//         assertEquals("T1_OBJECT_STORE_NAME", actual.getObjectStoreName());
//         assertEquals(true, actual.getPublicAccessBlockConfig().getBlockPublicAcls());
//         assertEquals(true, actual.getPublicAccessBlockConfig().getBlockPublicPolicy());
//         assertEquals(true, actual.getPublicAccessBlockConfig().getIgnorePublicAcls());
//         assertEquals(true, actual.getPublicAccessBlockConfig().getRestrictPublicBuckets());
//     }
// }
