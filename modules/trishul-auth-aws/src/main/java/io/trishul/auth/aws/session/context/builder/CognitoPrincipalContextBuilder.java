package io.trishul.auth.aws.session.context.builder;

import org.springframework.security.oauth2.jwt.Jwt;

import io.trishul.auth.aws.session.context.CognitoPrincipalContext;
import io.trishul.auth.session.context.PrincipalContext;
import io.trishul.auth.session.context.PrincipalContextBuilder;

public class CognitoPrincipalContextBuilder implements PrincipalContextBuilder {

    @Override
    public PrincipalContext build(Jwt jwt) {
        return new CognitoPrincipalContext(jwt);
    }
    
}
