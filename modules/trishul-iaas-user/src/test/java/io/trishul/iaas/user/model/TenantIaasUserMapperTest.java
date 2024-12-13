package io.trishul.iaas.user.model;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.trishul.iaas.user.model.IaasUser;
import io.trishul.user.model.User;
import io.trishul.user.role.model.UserRole;
import io.trishul.user.salutation.model.UserSalutation;
import io.trishul.user.status.UserStatus;

public class TenantIaasUserMapperTest {
    private TenantIaasUserMapper mapper;

    @BeforeEach
    public void init() {
        mapper = TenantIaasUserMapper.INSTANCE;
    }

    @Test
    public void testFromUsers_ReturnsNull_WhenArgIsNull() {
        assertNull(mapper.fromUsers(null));
    }

    @Test
    public void testFromUsers_ReturnsListOfIaasUser_WhenArgIsNotNull() {
        List<IaasUser> users = mapper.fromUsers(List.of(
            new User(
                1L,
                "USERNAME",
                "DISPLAY_NAME",
                "FIRST_NAME",
                "LAST_NAME",
                "EMAIL",
                "PHONE_NUMBER",
                URI.create("URI"),
                new UserStatus(1L),
                new UserSalutation(1L),
                List.of(new UserRole(1L)),
                LocalDateTime.of(2000, 1, 1, 0, 0),
                LocalDateTime.of(2001, 1, 1, 0, 0),
                1
            )
        ));

        List<IaasUser> expected = List.of(
            new IaasUser("USERNAME", "EMAIL", "PHONE_NUMBER", null, null)
        );
        assertEquals(expected, users);
    }

    @Test
    public void fromUser_ReturnsNull_WhenArgIsNull() {
        assertNull(mapper.fromUser(null));
    }

    @Test
    public void testFromUser_ReturnsIaasUser_WhenArgIsNotNull() {
        User arg = new User(
            1L,
            "USERNAME",
            "DISPLAY_NAME",
            "FIRST_NAME",
            "LAST_NAME",
            "EMAIL",
            "PHONE_NUMBER",
            URI.create("URI"),
            new UserStatus(1L),
            new UserSalutation(1L),
            List.of(new UserRole(1L)),
            LocalDateTime.of(2000, 1, 1, 0, 0),
            LocalDateTime.of(2001, 1, 1, 0, 0),
            1
        );

        IaasUser user = mapper.fromUser(arg);

        IaasUser expected = new IaasUser("USERNAME", "EMAIL", "PHONE_NUMBER", null, null);
        assertEquals(expected, user);
    }
}
