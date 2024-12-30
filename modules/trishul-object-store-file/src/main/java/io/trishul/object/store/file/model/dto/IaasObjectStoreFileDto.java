package io.trishul.object.store.file.model.dto;

import java.net.URI;
import java.net.URL;
import java.time.LocalDateTime;
import io.trishul.model.base.dto.BaseDto;

public class IaasObjectStoreFileDto extends BaseDto {
  private URI fileKey;
  private LocalDateTime expiration;
  private URL fileUrl;

  public IaasObjectStoreFileDto() {
    super();
  }

  public IaasObjectStoreFileDto(URI fileKey) {
    this();
    setFileKey(fileKey);
  }

  public IaasObjectStoreFileDto(URI fileKey, LocalDateTime expiration, URL fileUrl) {
    this(fileKey);
    setExpiration(expiration);
    setFileUrl(fileUrl);
  }

  public URI getFileKey() {
    return this.fileKey;
  }

  public IaasObjectStoreFileDto setFileKey(URI fileKey) {
    this.fileKey = fileKey;
    return this;
  }

  public LocalDateTime getExpiration() {
    return this.expiration;
  }

  public IaasObjectStoreFileDto setExpiration(LocalDateTime expiration) {
    this.expiration = expiration;
    return this;
  }

  public URL getFileUrl() {
    return this.fileUrl;
  }

  public IaasObjectStoreFileDto setFileUrl(URL fileUrl) {
    this.fileUrl = fileUrl;
    return this;
  }
}
