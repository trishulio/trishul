package io.trishul.iaas.auth.session.context;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class IaasAuthorizationTest {
    private IaasAuthorization authorization;

    @BeforeEach
    public void init() {
        authorization = new IaasAuthorization();
    }

    @Test
    public void testNoArgConstructor_SetsNull() {
        assertNull(authorization.getAccessKeyId());
        assertNull(authorization.getAccessSecretKey());
        assertNull(authorization.getExpiration());
        assertNull(authorization.getSessionToken());
    }

    @Test
    public void testAllArgConstructor() {
        authorization = new IaasAuthorization("ACCESS_KEY_ID", "ACCESS_SECRET_KEY", "SESSION_TOKEN", LocalDateTime.of(2000, 1, 1, 0, 0));

        assertEquals("ACCESS_KEY_ID", authorization.getAccessKeyId());
        assertEquals("ACCESS_SECRET_KEY", authorization.getAccessSecretKey());
        assertEquals("SESSION_TOKEN", authorization.getSessionToken());
        assertEquals(LocalDateTime.of(2000, 1, 1, 0, 0), authorization.getExpiration());
    }

    @Test
    public void testGetSetId() {
        authorization.setId("ACCESS_KEY_ID");
        assertEquals("ACCESS_KEY_ID", authorization.getId());
        assertEquals("ACCESS_KEY_ID", authorization.getAccessKeyId());
    }

    @Test
    public void testGetSetAccessKeyId() {
        authorization.setAccessKeyId("ACCESS_KEY_ID");
        assertEquals("ACCESS_KEY_ID", authorization.getAccessKeyId());
        assertEquals("ACCESS_KEY_ID", authorization.getId());
    }

    @Test
    public void testGetSetAccessSecretKey() {
        authorization.setAccessSecretKey("ACCESS_SECRET_KEY");
        assertEquals("ACCESS_SECRET_KEY", authorization.getAccessSecretKey());
    }

    @Test
    public void testGetSetSessionToken() {
        authorization.setSessionToken("SESSION_TOKEN");
        assertEquals("SESSION_TOKEN", authorization.getSessionToken());
    }

    @Test
    public void testGetSetExpiration() {
        authorization.setExpiration(LocalDateTime.of(2000, 1, 1, 0, 0));
        assertEquals(LocalDateTime.of(2000, 1, 1, 0, 0), authorization.getExpiration());
    }

    @Test
    public void testGetVersion_ReturnsNull() {
        assertNull(authorization.getVersion());
    }
}
