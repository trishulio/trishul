package sh.trishul.auth.aws.session.context.builder;

import org.springframework.security.oauth2.jwt.Jwt;
import sh.trishul.auth.aws.session.context.CognitoPrincipalContext;
import sh.trishul.auth.session.context.PrincipalContext;
import sh.trishul.auth.session.context.PrincipalContextBuilder;

public class CognitoPrincipalContextBuilder implements PrincipalContextBuilder {

  @Override
  public PrincipalContext build(Jwt jwt) {
    return CognitoPrincipalContext.fromJwt(jwt);
  }
}
