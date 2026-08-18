package sh.trishul.object.store.file.model.dto;

import java.net.URI;
import java.time.LocalDateTime;
import org.springframework.util.MimeType;
import sh.trishul.model.base.dto.BaseDto;

public class UpdateIaasObjectStoreFileDto extends BaseDto {
  private URI fileKey;
  private LocalDateTime minValidUntil;
  private MimeType mimeType;

  public UpdateIaasObjectStoreFileDto() {
    super();
  }

  public UpdateIaasObjectStoreFileDto(URI fileKey) {
    setFileKey(fileKey);
  }

  public UpdateIaasObjectStoreFileDto(URI fileKey, LocalDateTime minValidUntil) {
    this(fileKey);
    setMinValidUntil(minValidUntil);
  }

  public UpdateIaasObjectStoreFileDto(URI fileKey, LocalDateTime minValidUntil, MimeType mimeType) {
    this(fileKey, minValidUntil);
    setMimeType(mimeType);
  }

  public URI getFileKey() {
    return this.fileKey;
  }

  public UpdateIaasObjectStoreFileDto setFileKey(URI fileKey) {
    this.fileKey = fileKey;
    return this;
  }

  public LocalDateTime getMinValidUntil() {
    return minValidUntil;
  }

  public UpdateIaasObjectStoreFileDto setMinValidUntil(LocalDateTime minValidUntil) {
    this.minValidUntil = minValidUntil;
    return this;
  }

  public MimeType getMimeType() {
    return mimeType;
  }

  public UpdateIaasObjectStoreFileDto setMimeType(MimeType mimeType) {
    this.mimeType = mimeType;
    return this;
  }
}
