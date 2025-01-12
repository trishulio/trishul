// TODO: Looks liek this file is not being used. Gonna skip. Add to module if use arises
package io.trishul.iaas.auth.session.context;

import java.time.LocalDateTime;
import io.trishul.model.base.dto.BaseDto;

public class IaasAuthorizationDto extends BaseDto {
  private String accessKeyId;
  private String accessSecretKey;
  private String sessionToken;
  private LocalDateTime expiration;

  public IaasAuthorizationDto() {
    super();
  }

  public IaasAuthorizationDto(String accessKeyId, String accessSecretKey, String sessionToken,
      LocalDateTime expiration) {
    setAccessKeyId(accessKeyId);
    setAccessSecretKey(accessSecretKey);
    setSessionToken(sessionToken);
    setExpiration(expiration);
  }

  public String getAccessKeyId() {
    return accessKeyId;
  }

  public IaasAuthorizationDto setAccessKeyId(String accessKeyId) {
    this.accessKeyId = accessKeyId;
    return this;
  }

  public String getAccessSecretKey() {
    return accessSecretKey;
  }

  public IaasAuthorizationDto setAccessSecretKey(String accessSecretKey) {
    this.accessSecretKey = accessSecretKey;
    return this;
  }

  public String getSessionToken() {
    return sessionToken;
  }

  public IaasAuthorizationDto setSessionToken(String sessionToken) {
    this.sessionToken = sessionToken;
    return this;
  }

  public LocalDateTime getExpiration() {
    return expiration;
  }

  public IaasAuthorizationDto setExpiration(LocalDateTime expiration) {
    this.expiration = expiration;
    return this;
  }
}
