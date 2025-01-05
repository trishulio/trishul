// TODO: Split this up into each modules
package io.trishul.iaas.tenant.aws;

public class AwsDocumentTemplates {
  private static final String POLICY_DOC_TENANT_BUCKET = """
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
                  "Resource": "arn:aws:s3:::%s/*"
              }
          ]
      }""";

  private static final String POLICY_DOC_COGNITO_ID_ASSUME_ROLE = """
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
                          "cognito-identity.amazonaws.com:aud": "%s"
                      }
                  }
              }
          ]
      }""";

  private static final String BUCKET_NAME_TENANT_VFS = "t-%s-vfs";

  private static final String ROLE_NAME_TENANT_IAAS = "t-%s-iaas";

  private static final String ROLE_DESCRIPTION_TENANT_VFS
      = "Role assumed by tenant-users to gain access to the Iaas resources: %s";

  private static final String POLICY_NAME_TENANT_VFS = "t-%s-vfs";

  private static final String POLICY_DESCRIPTION_TENANT_VFS = "File storage for tenant: %s";

  private final String cognitoIdPoolId;

  public AwsDocumentTemplates(String cognitoIdPoolId) {
    this.cognitoIdPoolId = cognitoIdPoolId;
  }

  public String getTenantBucketPolicyDoc(String iaasIdpTenantId) {
    String bucketName = getTenantVfsBucketName(iaasIdpTenantId);
    return String.format(POLICY_DOC_TENANT_BUCKET, bucketName);
  }

  public String getCognitoIdAssumeRolePolicyDoc() {
    return String.format(POLICY_DOC_COGNITO_ID_ASSUME_ROLE, cognitoIdPoolId);
  }

  public String getTenantVfsBucketName(String iaasIdpTenantId) {
    return String.format(BUCKET_NAME_TENANT_VFS, iaasIdpTenantId);
  }

  public String getTenantIaasRoleName(String iaasIdpTenantId) {
    return String.format(ROLE_NAME_TENANT_IAAS, iaasIdpTenantId);
  }

  public String getTenantVfsPolicyName(String iaasIdpTenantId) {
    return String.format(POLICY_NAME_TENANT_VFS, iaasIdpTenantId);
  }

  public String getTenantVfsPolicyDescription(String iaasIdpTenantId) {
    return String.format(POLICY_DESCRIPTION_TENANT_VFS, iaasIdpTenantId);
  }

  public String getTenantIaasRoleDescription(String iaasIdpTenantId) {
    return String.format(ROLE_DESCRIPTION_TENANT_VFS, iaasIdpTenantId);
  }
}
