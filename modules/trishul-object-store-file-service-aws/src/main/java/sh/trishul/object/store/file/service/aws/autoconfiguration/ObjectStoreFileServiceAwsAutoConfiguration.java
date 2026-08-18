package sh.trishul.object.store.file.service.aws.autoconfiguration;

import java.net.URI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sh.trishul.iaas.auth.session.context.ContextHolderAuthorizationFetcher;
import sh.trishul.iaas.repository.provider.IaasRepositoryProvider;
import sh.trishul.object.store.file.model.BaseIaasObjectStoreFile;
import sh.trishul.object.store.file.model.IaasObjectStoreFile;
import sh.trishul.object.store.file.model.UpdateIaasObjectStoreFile;
import sh.trishul.object.store.file.service.aws.client.provider.TenantContextAwsObjectStoreFileClientProvider;
import sh.trishul.object.store.file.service.aws.factory.ObjectStoreFileServiceAwsFactory;
import sh.trishul.object.store.file.service.service.IaasObjectStoreNameProvider;

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
