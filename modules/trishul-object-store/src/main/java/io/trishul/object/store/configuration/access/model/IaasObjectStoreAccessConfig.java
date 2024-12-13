package io.trishul.object.store.configuration.access.model;

import com.amazonaws.services.s3.model.PublicAccessBlockConfiguration;

import io.trishul.model.base.entity.BaseEntity;
import io.trishul.base.types.base.pojo.CrudEntity;

public class IaasObjectStoreAccessConfig extends BaseEntity implements CrudEntity<String> {

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
        this.publicAccessBlockConfig = publicAccessBlockConfig;
    }

    @Override
    public void setId(String id) {
        setObjectStoreName(id);
    }

    @Override
    public String getId() {
        return getObjectStoreName();
    }

    public String getObjectStoreName() {
        return objectStoreName;
    }

    public void setObjectStoreName(String objectStoreName) {
        this.objectStoreName = objectStoreName;
    }

    public PublicAccessBlockConfiguration getPublicAccessBlockConfig() {
        return publicAccessBlockConfig;
    }

    public void setPublicAccessBlockConfig(PublicAccessBlockConfiguration publicAccessBlockConfig) {
        this.publicAccessBlockConfig = publicAccessBlockConfig;
    }

    @Override
    public Integer getVersion() {
        // Not implemented due to lack of use-case
        return null;
    }

}
