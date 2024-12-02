package io.trishul.object.store.aws.model.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

public class AwsFactory {
    private static final Logger logger = LoggerFactory.getLogger(AwsFactory.class);

    public AmazonS3 s3Client(String region, String s3AccessKeyId, String s3Secret) {
        BasicAWSCredentials basicAwsCredentials = new BasicAWSCredentials(s3AccessKeyId, s3Secret);
        AWSCredentialsProvider credsProvider = new AWSStaticCredentialsProvider(basicAwsCredentials);

        return AmazonS3ClientBuilder.standard()
                                     .withRegion(region)
                                     .withCredentials(credsProvider)
                                     .build();
    }

    public AmazonS3 s3Client(String region, String s3AccessKeyId, String s3Secret, String sessionToken) {
        AWSCredentials awsCreds = new BasicSessionCredentials(s3AccessKeyId, s3Secret, sessionToken);
        AWSCredentialsProvider credsProvider = new AWSStaticCredentialsProvider(awsCreds);

        return AmazonS3ClientBuilder.standard()
                                     .withRegion(region)
                                     .withCredentials(credsProvider)
                                     .build();
    }
}
