package io.trishul.user.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.trishul.user.role.binding.model.UserRoleBinding;
import io.trishul.user.role.model.UserRole;
import io.trishul.user.salutation.model.UserSalutation;
import io.trishul.user.status.UserStatus;

public class UserTest {
    private User user;

    @BeforeEach
    public void init() {
        user = new User();
    }

    @Test
    public void testAllArgConstructor_SetsAllFields() {
        user = new User(
            1L,
            "USER_NAME",
            "DISPLAY_NAME",
            "FIRST_NAME",
            "LAST_NAME",
            "EMAIL",
            "PHONE_NUMBER",
            URI.create("IMAGE_SRC"),
            new UserStatus(1L),
            new UserSalutation(2L),
            List.of(new UserRole(3L)),
            LocalDateTime.of(1999, 1, 1, 0, 0),
            LocalDateTime.of(2000, 1, 1, 0, 0),
            1
        );

        assertEquals(1L, user.getId());
        assertEquals("USER_NAME", user.getUserName());
        assertEquals("DISPLAY_NAME", user.getDisplayName());
        assertEquals("FIRST_NAME", user.getFirstName());
        assertEquals("LAST_NAME", user.getLastName());
        assertEquals("EMAIL", user.getEmail());
        assertEquals("PHONE_NUMBER", user.getPhoneNumber());
        assertEquals(URI.create("IMAGE_SRC"), user.getImageSrc());
        assertEquals(new UserStatus(1L), user.getStatus());
        assertEquals(new UserSalutation(2L), user.getSalutation());
        assertEquals(List.of(new UserRole(3L)), user.getRoles());
        assertEquals(LocalDateTime.of(1999, 1, 1, 0, 0), user.getCreatedAt());
        assertEquals(LocalDateTime.of(2000, 1, 1, 0, 0), user.getLastUpdated());
        assertEquals(1, user.getVersion());
    }

    @Test
    public void testAccessId() {
        assertNull(user.getId());
        user.setId(1L);
        assertEquals(1L, user.getId());
    }

    @Test
    public void testAccessUserName() {
        assertNull(user.getUserName());
        user.setUserName("userName");
        assertEquals("userName", user.getUserName());
    }

    @Test
    public void testAccessDisplayName() {
        assertNull(user.getDisplayName());
        user.setDisplayName("displayName");
        assertEquals("displayName", user.getDisplayName());
    }

    @Test
    public void testAccessFirstName() {
        assertNull(user.getFirstName());
        user.setFirstName("firstName");
        assertEquals("firstName", user.getFirstName());
    }

    @Test
    public void testAccessLastName() {
        assertNull(user.getLastName());
        user.setLastName("lastName");
        assertEquals("lastName", user.getLastName());
    }

    @Test
    public void testAccessEmail() {
        assertNull(user.getEmail());
        user.setEmail("email");
        assertEquals("email", user.getEmail());
    }

    @Test
    public void testAccessImageSrc() {
        assertNull(user.getImageSrc());
        user.setImageSrc(URI.create("imageSrc"));
        assertEquals(URI.create("imageSrc"), user.getImageSrc());
    }

    @Test
    public void testAccessPhoneNumber() {
        assertNull(user.getPhoneNumber());
        user.setPhoneNumber("phoneNumber");
        assertEquals("phoneNumber", user.getPhoneNumber());
    }

    @Test
    public void testAccessStatus() {
        assertNull(user.getStatus());
        user.setStatus(new UserStatus(1L));
        assertEquals(new UserStatus(1L), user.getStatus());
    }

    @Test
    public void testAccessSalutation() {
        assertNull(user.getSalutation());
        user.setSalutation(new UserSalutation(1L));
        assertEquals(new UserSalutation(1L), user.getSalutation());
    }

    @Test
    public void testAccessRoles() {
        assertNull(user.getRoles());
        user.setRoles(List.of(new UserRole(1L), new UserRole(2L)));
        assertEquals(List.of(new UserRole(1L), new UserRole(2L)), user.getRoles());

        user.setRoles(List.of(new UserRole(10L), new UserRole(20L)));
        assertEquals(List.of(new UserRole(10L), new UserRole(20L)), user.getRoles());
    }

    @Test
    public void testAccessRoles_NullValues() {
        assertNull(user.getRoles());
        user.setRoles(null);
        assertNull(user.getRoles());
    }

    @Test
    public void testAccessRoles_OverridesOldRoleAndAddNewOnes() {
        assertNull(user.getRoles());
        user.setRoles(List.of(new UserRole(1L)));
        assertEquals(List.of(new UserRole(1L)), user.getRoles());

        user.setRoles(List.of(new UserRole(10L), new UserRole(20L)));
        assertEquals(List.of(new UserRole(10L), new UserRole(20L)), user.getRoles());

        user.setRoles(null);
        assertEquals(new ArrayList<>(), user.getRoles());
    }

    @Test
    public void testGetRoleBindings() {
        assertNull(user.getRoles());
        user.setRoles(List.of(new UserRole(1L), new UserRole(2L)));

        List<UserRoleBinding> expected = List.of(
            new UserRoleBinding(null, new UserRole(1L), user),
            new UserRoleBinding(null, new UserRole(2L), user)
        );

        assertEquals(expected, user.getRoleBindings());
    }

    @Test
    public void testAccessLastUpdated() {
        assertNull(user.getLastUpdated());
        user.setLastUpdated(LocalDateTime.of(1999, 1, 1, 0, 0));
        assertEquals(LocalDateTime.of(1999, 1, 1, 0, 0), user.getLastUpdated());
    }

    @Test
    public void testAccessCreatedAt() {
        assertNull(user.getCreatedAt());
        user.setCreatedAt(LocalDateTime.of(2000, 1, 1, 0, 0));
        assertEquals(LocalDateTime.of(2000, 1, 1, 0, 0), user.getCreatedAt());
    }

    @Test
    public void testAccessVersion() {
        assertNull(user.getVersion());
        user.setVersion(1);
        assertEquals(1, user.getVersion());
    }
}
