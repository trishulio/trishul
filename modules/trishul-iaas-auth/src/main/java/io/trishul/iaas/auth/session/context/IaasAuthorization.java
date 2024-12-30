package io.trishul.iaas.auth.session.context;

import io.trishul.base.types.base.pojo.Identified;
import io.trishul.model.base.pojo.BaseModel;
import java.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IaasAuthorization extends BaseModel
    implements UpdateIaasAuthorization<IaasAuthorization>, Identified<String> {
  @SuppressWarnings("unused")
  private static final Logger log = LoggerFactory.getLogger(IaasAuthorization.class);

  private String accessKeyId;
  private String accessSecretKey;
  private String sessionToken;
  private LocalDateTime expiration;

  public IaasAuthorization() {
    super();
  }

  public IaasAuthorization(String accessKeyId, String accessSecretKey, String sessionToken,
      LocalDateTime expiration) {
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
  public final IaasAuthorization setAccessKeyId(String accessKeyId) {
    this.accessKeyId = accessKeyId;
    return this;
  }

  @Override
  public String getAccessSecretKey() {
    return accessSecretKey;
  }

  @Override
  public final IaasAuthorization setAccessSecretKey(String accessSecretKey) {
    this.accessSecretKey = accessSecretKey;
    return this;
  }

  @Override
  public String getSessionToken() {
    return sessionToken;
  }

  @Override
  public final IaasAuthorization setSessionToken(String sessionToken) {
    this.sessionToken = sessionToken;
    return this;
  }

  @Override
  public LocalDateTime getExpiration() {
    return expiration;
  }

  @Override
  public final IaasAuthorization setExpiration(LocalDateTime expiration) {
    this.expiration = expiration;
    return this;
  }
}
