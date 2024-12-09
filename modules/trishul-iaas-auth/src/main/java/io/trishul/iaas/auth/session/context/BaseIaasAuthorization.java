package io.trishul.iaas.auth.session.context;

import java.time.LocalDateTime;

public interface BaseIaasAuthorization {
    final String ATTR_ACCESS_KEY_ID = "accessKeyId";
    final String ATTR_ACCESS_SECRET_KEY = "accessSecretKey";
    final String ATTR_SESSION_TOKEN = "sessionToken";
    final String ATTR_EXPIRATION = "expiration";

    String getAccessKeyId();

    void setAccessKeyId(String accessKeyId);

    String getAccessSecretKey();

    void setAccessSecretKey(String accessSecretKey);

    String getSessionToken();

    void setSessionToken(String sessionToken);

    LocalDateTime getExpiration();

    void setExpiration(LocalDateTime Expiration);
}
