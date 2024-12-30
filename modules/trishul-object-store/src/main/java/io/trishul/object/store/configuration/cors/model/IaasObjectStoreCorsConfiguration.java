package io.trishul.object.store.configuration.cors.model;

import com.amazonaws.services.s3.model.BucketCrossOriginConfiguration;
import io.trishul.base.types.base.pojo.CrudEntity;
import io.trishul.model.base.entity.BaseEntity;

public class IaasObjectStoreCorsConfiguration extends BaseEntity
    implements CrudEntity<String, IaasObjectStoreCorsConfiguration> {

  private String bucketName;

  private BucketCrossOriginConfiguration bucketCrossOriginConfiguration;

  public IaasObjectStoreCorsConfiguration() {
    super();
  }

  public IaasObjectStoreCorsConfiguration(String bucketName,
      BucketCrossOriginConfiguration bucketCrossOriginConfiguration) {
    this();
    this.bucketName = bucketName;
    this.bucketCrossOriginConfiguration = bucketCrossOriginConfiguration;
  }

  @Override
  public final IaasObjectStoreCorsConfiguration setId(String id) {
    setBucketName(id);
    return this;
  }

  @Override
  public String getId() {
    return getBucketName();
  }

  public String getBucketName() {
    return bucketName;
  }

  public final IaasObjectStoreCorsConfiguration setBucketName(String bucketName) {
    this.bucketName = bucketName;
    return this;
  }

  public BucketCrossOriginConfiguration getBucketCrossOriginConfiguration() {
    return bucketCrossOriginConfiguration;
  }

  public final IaasObjectStoreCorsConfiguration setBucketCrossOriginConfiguration(
      BucketCrossOriginConfiguration bucketCrossOriginConfiguration) {
    this.bucketCrossOriginConfiguration = bucketCrossOriginConfiguration;
    return this;
  }

  @Override
  public Integer getVersion() {
    // Not implemented due to lack of use-case
    return null;
  }
}
