package io.trishul.auth.aws.session.context;

import java.util.ArrayList;
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

  private final UUID groupId;
  private final String username;
  private final List<String> roles;

  private CognitoPrincipalContext(UUID groupId, String username, List<String> roles) {
    this.groupId = groupId;
    this.username = username;
    this.roles = roles == null ? null : new ArrayList<>(roles);
  }

  public static CognitoPrincipalContext fromJwt(Jwt jwt) {
    if (jwt == null) {
      throw new IllegalArgumentException("Jwt cannot be null");
    }

    String username = jwt.getClaimAsString(CLAIM_USERNAME);
    List<String> roles = Arrays.asList(jwt.getClaimAsString(CLAIM_SCOPE).split(" "));

    List<String> groups = jwt.getClaimAsStringList(CLAIM_GROUPS);
    if (groups.size() > 1) {
      String msg = String.format(
          "Each user should only belong to a single cognito group. Instead found %s",
          groups.size());
      throw new IllegalArgumentException(msg);
    }

    UUID groupId = UUID.fromString(groups.get(0));
    return new CognitoPrincipalContext(groupId, username, roles);
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
    return new ArrayList<>(this.roles);
  }
}
