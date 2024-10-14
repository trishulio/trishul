package io.trishul.auth.session.context;

import java.util.List;
import java.util.UUID;

import io.trishul.auth.session.token.IaasAuthorizationCredentials;
public interface PrincipalContext {
    UUID getGroupId();

    String getUsername();

    List<String> getRoles();

    IaasAuthorizationCredentials getIaasLogin();
}
