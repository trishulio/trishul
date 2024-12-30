package io.trishul.iaas.tenant.aws;

import io.trishul.iaas.tenant.TenantContextIaasObjectStoreNameProvider;
import io.trishul.tenant.entity.TenantIdProvider;
import java.util.UUID;

public class TenantContextAwsBucketNameProvider
    implements TenantContextIaasObjectStoreNameProvider {
  private final String defaultAppBucketName;
  private final AwsDocumentTemplates templates;
  private final TenantIdProvider tenantIdProvider;

  public TenantContextAwsBucketNameProvider(AwsDocumentTemplates templates,
      TenantIdProvider tenantIdProvider, String defaultAppBucketName) {
    this.templates = templates;
    this.tenantIdProvider = tenantIdProvider;
    this.defaultAppBucketName = defaultAppBucketName;
  }

  @Override
  public String getObjectStoreName() {
    String objectStoreName = this.defaultAppBucketName;

    // TODO: Create a config with global tenant principal context.
    UUID tenantId = this.tenantIdProvider.getTenantId();

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
