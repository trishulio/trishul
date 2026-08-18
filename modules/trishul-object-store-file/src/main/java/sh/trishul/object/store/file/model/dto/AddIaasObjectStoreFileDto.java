package sh.trishul.object.store.file.model.dto;

import java.time.LocalDateTime;
import org.springframework.util.MimeType;
import sh.trishul.model.base.dto.BaseDto;

public class AddIaasObjectStoreFileDto extends BaseDto {
  private LocalDateTime minValidUntil;
  private MimeType mimeType;

  public AddIaasObjectStoreFileDto() {
    super();
  }

  public AddIaasObjectStoreFileDto(LocalDateTime minValidUntil) {
    this.minValidUntil = minValidUntil;
  }

  public AddIaasObjectStoreFileDto(LocalDateTime minValidUntil, MimeType mimeType) {
    this(minValidUntil);
    setMimeType(mimeType);
  }

  public LocalDateTime getMinValidUntil() {
    return minValidUntil;
  }

  public AddIaasObjectStoreFileDto setMinValidUntil(LocalDateTime minValidUntil) {
    this.minValidUntil = minValidUntil;
    return this;
  }

  public MimeType getMimeType() {
    return mimeType;
  }

  public AddIaasObjectStoreFileDto setMimeType(MimeType mimeType) {
    this.mimeType = mimeType;
    return this;
  }
}
