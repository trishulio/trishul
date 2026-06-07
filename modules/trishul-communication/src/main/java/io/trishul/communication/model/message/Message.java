package io.trishul.communication.model.message;

import io.trishul.base.types.base.pojo.Audited;
import io.trishul.base.types.base.pojo.CrudEntity;
import io.trishul.communication.model.channel.ChannelType;
import io.trishul.model.base.entity.BaseEntity;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Message extends BaseEntity
    implements CrudEntity<String, Message>, UpdateMessage<Message>, Audited<Message> {
  private String sid;
  private String from;
  private String to;
  private String body;
  private ChannelType channelType;
  private MessageStatus status;
  private MessageDirection direction;
  private List<String> mediaUrls;
  private String errorCode;
  private String errorMessage;
  private String price;
  private String priceUnit;
  private LocalDateTime createdAt;
  private LocalDateTime lastUpdated;

  public Message() {
    super();
  }

  public Message(String id) {
    this();
    setId(id);
  }

  public Message(String sid, String from, String to, String body, ChannelType channelType,
      MessageStatus status, MessageDirection direction, List<String> mediaUrls, String errorCode,
      String errorMessage, String price, String priceUnit, LocalDateTime createdAt,
      LocalDateTime lastUpdated) {
    this(sid);
    setFrom(from);
    setTo(to);
    setBody(body);
    setChannelType(channelType);
    setStatus(status);
    setDirection(direction);
    setMediaUrls(mediaUrls);
    setErrorCode(errorCode);
    setErrorMessage(errorMessage);
    setPrice(price);
    setPriceUnit(priceUnit);
    setCreatedAt(createdAt);
    setLastUpdated(lastUpdated);
  }

  @Override
  public String getId() {
    return sid;
  }

  @Override
  public Message setId(String id) {
    this.sid = id;
    return this;
  }

  @Override
  public String getFrom() {
    return from;
  }

  @Override
  public Message setFrom(String from) {
    this.from = from;
    return this;
  }

  @Override
  public String getTo() {
    return to;
  }

  @Override
  public Message setTo(String to) {
    this.to = to;
    return this;
  }

  @Override
  public String getBody() {
    return body;
  }

  @Override
  public Message setBody(String body) {
    this.body = body;
    return this;
  }

  @Override
  public ChannelType getChannelType() {
    return channelType;
  }

  @Override
  public Message setChannelType(ChannelType channelType) {
    this.channelType = channelType;
    return this;
  }

  public MessageStatus getStatus() {
    return status;
  }

  public Message setStatus(MessageStatus status) {
    this.status = status;
    return this;
  }

  public MessageDirection getDirection() {
    return direction;
  }

  public Message setDirection(MessageDirection direction) {
    this.direction = direction;
    return this;
  }

  @Override
  public List<String> getMediaUrls() {
    return mediaUrls == null ? null : new ArrayList<>(mediaUrls);
  }

  @Override
  public Message setMediaUrls(List<String> mediaUrls) {
    this.mediaUrls = mediaUrls == null ? null : new ArrayList<>(mediaUrls);
    return this;
  }

  public String getErrorCode() {
    return errorCode;
  }

  public Message setErrorCode(String errorCode) {
    this.errorCode = errorCode;
    return this;
  }

  public String getErrorMessage() {
    return errorMessage;
  }

  public Message setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
    return this;
  }

  public String getPrice() {
    return price;
  }

  public Message setPrice(String price) {
    this.price = price;
    return this;
  }

  public String getPriceUnit() {
    return priceUnit;
  }

  public Message setPriceUnit(String priceUnit) {
    this.priceUnit = priceUnit;
    return this;
  }

  @Override
  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  @Override
  public Message setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
    return this;
  }

  @Override
  public LocalDateTime getLastUpdated() {
    return lastUpdated;
  }

  @Override
  public Message setLastUpdated(LocalDateTime lastUpdated) {
    this.lastUpdated = lastUpdated;
    return this;
  }

  @Override
  public Integer getVersion() {
    return null;
  }

  public Message setVersion(Integer version) {
    return this;
  }
}
