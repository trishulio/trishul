package io.trishul.object.store.file.service.model.entity;

import java.net.URI;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import io.trishul.model.base.entity.BaseEntity;
import io.trishul.model.base.pojo.CrudEntity;

public class IaasObjectStoreFile extends BaseEntity implements UpdateIaasObjectStoreFile, CrudEntity<URI> {
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
    public void setId(URI id) {
        setFileKey(id);
    }

    @Override
    public URI getFileKey() {
        return this.fileKey;
    }

    @Override
    public void setFileKey(URI fileKey) {
        this.fileKey = fileKey;
    }

    @Override
    public LocalDateTime getExpiration() {
        return this.expiration;
    }

    @Override
    public void setExpiration(LocalDateTime expiration) {
        this.expiration = expiration;
    }

    /**
     * setExpiration rounds the expiry time to the nearest hour or hour-and-a-half.
     * This is to take advantage of the caching in the ObjectStoreClient so that we
     * can reuse the cache URL when request is made to get URL for the same resource
     * in an hour.
     */
    @Override
    public void setMinValidUntil(LocalDateTime minValidUntil) {
        int hourIncrement = minValidUntil.getMinute() <= 30 ? 1 : 2;

        setExpiration(minValidUntil.plusHours(hourIncrement)
                .truncatedTo(ChronoUnit.HOURS));
    }

    @Override
    public URL getFileUrl() {
        return this.fileUrl;
    }

    @Override
    public void setFileUrl(URL fileUrl) {
        this.fileUrl = fileUrl;
    }

    @Override
    public Integer getVersion() {
        // Not implemented due to lack of use-case
        return null;
    }
}
