package io.trishul.object.store.configuration.access.model;

import com.amazonaws.services.s3.model.PublicAccessBlockConfiguration;
import io.trishul.base.types.base.pojo.CrudEntity;
import io.trishul.model.base.entity.BaseEntity;

public class IaasObjectStoreAccessConfig extends BaseEntity
    implements CrudEntity<String, IaasObjectStoreAccessConfig> {

  private String objectStoreName;

  private PublicAccessBlockConfiguration publicAccessBlockConfig;

  public IaasObjectStoreAccessConfig() {
    super();
  }

  public IaasObjectStoreAccessConfig(String id) {
    this();
    setId(id);
  }

  public IaasObjectStoreAccessConfig(String objectStoreName,
      PublicAccessBlockConfiguration publicAccessBlockConfig) {
    this();
    this.objectStoreName = objectStoreName;
    this.publicAccessBlockConfig
        = publicAccessBlockConfig == null ? null : publicAccessBlockConfig.clone();
  }

  @Override
  public IaasObjectStoreAccessConfig setId(String id) {
    setObjectStoreName(id);
    return this;
  }

  @Override
  public String getId() {
    return getObjectStoreName();
  }

  public String getObjectStoreName() {
    return objectStoreName;
  }

  public IaasObjectStoreAccessConfig setObjectStoreName(String objectStoreName) {
    this.objectStoreName = objectStoreName;
    return this;
  }

  public PublicAccessBlockConfiguration getPublicAccessBlockConfig() {
    return publicAccessBlockConfig == null ? null : publicAccessBlockConfig.clone();
  }

  public IaasObjectStoreAccessConfig setPublicAccessBlockConfig(
      PublicAccessBlockConfiguration publicAccessBlockConfig) {
    this.publicAccessBlockConfig
        = publicAccessBlockConfig == null ? null : publicAccessBlockConfig.clone();
    return this;
  }

  @Override
  public Integer getVersion() {
    // Not implemented due to lack of use-case
    return null;
  }
}
