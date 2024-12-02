package io.trishul.object.store.file.service.aws.autoconfiguration;


import java.net.URI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.trishul.auth.aws.client.AwsCognitoIdentityClient;
import io.trishul.auth.aws.session.context.AwsResourceCredentialsFetcher;
import io.trishul.auth.aws.session.context.mapper.AwsIdentityCredentialsMapper;
import io.trishul.auth.session.context.IaasAuthorizationFetcher;
import io.trishul.auth.session.context.TenantContextIaasAuthorizationFetcher;
import io.trishul.auth.session.context.holder.ContextHolder;
import io.trishul.iaas.repository.provider.IaasRepositoryProvider;
import io.trishul.object.store.file.service.aws.client.provider.TenantContextAwsObjectStoreFileClientProvider;
import io.trishul.object.store.file.service.aws.factory.AwsFactory;
import io.trishul.object.store.file.service.model.entity.BaseIaasObjectStoreFile;
import io.trishul.object.store.file.service.model.entity.IaasObjectStoreFile;
import io.trishul.object.store.file.service.model.entity.UpdateIaasObjectStoreFile;
import io.trishul.object.store.file.service.service.IaasObjectStoreNameProvider;

@Configuration
public class AwsObjectStoreServiceAutoConfiguration {
    
    @Bean
    @ConditionalOnMissingBean(IaasRepositoryProvider.class)
    public IaasRepositoryProvider<URI, IaasObjectStoreFile, BaseIaasObjectStoreFile, UpdateIaasObjectStoreFile> iaasObjectStoreFileClientProvider(@Value("${aws.s3.region}") String region, IaasObjectStoreNameProvider bucketNameProvider, TenantContextIaasAuthorizationFetcher authFetcher, AwsFactory awsFactory, @Value("${app.object-store.file.get.url.expiry}") Long getPresignUrlDuration) {
        return new TenantContextAwsObjectStoreFileClientProvider(region, bucketNameProvider, authFetcher, awsFactory, getPresignUrlDuration);
    }


    @Bean
    @ConditionalOnMissingBean(TenantContextIaasAuthorizationFetcher.class)
    public TenantContextIaasAuthorizationFetcher tenantIaasAuthorizationFetcher(IaasAuthorizationFetcher authFetcher, ContextHolder contextHolder) {
        return new TenantContextIaasAuthorizationFetcher(authFetcher, contextHolder);
    }

    @Bean
    @ConditionalOnMissingBean(IaasAuthorizationFetcher.class)
    public IaasAuthorizationFetcher iaasAuthorizationFetch(AwsCognitoIdentityClient identityClient, @Value("${aws.cognito.user-pool.url}") String userPoolUrl) {
        return new AwsResourceCredentialsFetcher(identityClient, AwsIdentityCredentialsMapper.INSTANCE, userPoolUrl);
    }
}
