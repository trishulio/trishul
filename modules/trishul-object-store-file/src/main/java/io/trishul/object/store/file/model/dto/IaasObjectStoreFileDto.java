package io.trishul.object.store.file.model.dto;

import io.trishul.model.base.dto.BaseDto;
import java.net.URI;
import java.net.URL;
import java.time.LocalDateTime;

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

    public final void setFileKey(URI fileKey) {
        this.fileKey = fileKey;
    }

    public LocalDateTime getExpiration() {
        return this.expiration;
    }

    public final void setExpiration(LocalDateTime expiration) {
        this.expiration = expiration;
    }

    public URL getFileUrl() {
        return this.fileUrl;
    }

    public final void setFileUrl(URL fileUrl) {
        this.fileUrl = fileUrl;
    }
}
