package io.trishul.communication.model.channel;

import io.trishul.base.types.base.pojo.Audited;
import io.trishul.base.types.base.pojo.CrudEntity;
import io.trishul.model.base.entity.BaseEntity;
import java.time.LocalDateTime;

public class CommunicationChannel extends BaseEntity
    implements CrudEntity<String, CommunicationChannel>,
    UpdateCommunicationChannel<CommunicationChannel>, Audited<CommunicationChannel> {
  private String sid;
  private String address;
  private ChannelType channelType;
  private String displayName;
  private String capabilities;
  private LocalDateTime createdAt;
  private LocalDateTime lastUpdated;

  public CommunicationChannel() {
    super();
  }

  public CommunicationChannel(String id) {
    this();
    setId(id);
  }

  public CommunicationChannel(String sid, String address, ChannelType channelType,
      String displayName, String capabilities, LocalDateTime createdAt, LocalDateTime lastUpdated) {
    this(sid);
    setAddress(address);
    setChannelType(channelType);
    setDisplayName(displayName);
    setCapabilities(capabilities);
    setCreatedAt(createdAt);
    setLastUpdated(lastUpdated);
  }

  @Override
  public String getId() {
    return sid;
  }

  @Override
  public CommunicationChannel setId(String id) {
    this.sid = id;
    return this;
  }

  @Override
  public String getAddress() {
    return address;
  }

  @Override
  public CommunicationChannel setAddress(String address) {
    this.address = address;
    return this;
  }

  @Override
  public ChannelType getChannelType() {
    return channelType;
  }

  @Override
  public CommunicationChannel setChannelType(ChannelType channelType) {
    this.channelType = channelType;
    return this;
  }

  @Override
  public String getDisplayName() {
    return displayName;
  }

  @Override
  public CommunicationChannel setDisplayName(String displayName) {
    this.displayName = displayName;
    return this;
  }

  @Override
  public String getCapabilities() {
    return capabilities;
  }

  @Override
  public CommunicationChannel setCapabilities(String capabilities) {
    this.capabilities = capabilities;
    return this;
  }

  @Override
  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  @Override
  public CommunicationChannel setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
    return this;
  }

  @Override
  public LocalDateTime getLastUpdated() {
    return lastUpdated;
  }

  @Override
  public CommunicationChannel setLastUpdated(LocalDateTime lastUpdated) {
    this.lastUpdated = lastUpdated;
    return this;
  }

  @Override
  public Integer getVersion() {
    return null;
  }

  public CommunicationChannel setVersion(Integer version) {
    return this;
  }
}
