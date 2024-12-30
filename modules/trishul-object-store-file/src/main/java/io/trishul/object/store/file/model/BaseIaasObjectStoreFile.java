package io.trishul.object.store.file.model;

import java.net.URI;
import java.net.URL;
import java.time.LocalDateTime;

public interface BaseIaasObjectStoreFile<T extends BaseIaasObjectStoreFile<T>> {
  final String ATTR_FILE_KEY = "fileKey";
  final String ATTR_EXPIRATION = "expiration";
  final String ATTR_FILE_URL = "fileUrl";
  final String ATTR_MIN_VALID_UNTIL = "minValidUntil";

  URI getFileKey();

  T setFileKey(URI fileKey);

  LocalDateTime getExpiration();

  T setExpiration(LocalDateTime expiration);

  URL getFileUrl();

  T setFileUrl(URL fileUrl);

  T setMinValidUntil(LocalDateTime minValidUntil);
}
