package io.trishul.tenant.entity;

import io.trishul.base.types.base.pojo.Identified;
import java.net.URI;
import java.util.UUID;

public interface TenantData extends Identified<UUID> {
  final String ATTR_NAME = "name";
  final String ATTR_URL = "url";
  final String ATTR_IS_READY = "isReady";

  String getName();

  URI getUrl();

  Boolean getIsReady();
}
