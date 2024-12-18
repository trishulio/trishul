package io.trishul.tenant.entity;

import io.trishul.base.types.base.pojo.IdentityAccessor;
import java.net.URL;
import java.util.UUID;

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
