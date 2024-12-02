package io.trishul.object.store.file.service.model.entity;

import java.net.URI;
import java.net.URL;
import java.time.LocalDateTime;

public interface BaseIaasObjectStoreFile {
    final String ATTR_FILE_KEY = "fileKey";
    final String ATTR_EXPIRATION = "expiration";
    final String ATTR_FILE_URL = "fileUrl";
    final String ATTR_MIN_VALID_UNTIL = "minValidUntil";

    URI getFileKey();

    void setFileKey(URI fileKey);

    LocalDateTime getExpiration();

    void setExpiration(LocalDateTime expiration);

    URL getFileUrl();

    void setFileUrl(URL fileUrl);

    void setMinValidUntil(LocalDateTime minValidUntil);
}
