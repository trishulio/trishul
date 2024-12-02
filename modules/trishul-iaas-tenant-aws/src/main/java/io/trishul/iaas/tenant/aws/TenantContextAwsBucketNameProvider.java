package io.trishul.iaas.tenant.aws;

import java.util.UUID;

import io.trishul.auth.session.context.holder.ContextHolder;
import io.trishul.iaas.tenant.TenantContextIaasObjectStoreNameProvider;
import io.trishul.tenant.auth.context.TenantPrincipalContext;
import io.trishul.tenant.model.TenantIdProvider;

public class TenantContextAwsBucketNameProvider implements TenantContextIaasObjectStoreNameProvider {
    private final String defaultAppBucketName;
    private final AwsDocumentTemplates templates;
    private final ContextHolder contextHolder;

    public TenantContextAwsBucketNameProvider(AwsDocumentTemplates templates, ContextHolder contextHolder, String defaultAppBucketName) {
        this.templates = templates;
        this.contextHolder = contextHolder;
        this.defaultAppBucketName = defaultAppBucketName;
    }

    @Override
    public String getObjectStoreName() {
        String objectStoreName = this.defaultAppBucketName;

        // TODO: Create a config with global tenant principal context.
        TenantIdProvider tenantIdProvider = new TenantPrincipalContext(this.contextHolder.getPrincipalContext());

        UUID tenantId = tenantIdProvider.getTenantId();

        String bucketName = null;
        if (tenantId != null) {
            bucketName = this.templates.getTenantVfsBucketName(tenantId.toString());
        }

        if (bucketName != null) {
            objectStoreName = bucketName;
        }

        return objectStoreName;
    }
}
