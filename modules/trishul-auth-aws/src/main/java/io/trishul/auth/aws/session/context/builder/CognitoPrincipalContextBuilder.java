package io.trishul.auth.aws.session.context.builder;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.oauth2.jwt.Jwt;

import io.trishul.auth.aws.session.context.CognitoPrincipalContext;
import io.trishul.auth.session.context.PrincipalContext;
import io.trishul.auth.session.context.PrincipalContextBuilder;

public class CognitoPrincipalContextBuilder implements PrincipalContextBuilder {

    @Override
    public PrincipalContext build(Jwt jwt, HttpServletRequest req) {
        return new CognitoPrincipalContext(jwt, req);
    }
    
}
