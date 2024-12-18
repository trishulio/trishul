package io.trishul.iaas.tenant.aws;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import io.trishul.iaas.tenant.TenantContextIaasObjectStoreNameProvider;
import io.trishul.tenant.entity.TenantIdProvider;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TenantContextAwsBucketNameProviderTest {
    private TenantContextIaasObjectStoreNameProvider provider;

    private AwsDocumentTemplates mTemplates;
    private TenantIdProvider mTenantIdProvider;

    @BeforeEach
    public void init() {
        mTemplates = mock(AwsDocumentTemplates.class);
        mTenantIdProvider = mock(TenantIdProvider.class);
        provider =
                new TenantContextAwsBucketNameProvider(
                        mTemplates, mTenantIdProvider, "DEFAULT_BUCKET");
    }

    @Test
    public void testGetObjectStoreName_ReturnsDefaultBucketName_WhenPrincipalContextIsNull() {
        assertEquals("DEFAULT_BUCKET", provider.getObjectStoreName());
    }

    @Test
    public void
            testGetObjectStoreName_ReturnsDefaultBucketName_WhenPrincipalContextHaveNullTenantId() {

        assertEquals("DEFAULT_BUCKET", provider.getObjectStoreName());
    }

    @Test
    public void testGetObjectStoreName_ReturnsDefaultBucketName_WhenVfsBucketNameReturnsNull() {
        doReturn(UUID.fromString("00000000-0000-0000-0000-000000000001"))
                .when(mTenantIdProvider)
                .getTenantId();

        assertEquals("DEFAULT_BUCKET", provider.getObjectStoreName());
    }

    @Test
    public void
            testGetObjectStoreName_ReturnsBucketNameFromTemplate_WhenVfsBucketNameReturnsValue() {
        doReturn(UUID.fromString("00000000-0000-0000-0000-000000000001"))
                .when(mTenantIdProvider)
                .getTenantId();
        doReturn("BUCKET-00000000-0000-0000-0000-000000000001")
                .when(mTemplates)
                .getTenantVfsBucketName("00000000-0000-0000-0000-000000000001");

        assertEquals("BUCKET-00000000-0000-0000-0000-000000000001", provider.getObjectStoreName());
    }
}
