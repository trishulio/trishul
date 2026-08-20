package sh.trishul.iaas.auth.session.context;

import java.time.LocalDateTime;

public interface BaseIaasAuthorization<T extends BaseIaasAuthorization<T>> {
  String ATTR_ACCESS_KEY_ID = "accessKeyId";
  String ATTR_ACCESS_SECRET_KEY = "accessSecretKey";
  String ATTR_SESSION_TOKEN = "sessionToken";
  String ATTR_EXPIRATION = "expiration";

  String getAccessKeyId();

  T setAccessKeyId(String accessKeyId);

  String getAccessSecretKey();

  T setAccessSecretKey(String accessSecretKey);

  String getSessionToken();

  T setSessionToken(String sessionToken);

  LocalDateTime getExpiration();

  T setExpiration(LocalDateTime Expiration);
}
