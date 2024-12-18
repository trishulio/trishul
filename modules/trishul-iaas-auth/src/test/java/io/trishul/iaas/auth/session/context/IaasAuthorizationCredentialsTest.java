package io.trishul.iaas.auth.session.context;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class IaasAuthorizationCredentialsTest {
    private IaasAuthorizationCredentials credentials;

    @BeforeEach
    public void init() {
        credentials = new IaasAuthorizationCredentials("TOKEN");
    }

    @Test
    public void testAllArgConstructor() {
        assertEquals("TOKEN", credentials.toString());
    }
}
