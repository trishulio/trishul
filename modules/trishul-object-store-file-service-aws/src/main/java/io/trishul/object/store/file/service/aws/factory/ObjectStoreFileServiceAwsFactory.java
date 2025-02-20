package io.trishul.object.store.file.service.aws.factory;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ObjectStoreFileServiceAwsFactory {
  @SuppressWarnings("unused")
  private static final Logger log = LoggerFactory.getLogger(ObjectStoreFileServiceAwsFactory.class);

  public AmazonS3 s3Client(String region, String s3AccessKeyId, String s3Secret) {
    BasicAWSCredentials basicAwsCredentials = new BasicAWSCredentials(s3AccessKeyId, s3Secret);
    AWSCredentialsProvider credsProvider = new AWSStaticCredentialsProvider(basicAwsCredentials);

    return AmazonS3ClientBuilder.standard().withRegion(region).withCredentials(credsProvider)
        .build();
  }

  public AmazonS3 s3Client(String region, String s3AccessKeyId, String s3Secret,
      String sessionToken) {
    AWSCredentials awsCreds = new BasicSessionCredentials(s3AccessKeyId, s3Secret, sessionToken);
    AWSCredentialsProvider credsProvider = new AWSStaticCredentialsProvider(awsCreds);

    return AmazonS3ClientBuilder.standard().withRegion(region).withCredentials(credsProvider)
        .build();
  }
}
