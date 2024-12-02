package io.trishul.auth.session.context;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.trishul.model.base.pojo.BaseModel;
import io.trishul.model.base.pojo.Identified;

public class IaasAuthorization extends BaseModel implements UpdateIaasAuthorization, Identified<String> {
    @SuppressWarnings("unused")
    private static final Logger log = LoggerFactory.getLogger(IaasAuthorization.class);

    private String accessKeyId;
    private String accessSecretKey;
    private String sessionToken;
    private LocalDateTime expiration;

    public IaasAuthorization() {
        super();
    }

    public IaasAuthorization(String accessKeyId, String accessSecretKey, String sessionToken, LocalDateTime expiration) {
        this();
        setAccessKeyId(accessKeyId);
        setAccessSecretKey(accessSecretKey);
        setSessionToken(sessionToken);
        setExpiration(expiration);
    }

    @Override
    public String getId() {
        return this.getAccessKeyId();
    }

    @Override
    public String getAccessKeyId() {
        return accessKeyId;
    }

    @Override
    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    @Override
    public String getAccessSecretKey() {
        return accessSecretKey;
    }

    @Override
    public void setAccessSecretKey(String accessSecretKey) {
        this.accessSecretKey = accessSecretKey;
    }

    @Override
    public String getSessionToken() {
        return sessionToken;
    }

    @Override
    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    @Override
    public LocalDateTime getExpiration() {
        return expiration;
    }

    @Override
    public void setExpiration(LocalDateTime expiration) {
        this.expiration = expiration;
    }
}
