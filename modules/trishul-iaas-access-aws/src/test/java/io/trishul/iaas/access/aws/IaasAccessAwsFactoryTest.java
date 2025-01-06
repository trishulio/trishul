// TODO: re-enable them back
// package io.trishul.iaas.access.aws;

// import static org.junit.jupiter.api.Assertions.assertEquals;

// import java.net.URI;
// import java.net.URISyntaxException;

// import org.apache.commons.lang3.reflect.FieldUtils;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;

// import com.amazonaws.auth.AWSCredentialsProvider;
// import com.amazonaws.auth.BasicSessionCredentials;
// import com.amazonaws.services.cognitoidentity.AmazonCognitoIdentity;
// import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
// import com.amazonaws.services.identitymanagement.AmazonIdentityManagement;
// import com.amazonaws.services.s3.AmazonS3;
// import com.amazonaws.services.secretsmanager.AWSSecretsManager;

// public class IaasAccessAwsFactoryTest {
// private IaasAccessAwsFactory factory;

// @BeforeEach
// public void init() {
// factory = new AwsFactory();
// }

// @Test
// public void testGetIdentityProvider() throws IllegalAccessException,
// URISyntaxException {
// AWSCognitoIdentityProvider idp = factory.getIdentityProvider("REGION", "URL",
// "ACCESS_KEY_ID", "ACCESS_SECRET_KEY");

// final AWSCredentialsProvider awsCredentialsProvider =
// (AWSCredentialsProvider)
// FieldUtils.readField(idp, "awsCredentialsProvider", true);
// final URI endpoint = (URI) FieldUtils.readField(idp, "endpoint", true);
// final String region = (String) FieldUtils.readField(idp, "signingRegion",
// true);

// assertEquals(new URI("https://URL"), endpoint);
// assertEquals("REGION", region);
// assertEquals(awsCredentialsProvider.getCredentials().getAWSAccessKeyId(),
// "ACCESS_KEY_ID");
// assertEquals(awsCredentialsProvider.getCredentials().getAWSSecretKey(),
// "ACCESS_SECRET_KEY");
// }

// @Test
// public void testSecretsMgrClient() throws URISyntaxException,
// IllegalAccessException {
// AWSSecretsManager secretsMgr = factory.secretsMgrClient("REGION", "URL",
// "ACCESS_KEY_ID",
// "ACCESS_SECRET_KEY");

// final AWSCredentialsProvider awsCredentialsProvider =
// (AWSCredentialsProvider)
// FieldUtils.readField(secretsMgr, "awsCredentialsProvider", true);
// final URI endpoint = (URI) FieldUtils.readField(secretsMgr, "endpoint",
// true);
// final String region = (String) FieldUtils.readField(secretsMgr,
// "signingRegion", true);

// assertEquals(new URI("https://URL"), endpoint);
// assertEquals("REGION", region);
// assertEquals(awsCredentialsProvider.getCredentials().getAWSAccessKeyId(),
// "ACCESS_KEY_ID");
// assertEquals(awsCredentialsProvider.getCredentials().getAWSSecretKey(),
// "ACCESS_SECRET_KEY");
// }

// @Test
// public void testGetAwsCognitoIdentityClient() throws IllegalAccessException,
// URISyntaxException {
// AmazonCognitoIdentity idp = factory.getAwsCognitoIdentityClient("REGION",
// "ACCESS_KEY_ID", "ACCESS_SECRET_KEY");

// final AWSCredentialsProvider awsCredentialsProvider =
// (AWSCredentialsProvider)
// FieldUtils.readField(idp, "awsCredentialsProvider", true);
// final URI endpoint = (URI) FieldUtils.readField(idp, "endpoint", true);
// final String region = (String) FieldUtils.readField(idp, "signingRegion",
// true);

// assertEquals(new URI("https://cognito-identity.REGION.amazonaws.com"),
// endpoint);
// assertEquals("REGION", region);
// assertEquals(awsCredentialsProvider.getCredentials().getAWSAccessKeyId(),
// "ACCESS_KEY_ID");
// assertEquals(awsCredentialsProvider.getCredentials().getAWSSecretKey(),
// "ACCESS_SECRET_KEY");
// }

// @Test
// public void testS3Client() throws IllegalAccessException, URISyntaxException
// {
// AmazonS3 s3 = factory.s3Client("REGION", "ACCESS_KEY_ID",
// "ACCESS_SECRET_KEY",
// "SESSION_TOKEN");

// final AWSCredentialsProvider awsCredentialsProvider =
// (AWSCredentialsProvider)
// FieldUtils.readField(s3, "awsCredentialsProvider", true);
// final URI endpoint = (URI) FieldUtils.readField(s3, "endpoint", true);
// final String region = (String) FieldUtils.readField(s3, "signingRegion",
// true);

// assertEquals(new URI("https://s3.REGION.amazonaws.com"), endpoint);
// assertEquals("REGION", region);
// assertEquals(awsCredentialsProvider.getCredentials().getAWSAccessKeyId(),
// "ACCESS_KEY_ID");
// assertEquals(awsCredentialsProvider.getCredentials().getAWSSecretKey(),
// "ACCESS_SECRET_KEY");
// assertEquals(((BasicSessionCredentials)
// awsCredentialsProvider.getCredentials()).getSessionToken(), "SESSION_TOKEN");
// }

// @Test
// public void testIamClient() throws IllegalAccessException, URISyntaxException
// {
// AmazonIdentityManagement iamClient = factory.iamClient("ACCESS_KEY_ID",
// "ACCESS_SECRET_KEY");

// final AWSCredentialsProvider awsCredentialsProvider =
// (AWSCredentialsProvider)
// FieldUtils.readField(iamClient, "awsCredentialsProvider", true);
// final URI endpoint = (URI) FieldUtils.readField(iamClient, "endpoint", true);
// final String region = (String) FieldUtils.readField(iamClient,
// "signingRegion", true);

// assertEquals(new URI("https://iam.amazonaws.com"), endpoint);
// assertEquals("us-east-1", region);
// assertEquals(awsCredentialsProvider.getCredentials().getAWSAccessKeyId(),
// "ACCESS_KEY_ID");
// assertEquals(awsCredentialsProvider.getCredentials().getAWSSecretKey(),
// "ACCESS_SECRET_KEY");
// }
// }
