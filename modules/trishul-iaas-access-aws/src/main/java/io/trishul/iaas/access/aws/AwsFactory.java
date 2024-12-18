package io.trishul.iaas.access.aws;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.identitymanagement.AmazonIdentityManagement;
import com.amazonaws.services.identitymanagement.AmazonIdentityManagementClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AwsFactory {
    private static final Logger logger = LoggerFactory.getLogger(AwsFactory.class);

    public AmazonIdentityManagement iamClient(String iamAccessKeyId, String iamSecret) {
        BasicAWSCredentials basicAwsCredentials =
                new BasicAWSCredentials(iamAccessKeyId, iamSecret);
        AWSCredentialsProvider credsProvider =
                new AWSStaticCredentialsProvider(basicAwsCredentials);

        AmazonIdentityManagement awsIamClient =
                AmazonIdentityManagementClientBuilder.standard()
                        .withCredentials(credsProvider)
                        // Note: IAM services are non-regional. The region defaults to US_EAST_1.
                        // We hard-code a value here to avoid making network calls that builder
                        // makes behind the scenes when this parameter is not being set.
                        .withRegion(Regions.US_EAST_1)
                        .build();

        return awsIamClient;
    }
}
