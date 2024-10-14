package io.trishul.object.service.controller.dto;

import java.time.LocalDateTime;

import io.trishul.model.base.dto.BaseDto;

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
