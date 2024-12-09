package io.trishul.iaas.auth.aws.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.cognitoidentity.AmazonCognitoIdentity;
import com.amazonaws.services.cognitoidentity.AmazonCognitoIdentityClientBuilder;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProviderClient;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProviderClientBuilder;

public class AwsFactory {
    private static final Logger logger = LoggerFactory.getLogger(AwsFactory.class);

    public AWSCognitoIdentityProvider getIdentityProvider(final String cognitoRegion, String cognitoUrl, String cognitoAccessKeyId, String cognitoAccessSecretKey) {
        logger.debug("Creating an instance of AwsCognitoIdp");
        final AwsClientBuilder.EndpointConfiguration endpointConfig = new AwsClientBuilder.EndpointConfiguration(cognitoUrl, cognitoRegion);
        final AWSCognitoIdentityProviderClientBuilder idpClientBuilder = AWSCognitoIdentityProviderClient.builder().withEndpointConfiguration(endpointConfig);
        BasicAWSCredentials basicAwsCredentials = new BasicAWSCredentials(cognitoAccessKeyId, cognitoAccessSecretKey);
        idpClientBuilder.withCredentials(new AWSStaticCredentialsProvider(basicAwsCredentials));

        AWSCognitoIdentityProvider awsCognitoIdp = idpClientBuilder.build();

        return awsCognitoIdp;
    }

    public AmazonCognitoIdentity getAwsCognitoIdentityClient(String region, String accessKeyId, String accessSecretKey) {
        AWSCredentials creds = new BasicAWSCredentials(accessKeyId, accessSecretKey);
        AWSStaticCredentialsProvider credsProvider = new AWSStaticCredentialsProvider(creds);
        AmazonCognitoIdentity cognitoIdentityClient = AmazonCognitoIdentityClientBuilder.standard()
                                                                                        .withCredentials(credsProvider)
                                                                                        .withRegion(region)
                                                                                        .build();
        return cognitoIdentityClient;
    }
}
