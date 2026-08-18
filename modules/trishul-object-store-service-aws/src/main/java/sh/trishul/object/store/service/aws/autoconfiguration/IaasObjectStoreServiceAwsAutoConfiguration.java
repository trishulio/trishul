package sh.trishul.object.store.service.aws.autoconfiguration;

import com.amazonaws.services.s3.AmazonS3;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sh.trishul.iaas.client.IaasClient;
import sh.trishul.object.store.aws.model.mapper.AwsIaasObjectStoreMapper;
import sh.trishul.object.store.configuration.access.model.IaasObjectStoreAccessConfig;
import sh.trishul.object.store.configuration.cors.model.IaasObjectStoreCorsConfiguration;
import sh.trishul.object.store.model.BaseIaasObjectStore;
import sh.trishul.object.store.model.IaasObjectStore;
import sh.trishul.object.store.model.UpdateIaasObjectStore;
import sh.trishul.object.store.service.aws.cors.config.AwsCorsConfigClient;
import sh.trishul.object.store.service.aws.cors.config.AwsObjectStoreClient;
import sh.trishul.object.store.service.aws.cors.config.AwsPublicAccessBlockClient;

@Configuration
public class IaasObjectStoreServiceAwsAutoConfiguration {
  @Bean
  @ConditionalOnMissingBean(AwsObjectStoreClient.class)
  public IaasClient<String, IaasObjectStore, BaseIaasObjectStore<?>, UpdateIaasObjectStore<?>> iaasObjectStoreClient(
      AmazonS3 awsClient) {
    return new AwsObjectStoreClient(awsClient, AwsIaasObjectStoreMapper.INSTANCE);
  }

  @Bean
  @ConditionalOnMissingBean(AwsPublicAccessBlockClient.class)
  public IaasClient<String, IaasObjectStoreAccessConfig, IaasObjectStoreAccessConfig, IaasObjectStoreAccessConfig> iaasObjectStoreAccessConfigClient(
      AmazonS3 awsS3Client) {
    return new AwsPublicAccessBlockClient(awsS3Client);
  }

  @Bean
  @ConditionalOnMissingBean(AwsCorsConfigClient.class)
  public IaasClient<String, IaasObjectStoreCorsConfiguration, IaasObjectStoreCorsConfiguration, IaasObjectStoreCorsConfiguration> iaasObjectStoreCorsConfigClient(
      AmazonS3 awsS3Client) {
    return new AwsCorsConfigClient(awsS3Client);
  }
}
