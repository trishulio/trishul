package io.trishul.iaas.tenant.aws;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AwsDocumentTemplatesTest {
  private AwsDocumentTemplates templates;

  @BeforeEach
  public void init() {
    templates = new AwsDocumentTemplates("COGNITO_ID_POOL_ID");
  }

  @Test
  public void testGetTenantBucketPolicyDoc() {
    assertEquals("{\n" + "    \"Version\": \"2012-10-17\",\n" + "    \"Statement\": [\n"
        + "        {\n" + "            \"Sid\": \"VisualEditor0\",\n"
        + "            \"Effect\": \"Allow\",\n" + "            \"Action\": [\n"
        + "                \"s3:DeleteObjectTagging\",\n"
        + "                \"s3:ListBucketMultipartUploads\",\n"
        + "                \"s3:GetJobTagging\",\n"
        + "                \"s3:DeleteObjectVersion\",\n"
        + "                \"s3:RestoreObject\",\n" + "                \"s3:PutJobTagging\",\n"
        + "                \"s3:ListBucket\",\n"
        + "                \"s3:ListMultipartUploadParts\",\n"
        + "                \"s3:PutObject\",\n" + "                \"s3:GetObject\",\n"
        + "                \"s3:DeleteJobTagging\",\n" + "                \"s3:DescribeJob\",\n"
        + "                \"s3:GetObjectTagging\",\n"
        + "                \"s3:PutObjectTagging\",\n" + "                \"s3:DeleteObject\",\n"
        + "                \"s3:GetBucketLocation\"\n" + "            ],\n"
        + "            \"Resource\": \"arn:aws:s3:::t-T1-vfs/*\"\n" + "        }\n" + "    ]\n"
        + "}", templates.getTenantBucketPolicyDoc("T1"));
  }

  @Test
  public void testGetCognitoIdAssumeRolePolicyDoc() {
    assertEquals(
        "{\n" + "  \"Version\": \"2012-10-17\",\n" + "  \"Statement\": [\n" + "    {\n"
            + "      \"Effect\": \"Allow\",\n" + "      \"Principal\": {\n"
            + "        \"Federated\": \"cognito-identity.amazonaws.com\"\n" + "      },\n"
            + "      \"Action\": \"sts:AssumeRoleWithWebIdentity\",\n" + "      \"Condition\": {\n"
            + "        \"StringEquals\": {\n"
            + "          \"cognito-identity.amazonaws.com:aud\": \"COGNITO_ID_POOL_ID\"\n"
            + "        }\n" + "      }\n" + "    }\n" + "  ]\n" + "}",
        templates.getCognitoIdAssumeRolePolicyDoc());
  }

  @Test
  public void testGetTenantVfsBucketName() {
    assertEquals("t-T1-vfs", templates.getTenantVfsBucketName("T1"));
  }

  @Test
  public void testGetTenantIaasRoleName() {
    assertEquals("t-T1-iaas", templates.getTenantIaasRoleName("T1"));
  }

  @Test
  public void testGetTenantVfsPolicyName() {
    assertEquals("t-T1-vfs", templates.getTenantVfsPolicyName("T1"));
  }

  @Test
  public void testGetTenantVfsPolicyDescription() {
    assertEquals("File storage for tenant: T1", templates.getTenantVfsPolicyDescription("T1"));
  }

  @Test
  public void testGetTenantIaasRoleDescription() {
    assertEquals("Role assumed by tenant-users to gain access to the Iaas resources: T1",
        templates.getTenantIaasRoleDescription("T1"));
  }
}
