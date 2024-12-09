package io.trishul.auth.session.context;

import org.springframework.security.oauth2.jwt.Jwt;

public interface PrincipalContextBuilder {
    PrincipalContext build(Jwt jwt);
}
