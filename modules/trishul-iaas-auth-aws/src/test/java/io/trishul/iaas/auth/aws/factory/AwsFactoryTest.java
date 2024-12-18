package io.trishul.iaas.auth.aws.factory;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.services.cognitoidentity.AmazonCognitoIdentity;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import java.net.URI;
import java.net.URISyntaxException;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AwsFactoryTest {
    private AwsFactory factory;

    @BeforeEach
    public void init() {
        factory = new AwsFactory();
    }

    @Test
    public void testGetIdentityProvider() throws IllegalAccessException, URISyntaxException {
        AWSCognitoIdentityProvider idp =
                factory.getIdentityProvider("REGION", "URL", "ACCESS_KEY_ID", "ACCESS_SECRET_KEY");

        final AWSCredentialsProvider awsCredentialsProvider =
                (AWSCredentialsProvider) FieldUtils.readField(idp, "awsCredentialsProvider", true);
        final URI endpoint = (URI) FieldUtils.readField(idp, "endpoint", true);
        final String region = (String) FieldUtils.readField(idp, "signingRegion", true);

        assertEquals(new URI("https://URL"), endpoint);
        assertEquals("REGION", region);
        assertEquals(awsCredentialsProvider.getCredentials().getAWSAccessKeyId(), "ACCESS_KEY_ID");
        assertEquals(
                awsCredentialsProvider.getCredentials().getAWSSecretKey(), "ACCESS_SECRET_KEY");
    }

    @Test
    public void testGetAwsCognitoIdentityClient()
            throws IllegalAccessException, URISyntaxException {
        AmazonCognitoIdentity idp =
                factory.getAwsCognitoIdentityClient("REGION", "ACCESS_KEY_ID", "ACCESS_SECRET_KEY");

        final AWSCredentialsProvider awsCredentialsProvider =
                (AWSCredentialsProvider) FieldUtils.readField(idp, "awsCredentialsProvider", true);
        final URI endpoint = (URI) FieldUtils.readField(idp, "endpoint", true);
        final String region = (String) FieldUtils.readField(idp, "signingRegion", true);

        assertEquals(new URI("https://cognito-identity.REGION.amazonaws.com"), endpoint);
        assertEquals("REGION", region);
        assertEquals(awsCredentialsProvider.getCredentials().getAWSAccessKeyId(), "ACCESS_KEY_ID");
        assertEquals(
                awsCredentialsProvider.getCredentials().getAWSSecretKey(), "ACCESS_SECRET_KEY");
    }
}
