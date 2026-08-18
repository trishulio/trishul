package sh.trishul.integration.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import sh.trishul.model.base.dto.BaseDto;

public class AddIntegrationDto extends BaseDto {
  @NotBlank
  private String name;

  @NotNull
  private IntegrationType type;

  @NotBlank
  private String provider;

  private IntegrationStatus status;

  private String configuration;

  public AddIntegrationDto() {}

  public AddIntegrationDto(@NotBlank String name, @NotNull IntegrationType type,
      @NotBlank String provider, IntegrationStatus status, String configuration) {
    setName(name);
    setType(type);
    setProvider(provider);
    setStatus(status);
    setConfiguration(configuration);
  }

  public String getName() {
    return name;
  }

  public AddIntegrationDto setName(String name) {
    this.name = name;
    return this;
  }

  public IntegrationType getType() {
    return type;
  }

  public AddIntegrationDto setType(IntegrationType type) {
    this.type = type;
    return this;
  }

  public String getProvider() {
    return provider;
  }

  public AddIntegrationDto setProvider(String provider) {
    this.provider = provider;
    return this;
  }

  public IntegrationStatus getStatus() {
    return status;
  }

  public AddIntegrationDto setStatus(IntegrationStatus status) {
    this.status = status;
    return this;
  }

  public String getConfiguration() {
    return configuration;
  }

  public AddIntegrationDto setConfiguration(String configuration) {
    this.configuration = configuration;
    return this;
  }
}
