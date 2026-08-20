package sh.trishul.tenant.entity;

import java.net.URI;
import java.util.UUID;
import sh.trishul.base.types.base.pojo.Identified;

public interface TenantData extends Identified<UUID> {
  String ATTR_NAME = "name";
  String ATTR_URL = "url";
  String ATTR_IS_READY = "isReady";

  String getName();

  URI getUrl();

  Boolean getIsReady();
}
