package io.trishul.iaas.auth.session.context;

import java.time.LocalDateTime;

public interface BaseIaasAuthorization<T extends BaseIaasAuthorization<T>> {
  final String ATTR_ACCESS_KEY_ID = "accessKeyId";
  final String ATTR_ACCESS_SECRET_KEY = "accessSecretKey";
  final String ATTR_SESSION_TOKEN = "sessionToken";
  final String ATTR_EXPIRATION = "expiration";

  String getAccessKeyId();

  T setAccessKeyId(String accessKeyId);

  String getAccessSecretKey();

  T setAccessSecretKey(String accessSecretKey);

  String getSessionToken();

  T setSessionToken(String sessionToken);

  LocalDateTime getExpiration();

  T setExpiration(LocalDateTime Expiration);
}
