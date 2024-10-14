package io.trishul.auth.session.context;

import io.trishul.auth.session.token.IaasAuthorizationCredentials;

import java.util.List;
import java.util.UUID;

public interface PrincipalContext {
    UUID getGroupId();

    String getUsername();

    List<String> getRoles();

    IaasAuthorizationCredentials getIaasLogin();
}
