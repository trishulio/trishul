package io.trishul.auth.aws.session.context;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.springframework.security.oauth2.jwt.Jwt;

import io.trishul.auth.session.context.PrincipalContext;

public class CognitoPrincipalContext implements PrincipalContext {
    public static final String CLAIM_GROUPS = "cognito:groups";
    public static final String CLAIM_USERNAME = "username";
    public static final String CLAIM_SCOPE = "scope";
    public static final String ATTRIBUTE_EMAIL = "email";
    public static final String ATTRIBUTE_EMAIL_VERIFIED = "email_verified";

    private UUID groupId;
    private String username;
    private List<String> roles;

    public CognitoPrincipalContext(Jwt jwt) {
        if (jwt != null) {
            setUsername(jwt);
            setGroupId(jwt);
            setRoles(jwt);
        }
    }

    @Override
    public UUID getGroupId() {
        return this.groupId;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public List<String> getRoles() {
        return this.roles;
    }

    private void setUsername(Jwt jwt) {
        this.username = jwt.getClaimAsString(CLAIM_USERNAME);
    }

    private void setRoles(Jwt jwt) {
        this.roles = Arrays.asList(jwt.getClaimAsString(CLAIM_SCOPE).split(" "));
    }

    private void setGroupId(Jwt jwt) {
        List<String> groups = jwt.getClaimAsStringList(CLAIM_GROUPS);
        if (groups.size() > 1) {
            String msg = String.format("Each user should only belong to a single cognito group. Instead found %s", groups.size());
            throw new IllegalArgumentException(msg);
        }

        String sGroupId = groups.get(0);

        this.groupId = UUID.fromString(sGroupId);
    }
}
