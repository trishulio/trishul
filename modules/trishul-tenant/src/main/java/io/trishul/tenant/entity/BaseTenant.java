package io.trishul.tenant.entity;

import java.net.URL;
import java.util.UUID;

import io.trishul.model.base.pojo.IdentityAccessor;

public interface BaseTenant extends IdentityAccessor<UUID> {
    final String ATTR_NAME = "name";
    final String ATTR_URL = "url";
    final String ATTR_IS_READY = "isReady";

    String getName();

    void setName(String name);

    URL getUrl();

    void setUrl(URL url);

    Boolean getIsReady();

    void setIsReady(Boolean isReady);
}
