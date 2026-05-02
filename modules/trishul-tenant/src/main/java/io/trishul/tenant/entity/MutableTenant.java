package io.trishul.tenant.entity;

import java.net.URI;

public interface MutableTenant<T extends MutableTenant<T>> {
  T setName(String name);

  T setUrl(URI url);

  T setIsReady(Boolean isReady);
}
