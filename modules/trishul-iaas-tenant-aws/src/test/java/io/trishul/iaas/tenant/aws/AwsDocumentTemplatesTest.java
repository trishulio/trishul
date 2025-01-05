package io.trishul.iaas.tenant.aws;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

public class AwsDocumentTemplatesTest {
  private AwsDocumentTemplates templates;

  @BeforeEach
  public void init() {
    templates = new AwsDocumentTemplates("COGNITO_ID_POOL_ID");
  }

  @Test
  public void testGetTenantBucketPolicyDoc() throws JSONException {
        JSONAssert.assertEquals("""
        {
          "Version": "2012-10-17",
          "Statement": [
              {
                  "Sid": "VisualEditor0",
                  "Effect": "Allow",
                  "Action": [
                      "s3:DeleteObjectTagging",
                      "s3:ListBucketMultipartUploads",
                      "s3:GetJobTagging",
                      "s3:DeleteObjectVersion",
                      "s3:RestoreObject",
                      "s3:PutJobTagging",
                      "s3:ListBucket",
                      "s3:ListMultipartUploadParts",
                      "s3:PutObject",
                      "s3:GetObject",
                      "s3:DeleteJobTagging",
                      "s3:DescribeJob",
                      "s3:GetObjectTagging",
                      "s3:PutObjectTagging",
                      "s3:DeleteObject",
                      "s3:GetBucketLocation"
                  ],
                  "Resource": "arn:aws:s3:::t-T1-vfs/*"
              }
          ]
        } 
        """, templates.getTenantBucketPolicyDoc("T1"), JSONCompareMode.NON_EXTENSIBLE);
  }

  @Test
  public void testGetCognitoIdAssumeRolePolicyDoc() throws JSONException {
    JSONAssert.assertEquals("""
      {
        "Version": "2012-10-17",
        "Statement": [
            {
                "Effect": "Allow",
                "Principal": {
                    "Federated": "cognito-identity.amazonaws.com"
                },
                "Action": "sts:AssumeRoleWithWebIdentity",
                "Condition": {
                    "StringEquals": {
                        "cognito-identity.amazonaws.com:aud": "COGNITO_ID_POOL_ID"
                    }
                }
            }
        ]
      }
    """, templates.getCognitoIdAssumeRolePolicyDoc(), JSONCompareMode.NON_EXTENSIBLE);
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
