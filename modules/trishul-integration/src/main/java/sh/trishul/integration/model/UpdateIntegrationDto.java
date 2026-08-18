package sh.trishul.integration.model;

import jakarta.validation.constraints.NotNull;
import sh.trishul.model.base.dto.BaseDto;

public class UpdateIntegrationDto extends BaseDto {
  private Long id;

  private String name;

  private IntegrationType type;

  private String provider;

  private IntegrationStatus status;

  private String configuration;

  @NotNull
  private Integer version;

  public UpdateIntegrationDto() {
    super();
  }

  public UpdateIntegrationDto(Long id) {
    this();
    setId(id);
  }

  public UpdateIntegrationDto(Long id, String name, IntegrationType type, String provider,
      IntegrationStatus status, String configuration, @NotNull Integer version) {
    this(id);
    setName(name);
    setType(type);
    setProvider(provider);
    setStatus(status);
    setConfiguration(configuration);
    setVersion(version);
  }

  public Long getId() {
    return id;
  }

  public UpdateIntegrationDto setId(Long id) {
    this.id = id;
    return this;
  }

  public String getName() {
    return name;
  }

  public UpdateIntegrationDto setName(String name) {
    this.name = name;
    return this;
  }

  public IntegrationType getType() {
    return type;
  }

  public UpdateIntegrationDto setType(IntegrationType type) {
    this.type = type;
    return this;
  }

  public String getProvider() {
    return provider;
  }

  public UpdateIntegrationDto setProvider(String provider) {
    this.provider = provider;
    return this;
  }

  public IntegrationStatus getStatus() {
    return status;
  }

  public UpdateIntegrationDto setStatus(IntegrationStatus status) {
    this.status = status;
    return this;
  }

  public String getConfiguration() {
    return configuration;
  }

  public UpdateIntegrationDto setConfiguration(String configuration) {
    this.configuration = configuration;
    return this;
  }

  public Integer getVersion() {
    return version;
  }

  public UpdateIntegrationDto setVersion(Integer version) {
    this.version = version;
    return this;
  }
}
