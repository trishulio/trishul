package io.trishul.iaas.access.aws;

import com.amazonaws.arn.Arn;

public class AwsArnMapper {
    public static final String AWS_SERVICE_IAM = "iam";
    public static final String AWS_SERVICE_PREFIX_POLICY = "policy/";
    public static final String AWS_SERVICE_PREFIX_ROLE = "role/";

    private String accountId;
    private String partition;

    public AwsArnMapper(String accountId, String partition) {
        this.accountId = accountId;
        this.partition = partition;
    }

    public String getPolicyArn(String policyName) {
        return getServiceArn("", AWS_SERVICE_IAM, AWS_SERVICE_PREFIX_POLICY, policyName);
    }

    public String getRoleArn(String roleName) {
        return getServiceArn("", AWS_SERVICE_IAM, AWS_SERVICE_PREFIX_ROLE, roleName);
    }

    public String getName(String arn) {
        return Arn.fromString(arn).getResource().getResource();
    }

    private String getServiceArn(String region, String serviceName, String resourcePrefix, String resourceName) {
        String arn = Arn.builder()
            .withAccountId(this.accountId)
            .withPartition(this.partition)
            .withService(serviceName)
            .withRegion(region)
            .withResource(resourcePrefix + resourceName)
            .build()
            .toString();

        return arn.toLowerCase();
    }
}
