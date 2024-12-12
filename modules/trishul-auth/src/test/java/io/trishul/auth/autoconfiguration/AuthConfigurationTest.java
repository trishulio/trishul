package io.trishul.auth.autoconfiguration;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.trishul.auth.session.context.holder.ContextHolder;
import io.trishul.auth.session.context.holder.ThreadLocalContextHolder;

public class AuthConfigurationTest {
    private AuthConfiguration config;

    @BeforeEach
    public void init() {
        config = new AuthConfiguration();
    }

    @Test
    public void testCtxHolder_ReturnsInstanceOfTypeThreadLocalContextHolder() {
        ContextHolder holder = config.ctxHolder();
        assertTrue(holder instanceof ThreadLocalContextHolder);
    }
}
