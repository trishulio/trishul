package io.trishul.auth.session.context;

import java.util.List;
import java.util.UUID;

import io.trishul.auth.session.token.IaasAuthorizationCredentials;
public interface PrincipalContext {
    public static final String HEADER_NAME_IAAS_TOKEN = "X-Iaas-Token";

    UUID getGroupId();

    String getUsername();

    List<String> getRoles();

    IaasAuthorizationCredentials getIaasLogin();
}
