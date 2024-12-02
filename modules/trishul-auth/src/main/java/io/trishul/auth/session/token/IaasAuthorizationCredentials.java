package io.trishul.auth.session.token;

import io.trishul.model.base.pojo.BaseModel;

public class IaasAuthorizationCredentials extends BaseModel {
    private final String token;

    public IaasAuthorizationCredentials(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return this.token;
    }
}
