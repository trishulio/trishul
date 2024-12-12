package io.company.brewcraft.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.model.AwsDocumentTemplates;
import io.company.brewcraft.security.session.ContextHolder;
import io.company.brewcraft.security.session.PrincipalContext;

public class TenantContextAwsBucketNameProviderTest {
    private TenantContextIaasObjectStoreNameProvider provider;

    private AwsDocumentTemplates mTemplates;
    private ContextHolder mCtxHolder;

    @BeforeEach
    public void init() {
        mCtxHolder = mock(ContextHolder.class);
        mTemplates = mock(AwsDocumentTemplates.class);
        provider = new TenantContextAwsBucketNameProvider(mTemplates, mCtxHolder, "DEFAULT_BUCKET");
    }

    @Test
    public void testGetObjectStoreName_ReturnsDefaultBucketName_WhenPrincipalContextIsNull() {
        assertEquals("DEFAULT_BUCKET", provider.getObjectStoreName());
    }

    @Test
    public void testGetObjectStoreName_ReturnsDefaultBucketName_WhenPrincipalContextHaveNullTenantId() {
        doReturn(mock(PrincipalContext.class)).when(mCtxHolder).getPrincipalContext();

        assertEquals("DEFAULT_BUCKET", provider.getObjectStoreName());
    }

    @Test
    public void testGetObjectStoreName_ReturnsDefaultBucketName_WhenVfsBucketNameReturnsNull() {
        PrincipalContext ctx = mock(PrincipalContext.class);
        doReturn(UUID.fromString("00000000-0000-0000-0000-000000000001")).when(ctx).getTenantId();
        doReturn(ctx).when(mCtxHolder).getPrincipalContext();

        assertEquals("DEFAULT_BUCKET", provider.getObjectStoreName());
    }

    @Test
    public void testGetObjectStoreName_ReturnsBucketNameFromTemplate_WhenVfsBucketNameReturnsValue() {
        PrincipalContext ctx = mock(PrincipalContext.class);
        doReturn(UUID.fromString("00000000-0000-0000-0000-000000000001")).when(ctx).getTenantId();
        doReturn("BUCKET-00000000-0000-0000-0000-000000000001").when(mTemplates).getTenantVfsBucketName("00000000-0000-0000-0000-000000000001");

        doReturn(ctx).when(mCtxHolder).getPrincipalContext();

        assertEquals("BUCKET-00000000-0000-0000-0000-000000000001", provider.getObjectStoreName());
    }
}
