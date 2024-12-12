package io.company.brewcraft.model;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class IaasUserTest {
    private IaasUser iaasUser;

    @BeforeEach
    public void init() {
        iaasUser = new IaasUser();
    }

    @Test
    public void testNoArgConstructor() {
        assertNull(iaasUser.getId());
        assertNull(iaasUser.getUserName());
        assertNull(iaasUser.getEmail());
        assertNull(iaasUser.getPhoneNumber());
        assertNull(iaasUser.getLastUpdated());
        assertNull(iaasUser.getCreatedAt());
        assertNull(iaasUser.getVersion());
    }

    @Test
    public void testAllArgConstructor() {
        iaasUser = new IaasUser("USERNAME", "EMAIL", "PHONE_NUMBER", LocalDateTime.of(2000, 1, 1, 0, 0), LocalDateTime.of(2001, 1, 1, 0, 0));

        assertEquals("EMAIL", iaasUser.getId());
        assertEquals("USERNAME", iaasUser.getUserName());
        assertEquals("EMAIL", iaasUser.getEmail());
        assertEquals("PHONE_NUMBER", iaasUser.getPhoneNumber());
        assertEquals(LocalDateTime.of(2000, 1, 1, 0, 0), iaasUser.getCreatedAt());
        assertEquals(LocalDateTime.of(2001, 1, 1, 0, 0), iaasUser.getLastUpdated());
    }

    @Test
    public void testGetSetId() {
        iaasUser.setId("ID");
        assertEquals("ID", iaasUser.getId());
    }

    @Test
    public void testGetSetUserName() {
        iaasUser.setUserName("USERNAME");
        assertEquals("USERNAME", iaasUser.getUserName());
    }

    @Test
    public void testGetSetEmail() {
        iaasUser.setEmail("EMAIL");
        assertEquals("EMAIL", iaasUser.getEmail());
    }

    @Test
    public void testGetSetPhoneNumber() {
        iaasUser.setPhoneNumber("PHONE_NUMBER");
        assertEquals("PHONE_NUMBER", iaasUser.getPhoneNumber());
    }

    @Test
    public void testGetSetLastUpdated() {
        iaasUser.setLastUpdated(LocalDateTime.of(2000, 1, 1, 0, 0));
        assertEquals(LocalDateTime.of(2000, 1, 1, 0, 0), iaasUser.getLastUpdated());
    }

    @Test
    public void testGetSetCreatedAt() {
        iaasUser.setCreatedAt(LocalDateTime.of(2000, 1, 1, 0, 0));
        assertEquals(LocalDateTime.of(2000, 1, 1, 0, 0), iaasUser.getCreatedAt());
    }
}
