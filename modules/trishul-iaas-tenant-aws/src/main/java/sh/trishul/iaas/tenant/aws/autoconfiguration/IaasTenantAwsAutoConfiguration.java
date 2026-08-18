package sh.trishul.iaas.tenant.aws.autoconfiguration;

import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sh.trishul.config.AppConfig;
import sh.trishul.iaas.tenant.aws.AwsDocumentTemplates;
import sh.trishul.iaas.tenant.aws.AwsTenantIaasResourceBuilder;
import sh.trishul.iaas.tenant.aws.TenantContextAwsBucketNameProvider;
import sh.trishul.iaas.tenant.object.store.builder.TenantObjectStoreResourceBuilder;
import sh.trishul.iaas.tenant.resource.TenantIaasResourceBuilder;
import sh.trishul.object.store.file.service.service.IaasObjectStoreNameProvider;
import sh.trishul.tenant.auth.model.ContextHolderTenantIdProvider;

@Configuration
public class IaasTenantAwsAutoConfiguration {

  @Bean
  @ConditionalOnMissingBean(AwsDocumentTemplates.class)
  public AwsDocumentTemplates awsDocumentTemplates(
      @Value("${aws.cognito.identity.pool.id}") String cognitoIdPoolId, AppConfig appConfig) {
    return new AwsDocumentTemplates(cognitoIdPoolId, appConfig.getName());
  }

  @Bean
  @ConditionalOnMissingBean(IaasObjectStoreNameProvider.class)
  public IaasObjectStoreNameProvider iaasObjectStoreNameProvider(
      AwsDocumentTemplates awsDocumentTemplates,
      ContextHolderTenantIdProvider contextHolderTenantIdProvider,
      @Value("app.object-store.bucket.name") String appBucketName) {
    return new TenantContextAwsBucketNameProvider(awsDocumentTemplates,
        contextHolderTenantIdProvider, appBucketName);
  }

  @Bean
  @ConditionalOnMissingBean({TenantIaasResourceBuilder.class,
      TenantObjectStoreResourceBuilder.class})
  public TenantIaasResourceBuilder tenantIaasResourceBuilder(
      AwsDocumentTemplates awsDocumentTemplates,
      @Value("#{'${aws.s3.config.cors.allowed.headers}'.split(';')}") List<String> allowedHeaders,
      @Value("#{'${aws.s3.config.cors.allowed.methods}'.split(';')}") List<String> allowedMethods,
      @Value("#{'${aws.s3.config.cors.allowed.origins}'.split(';')}") List<String> allowedOrigins,
      @Value("${aws.s3.config.access.public.acls.block}") boolean blockPublicAcls,
      @Value("${aws.s3.config.access.public.acls.ignore}") boolean ignorePublicAcls,
      @Value("${aws.s3.config.access.public.policy.block}") boolean blockPublicPolicy,
      @Value("${aws.s3.config.access.public.buckets.restrict}") boolean restrictPublicBuckets) {
    return new AwsTenantIaasResourceBuilder(awsDocumentTemplates, allowedHeaders, allowedMethods,
        allowedOrigins, blockPublicAcls, ignorePublicAcls, blockPublicPolicy,
        restrictPublicBuckets);
  }
}
