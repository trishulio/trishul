package io.trishul.auth.aws.session.context;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.security.oauth2.jwt.Jwt;

import io.trishul.auth.session.context.PrincipalContext;

public class CognitoPrincipalContext implements PrincipalContext {
  public static final String CLAIM_GROUPS = "cognito:groups";
  public static final String CLAIM_USERNAME = "username";
  public static final String CLAIM_SCOPE = "scope";
  public static final String ATTRIBUTE_EMAIL = "email";
  public static final String ATTRIBUTE_EMAIL_VERIFIED = "email_verified";

  private final List<UUID> tenantIds;
  private final String username;
  private final List<String> roles;

  private CognitoPrincipalContext(List<UUID> tenantIds, String username, List<String> roles) {
    this.tenantIds = tenantIds != null ? new ArrayList<>(tenantIds) : Collections.emptyList();
    this.username = username;
    this.roles = roles != null ? new ArrayList<>(roles) : Collections.emptyList();
  }

  public static CognitoPrincipalContext fromJwt(Jwt jwt) {
    if (jwt == null) {
      throw new IllegalArgumentException("Jwt cannot be null");
    }

    String username = jwt.getClaimAsString(CLAIM_USERNAME);
    List<String> roles = Arrays.asList(jwt.getClaimAsString(CLAIM_SCOPE).split(" "));

    List<String> groups = jwt.getClaimAsStringList(CLAIM_GROUPS);
    List<UUID> tenantIds = groups.stream().map(UUID::fromString).toList();

    return new CognitoPrincipalContext(tenantIds, username, roles);
  }

  @Override
  public String getUsername() {
    return this.username;
  }

  @Override
  public List<String> getRoles() {
    return new ArrayList<>(this.roles);
  }

  @Override
  public List<UUID> getTenantIds() {
    return new ArrayList<>(this.tenantIds);
  }
}
