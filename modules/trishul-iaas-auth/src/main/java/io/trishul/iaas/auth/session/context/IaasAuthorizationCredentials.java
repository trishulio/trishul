package io.trishul.iaas.auth.session.context;

import io.trishul.model.base.pojo.BaseModel;

public class IaasAuthorizationCredentials extends BaseModel {
  public static final String HEADER_NAME_IAAS_TOKEN = "X-Iaas-Token";

  private final String token;

  public IaasAuthorizationCredentials(String token) {
    this.token = token;
  }

  @Override
  public String toString() {
    return this.token;
  }
}
