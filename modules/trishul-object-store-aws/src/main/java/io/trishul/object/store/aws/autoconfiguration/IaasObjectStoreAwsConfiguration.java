package io.trishul.object.store.aws.autoconfiguration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.services.s3.AmazonS3;

import io.trishul.object.store.aws.model.mapper.ObjectStoreAwsFactory;

@Configuration
public class IaasObjectStoreAwsConfiguration {
    @Bean
    @ConditionalOnMissingBean(ObjectStoreAwsFactory.class)
    public ObjectStoreAwsFactory awsFactory() {
        return new ObjectStoreAwsFactory();
    }

    @Bean
    @ConditionalOnMissingBean(AmazonS3.class)
    public AmazonS3 s3Client(ObjectStoreAwsFactory awsFactory, @Value("${aws.s3.region}") String region, @Value("${aws.s3.access-key}") String s3AccessKeyId, @Value("${aws.s3.access-secret}") String s3Secret) {
        return awsFactory.s3Client(region, s3AccessKeyId, s3Secret);
    }
}
