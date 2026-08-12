package sh.trishul.integration.communication.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import sh.trishul.base.types.base.pojo.Audited;
import sh.trishul.base.types.base.pojo.CrudEntity;
import sh.trishul.communication.model.channel.ChannelType;
import sh.trishul.integration.model.Integration;
import sh.trishul.model.base.entity.BaseEntity;

@Entity(name = "integrationCommunicationConfig")
@Table(name = "integration_communication_config")
public class IntegrationCommunicationConfig extends BaseEntity
    implements CrudEntity<Long, IntegrationCommunicationConfig>,
    UpdateIntegrationCommunicationConfig<IntegrationCommunicationConfig>,
    Audited<IntegrationCommunicationConfig> {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE,
      generator = "integration_communication_config_generator")
  @SequenceGenerator(name = "integration_communication_config_generator",
      sequenceName = "integration_communication_config_sequence", allocationSize = 1)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "integration_id", referencedColumnName = "id")
  private Integration integration;

  @Enumerated(EnumType.STRING)
  @Column(name = "channel_type")
  private ChannelType channelType;

  @Column(name = "channel_address")
  private String channelAddress;

  @Column(name = "default_from")
  private String defaultFrom;

  @Column(name = "enabled")
  private Boolean enabled;

  @Version
  private Integer version;

  @CreationTimestamp
  @Column(name = "created_at", updatable = false)
  private LocalDateTime createdAt;

  @UpdateTimestamp
  @Column(name = "last_updated")
  private LocalDateTime lastUpdated;

  public IntegrationCommunicationConfig() {}

  public IntegrationCommunicationConfig(Long id) {
    this();
    setId(id);
  }

  public IntegrationCommunicationConfig(Long id, Integration integration, ChannelType channelType,
      String channelAddress, String defaultFrom, Boolean enabled, LocalDateTime createdAt,
      LocalDateTime lastUpdated, Integer version) {
    this(id);
    setIntegration(integration);
    setChannelType(channelType);
    setChannelAddress(channelAddress);
    setDefaultFrom(defaultFrom);
    setEnabled(enabled);
    setCreatedAt(createdAt);
    setLastUpdated(lastUpdated);
    setVersion(version);
  }

  @Override
  public Long getId() {
    return id;
  }

  @Override
  public IntegrationCommunicationConfig setId(Long id) {
    this.id = id;
    return this;
  }

  @Override
  public Integration getIntegration() {
    return integration;
  }

  @Override
  public IntegrationCommunicationConfig setIntegration(Integration integration) {
    this.integration = integration;
    return this;
  }

  @Override
  public ChannelType getChannelType() {
    return channelType;
  }

  @Override
  public IntegrationCommunicationConfig setChannelType(ChannelType channelType) {
    this.channelType = channelType;
    return this;
  }

  @Override
  public String getChannelAddress() {
    return channelAddress;
  }

  @Override
  public IntegrationCommunicationConfig setChannelAddress(String channelAddress) {
    this.channelAddress = channelAddress;
    return this;
  }

  @Override
  public String getDefaultFrom() {
    return defaultFrom;
  }

  @Override
  public IntegrationCommunicationConfig setDefaultFrom(String defaultFrom) {
    this.defaultFrom = defaultFrom;
    return this;
  }

  @Override
  public Boolean getEnabled() {
    return enabled;
  }

  @Override
  public IntegrationCommunicationConfig setEnabled(Boolean enabled) {
    this.enabled = enabled;
    return this;
  }

  @Override
  public Integer getVersion() {
    return version;
  }

  public IntegrationCommunicationConfig setVersion(Integer version) {
    this.version = version;
    return this;
  }

  @Override
  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  @Override
  public IntegrationCommunicationConfig setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
    return this;
  }

  @Override
  public LocalDateTime getLastUpdated() {
    return lastUpdated;
  }

  @Override
  public IntegrationCommunicationConfig setLastUpdated(LocalDateTime lastUpdated) {
    this.lastUpdated = lastUpdated;
    return this;
  }
}
