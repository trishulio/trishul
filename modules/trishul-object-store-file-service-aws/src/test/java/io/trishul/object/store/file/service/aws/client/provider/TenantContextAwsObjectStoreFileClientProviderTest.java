package io.company.brewcraft.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;

import io.company.brewcraft.model.BaseIaasObjectStoreFile;
import io.company.brewcraft.model.IaasAuthorization;
import io.company.brewcraft.model.IaasObjectStoreFile;
import io.company.brewcraft.model.UpdateIaasObjectStoreFile;
import io.company.brewcraft.security.idp.AwsFactory;

public class TenantContextAwsObjectStoreFileClientProviderTest {
    private TenantContextAwsObjectStoreFileClientProvider  provider;

    private TenantContextIaasAuthorizationFetch mAuthFetcher;
    private TenantContextIaasObjectStoreNameProvider mBucketNameProvider;
    private AwsFactory mFactory;

    @BeforeEach
    public void init() {
        mAuthFetcher = mock(TenantContextIaasAuthorizationFetch.class);
        mBucketNameProvider = mock(TenantContextIaasObjectStoreNameProvider.class);
        mFactory = mock(AwsFactory.class);

        provider = new TenantContextAwsObjectStoreFileClientProvider("REGION", mBucketNameProvider, mAuthFetcher, mFactory, 1000L);
    }

    @Test
    public void testGetIaasRepository_ReturnsClientFromTenantContext() {
        ArgumentCaptor<GeneratePresignedUrlRequest> captor = ArgumentCaptor.forClass(GeneratePresignedUrlRequest.class);

        AmazonS3 mS3 = mock(AmazonS3.class);
        doReturn(null).when(mS3).generatePresignedUrl(captor.capture());

        doReturn(mS3).when(mFactory).s3Client("REGION", "ACCESS_KEY_ID", "ACCESS_SECRET_KEY", "SESSION_TOKEN");

        doReturn(new IaasAuthorization("ACCESS_KEY_ID", "ACCESS_SECRET_KEY", "SESSION_TOKEN", LocalDateTime.of(2000, 1, 1, 0, 0))).when(mAuthFetcher).fetch();

        doReturn("OBJECT_STORE").when(mBucketNameProvider).getObjectStoreName();

        IaasRepository<URI, IaasObjectStoreFile, BaseIaasObjectStoreFile, UpdateIaasObjectStoreFile> client = provider.getIaasRepository();

        client.get(Set.of(URI.create("file.txt")));

        assertEquals("OBJECT_STORE", captor.getValue().getBucketName());
        assertEquals("file.txt", captor.getValue().getKey());
    }
}
