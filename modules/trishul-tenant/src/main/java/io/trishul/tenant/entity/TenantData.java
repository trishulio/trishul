package io.trishul.tenant.entity;

import java.net.URL;
import java.util.UUID;
import io.trishul.base.types.base.pojo.Identified;

public interface TenantData extends Identified<UUID> {
  final String ATTR_NAME = "name";
  final String ATTR_URL = "url";
  final String ATTR_IS_READY = "isReady";

  String getName();

  URL getUrl();

  Boolean getIsReady();
}
