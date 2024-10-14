package io.trishul.object.model.dto;

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

    public void setFileKey(URI fileKey) {
        this.fileKey = fileKey;
    }

    public LocalDateTime getExpiration() {
        return this.expiration;
    }

    public void setExpiration(LocalDateTime expiration) {
        this.expiration = expiration;
    }

    public URL getFileUrl() {
        return this.fileUrl;
    }

    public void setFileUrl(URL fileUrl) {
        this.fileUrl = fileUrl;
    }
}
