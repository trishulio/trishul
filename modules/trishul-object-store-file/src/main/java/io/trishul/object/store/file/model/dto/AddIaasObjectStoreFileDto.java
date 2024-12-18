package io.trishul.object.store.file.model.dto;

import io.trishul.model.base.dto.BaseDto;
import java.time.LocalDateTime;

public class AddIaasObjectStoreFileDto extends BaseDto {
    private LocalDateTime minValidUntil;

    public AddIaasObjectStoreFileDto() {
        super();
    }

    public AddIaasObjectStoreFileDto(LocalDateTime minValidUntil) {
        this.minValidUntil = minValidUntil;
    }

    public LocalDateTime getMinValidUntil() {
        return minValidUntil;
    }

    public void setMinValidUntil(LocalDateTime minValidUntil) {
        this.minValidUntil = minValidUntil;
    }
}
