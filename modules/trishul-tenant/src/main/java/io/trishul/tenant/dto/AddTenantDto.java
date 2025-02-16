package io.trishul.tenant.dto;

import java.net.URL;
import io.trishul.model.base.dto.BaseDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class AddTenantDto extends BaseDto {
  @NotBlank
  private String name;

  @NotNull
  private URL url;

  public AddTenantDto() {
    super();
  }

  public AddTenantDto(String name, URL url) {
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

  public URL getUrl() {
    return url;
  }

  public AddTenantDto setUrl(URL url) {
    this.url = url;
    return this;
  }
}
