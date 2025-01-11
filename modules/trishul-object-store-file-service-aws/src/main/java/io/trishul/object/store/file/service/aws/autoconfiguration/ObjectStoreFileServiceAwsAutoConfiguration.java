package io.trishul.object.store.file.service.aws.autoconfiguration;

import java.net.URI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.trishul.iaas.auth.session.context.ContextHolderAuthorizationFetcher;
import io.trishul.iaas.repository.provider.IaasRepositoryProvider;
import io.trishul.object.store.file.model.BaseIaasObjectStoreFile;
import io.trishul.object.store.file.model.IaasObjectStoreFile;
import io.trishul.object.store.file.model.UpdateIaasObjectStoreFile;
import io.trishul.object.store.file.service.aws.client.provider.TenantContextAwsObjectStoreFileClientProvider;
import io.trishul.object.store.file.service.aws.factory.ObjectStoreFileServiceAwsFactory;
import io.trishul.object.store.file.service.service.IaasObjectStoreNameProvider;

@Configuration
public class ObjectStoreFileServiceAwsAutoConfiguration {

  @Bean
  @ConditionalOnMissingBean(ObjectStoreFileServiceAwsFactory.class)
  public ObjectStoreFileServiceAwsFactory objectStoreFileServiceAwsFactory() {
    return new ObjectStoreFileServiceAwsFactory();
  }

  @Bean
  @ConditionalOnMissingBean(IaasRepositoryProvider.class)
  public IaasRepositoryProvider<URI, IaasObjectStoreFile, BaseIaasObjectStoreFile<?>, UpdateIaasObjectStoreFile<?>> iaasObjectStoreFileClientProvider(
      @Value("${aws.s3.region}") String region, IaasObjectStoreNameProvider bucketNameProvider,
      ContextHolderAuthorizationFetcher authFetcher, ObjectStoreFileServiceAwsFactory awsFactory,
      @Value("${app.object-store.file.get.url.expiry}") Long getPresignUrlDuration) {
    return new TenantContextAwsObjectStoreFileClientProvider(region, bucketNameProvider,
        authFetcher, awsFactory, getPresignUrlDuration);
  }
}
