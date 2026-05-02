package io.trishul.tenant.dto;

import java.net.URI;
import io.trishul.model.base.dto.BaseDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class AddTenantDto extends BaseDto {
  @NotBlank
  private String name;

  @NotNull
  private URI url;

  public AddTenantDto() {
    super();
  }

  public AddTenantDto(String name, URI url) {
    setName(name);
    setUrl(url);
  }

  public String getName() {
    return name;
  }

  public AddTenantDto setName(String name) {
    this.name = name;
    return this;
  }

  public URI getUrl() {
    return url;
  }

  public AddTenantDto setUrl(URI url) {
    this.url = url;
    return this;
  }
}
