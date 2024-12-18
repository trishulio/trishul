package io.trishul.object.store.configuration.cors.model;

import com.amazonaws.services.s3.model.BucketCrossOriginConfiguration;
import io.trishul.base.types.base.pojo.CrudEntity;
import io.trishul.model.base.entity.BaseEntity;

public class IaasObjectStoreCorsConfiguration extends BaseEntity implements CrudEntity<String> {

    private String bucketName;

    private BucketCrossOriginConfiguration bucketCrossOriginConfiguration;

    public IaasObjectStoreCorsConfiguration() {
        super();
    }

    public IaasObjectStoreCorsConfiguration(
            String bucketName, BucketCrossOriginConfiguration bucketCrossOriginConfiguration) {
        this();
        this.bucketName = bucketName;
        this.bucketCrossOriginConfiguration = bucketCrossOriginConfiguration;
    }

    @Override
    public void setId(String id) {
        setBucketName(id);
    }

    @Override
    public String getId() {
        return getBucketName();
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public BucketCrossOriginConfiguration getBucketCrossOriginConfiguration() {
        return bucketCrossOriginConfiguration;
    }

    public void setBucketCrossOriginConfiguration(
            BucketCrossOriginConfiguration bucketCrossOriginConfiguration) {
        this.bucketCrossOriginConfiguration = bucketCrossOriginConfiguration;
    }

    @Override
    public Integer getVersion() {
        // Not implemented due to lack of use-case
        return null;
    }
}
