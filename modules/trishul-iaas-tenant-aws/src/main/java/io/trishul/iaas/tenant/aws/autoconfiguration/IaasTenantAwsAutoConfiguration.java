package io.trishul.iaas.tenant.aws.autoconfiguration;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.trishul.iaas.tenant.aws.AwsDocumentTemplates;
import io.trishul.iaas.tenant.aws.AwsTenantIaasResourceBuilder;
import io.trishul.iaas.tenant.aws.TenantContextAwsBucketNameProvider;
import io.trishul.iaas.tenant.object.store.builder.TenantObjectStoreResourceBuilder;
import io.trishul.iaas.tenant.resource.TenantIaasResourceBuilder;
import io.trishul.object.store.file.service.service.IaasObjectStoreNameProvider;
import io.trishul.tenant.auth.model.ContextHolderTenantIdProvider;

@Configuration
public class IaasTenantAwsAutoConfiguration {
    
    @Bean
    @ConditionalOnMissingBean(AwsDocumentTemplates.class)
    public AwsDocumentTemplates awsDocumentTemplates(@Value("${aws.cognito.identity.pool.id}") String cognitoIdPoolId) {
        return new AwsDocumentTemplates(cognitoIdPoolId);
    }

    @Bean
    @ConditionalOnMissingBean(IaasObjectStoreNameProvider.class)
    public IaasObjectStoreNameProvider iaasObjectStoreNameProvider(AwsDocumentTemplates awsDocumentTemplates, ContextHolderTenantIdProvider contextHolderTenantIdProvider, @Value("app.object-store.bucket.name") String appBucketName) {
        return new TenantContextAwsBucketNameProvider(awsDocumentTemplates, contextHolderTenantIdProvider, appBucketName);
    }

    @Bean
    @ConditionalOnMissingBean({TenantIaasResourceBuilder.class, TenantObjectStoreResourceBuilder.class})
    public TenantIaasResourceBuilder tenantIaasResourceBuilder(AwsDocumentTemplates awsDocumentTemplates, @Value("#{'${aws.s3.config.cors.allowed.headers}'.split(';')}") List<String> allowedHeaders, @Value("#{'${aws.s3.config.cors.allowed.methods}'.split(';')}") List<String> allowedMethods, @Value("#{'${aws.s3.config.cors.allowed.origins}'.split(';')}") List<String> allowedOrigins, @Value("${aws.s3.config.access.public.acls.block}") boolean blockPublicAcls, @Value("${aws.s3.config.access.public.acls.ignore}") boolean ignorePublicAcls, @Value("${aws.s3.config.access.public.policy.block}") boolean blockPublicPolicy, @Value("${aws.s3.config.access.public.buckets.restrict}") boolean restrictPublicBuckets) {
        return new AwsTenantIaasResourceBuilder(awsDocumentTemplates, allowedHeaders, allowedMethods, allowedOrigins, blockPublicAcls, ignorePublicAcls, blockPublicPolicy, restrictPublicBuckets);
    }
}
