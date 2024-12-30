package io.trishul.tenant.entity;

import java.net.URL;

public interface MutableTenant<T extends MutableTenant<T>> {
  T setName(String name);

  T setUrl(URL url);

  T setIsReady(Boolean isReady);
}
