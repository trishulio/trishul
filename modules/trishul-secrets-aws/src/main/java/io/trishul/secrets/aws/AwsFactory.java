package io.trishul.secrets.SecretsManager.aws;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.secretsmanager.AWSSecretsManager;
import com.amazonaws.services.secretsmanager.AWSSecretsManagerClientBuilder;

public class AwsFactory {
    private static final Logger logger = LoggerFactory.getLogger(AwsFactory.class);

    public AWSSecretsManager secretsMgrClient(String region, String url, String accessKeyId, String accessSecretKey) {
        AWSCredentials creds = new BasicAWSCredentials(accessKeyId, accessSecretKey);
        AWSStaticCredentialsProvider credsProvider = new AWSStaticCredentialsProvider(creds);

        AwsClientBuilder.EndpointConfiguration endpointConfig = new AwsClientBuilder.EndpointConfiguration(url, region);
        AWSSecretsManager client = AWSSecretsManagerClientBuilder.standard()
                                                                 .withEndpointConfiguration(endpointConfig)
                                                                 .withCredentials(credsProvider)
                                                                 .build();

        return client;
    }
}
