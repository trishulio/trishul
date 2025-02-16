package io.trishul.tenant.dto;

import java.net.URL;
import java.util.UUID;
import io.trishul.model.base.dto.BaseDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class UpdateTenantDto extends BaseDto {
  private UUID id;

  @NotBlank
  private String name;

  @NotNull
  private URL url;

  public UpdateTenantDto() {
    super();
  }

  public UpdateTenantDto(UUID id) {
    this();
    setId(id);
  }

  public UpdateTenantDto(UUID id, String name, URL url) {
    this(id);
    setName(name);
    setUrl(url);
  }

  public UUID getId() {
    return id;
  }

  public UpdateTenantDto setId(UUID id) {
    this.id = id;
    return this;
  }

  public String getName() {
    return name;
  }

  public UpdateTenantDto setName(String name) {
    this.name = name;
    return this;
  }

  public URL getUrl() {
    return url;
  }

  public UpdateTenantDto setUrl(URL url) {
    this.url = url;
    return this;
  }
}
