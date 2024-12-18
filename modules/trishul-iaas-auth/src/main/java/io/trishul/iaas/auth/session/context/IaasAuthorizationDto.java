// TODO: Looks liek this file is not being used. Gonna skip. Add to module if use arises
package io.trishul.iaas.auth.session.context;

import io.trishul.model.base.dto.BaseDto;
import java.time.LocalDateTime;

public class IaasAuthorizationDto extends BaseDto {
    private String accessKeyId;
    private String accessSecretKey;
    private String sessionToken;
    private LocalDateTime expiration;

    public IaasAuthorizationDto() {
        super();
    }

    public IaasAuthorizationDto(
            String accessKeyId,
            String accessSecretKey,
            String sessionToken,
            LocalDateTime expiration) {
        setAccessKeyId(accessKeyId);
        setAccessSecretKey(accessSecretKey);
        setSessionToken(sessionToken);
        setExpiration(expiration);
    }

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getAccessSecretKey() {
        return accessSecretKey;
    }

    public void setAccessSecretKey(String accessSecretKey) {
        this.accessSecretKey = accessSecretKey;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    public LocalDateTime getExpiration() {
        return expiration;
    }

    public void setExpiration(LocalDateTime expiration) {
        this.expiration = expiration;
    }
}
