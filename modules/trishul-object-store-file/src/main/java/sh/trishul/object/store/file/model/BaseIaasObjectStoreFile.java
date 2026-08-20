package sh.trishul.object.store.file.model;

import java.net.URI;
import java.net.URL;
import java.time.LocalDateTime;
import org.springframework.util.MimeType;

public interface BaseIaasObjectStoreFile<T extends BaseIaasObjectStoreFile<T>> {
  String ATTR_FILE_KEY = "fileKey";
  String ATTR_EXPIRATION = "expiration";
  String ATTR_FILE_URL = "fileUrl";
  String ATTR_MIN_VALID_UNTIL = "minValidUntil";
  String ATTR_MIME_TYPE = "mimeType";

  URI getFileKey();

  T setFileKey(URI fileKey);

  LocalDateTime getExpiration();

  T setExpiration(LocalDateTime expiration);

  URL getFileUrl();

  T setFileUrl(URL fileUrl);

  T setMinValidUntil(LocalDateTime minValidUntil);

  MimeType getMimeType();

  T setMimeType(MimeType mimeType);
}
