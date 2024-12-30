package io.trishul.tenant.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.net.URL;
import java.util.UUID;

@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class AdminTenant implements TenantData {
  private final UUID id;
  private final String name;
  private final URL url;
  private final Boolean isReady;

  public AdminTenant(UUID id, String name, URL url) {
    super();
    this.id = id;
    this.name = name;
    this.url = url;
    this.isReady = true;
  }

  @Override
  public UUID getId() {
    return id;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public URL getUrl() {
    return url;
  }

  @Override
  public Boolean getIsReady() {
    return isReady;
  }
}
