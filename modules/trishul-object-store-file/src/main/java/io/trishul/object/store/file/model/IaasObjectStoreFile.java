package io.trishul.object.store.file.model;

import io.trishul.base.types.base.pojo.CrudEntity;
import io.trishul.model.base.entity.BaseEntity;
import java.net.URI;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class IaasObjectStoreFile extends BaseEntity implements
    UpdateIaasObjectStoreFile<IaasObjectStoreFile>, CrudEntity<URI, IaasObjectStoreFile> {
  private URI fileKey;
  private LocalDateTime expiration;
  private URL fileUrl;

  public IaasObjectStoreFile() {
    super();
  }

  public IaasObjectStoreFile(URI id) {
    this();
    setId(id);
  }

  public IaasObjectStoreFile(URI fileKey, LocalDateTime expiration, URL fileUrl) {
    this(fileKey);
    setExpiration(expiration);
    setFileUrl(fileUrl);
  }

  @Override
  public URI getId() {
    return getFileKey();
  }

  @Override
  public IaasObjectStoreFile setId(URI id) {
    setFileKey(id);
    return this;
  }

  @Override
  public URI getFileKey() {
    return this.fileKey;
  }

  @Override
  public IaasObjectStoreFile setFileKey(URI fileKey) {
    this.fileKey = fileKey;
    return this;
  }

  @Override
  public LocalDateTime getExpiration() {
    return this.expiration;
  }

  @Override
  public IaasObjectStoreFile setExpiration(LocalDateTime expiration) {
    this.expiration = expiration;
    return this;
  }

  /**
   * setExpiration rounds the expiry time to the nearest hour or hour-and-a-half. This is to take
   * advantage of the caching in the ObjectStoreClient so that we can reuse the cache URL when
   * request is made to get URL for the same resource in an hour.
   */
  @Override
  public IaasObjectStoreFile setMinValidUntil(LocalDateTime minValidUntil) {
    int hourIncrement = minValidUntil.getMinute() <= 30 ? 1 : 2;

    setExpiration(minValidUntil.plusHours(hourIncrement).truncatedTo(ChronoUnit.HOURS));
    return this;
  }

  @Override
  public URL getFileUrl() {
    return this.fileUrl;
  }

  @Override
  public IaasObjectStoreFile setFileUrl(URL fileUrl) {
    this.fileUrl = fileUrl;
    return this;
  }

  @Override
  public Integer getVersion() {
    // Not implemented due to lack of use-case
    return null;
  }
}
