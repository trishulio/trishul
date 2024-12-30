package io.trishul.user.role.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserRoleMapperTest {

    @Test
    public void testFromDto_CreatesRoleWithId_WhenIdIsNotNull() {
        UserRole role = mapper.fromDto(1L);
        UserRole expected = new UserRole().setId(1L);
        assertEquals(expected, role);
    }

    @Test
    public void testFromUpdateDto_ReturnsPojo_WhenArgIsNotNll() {
        UpdateUserRoleDto arg = new UpdateUserRoleDto(1L, "TITLE", 1);
        UserRole expected = new UserRole()
            .setId(1L)
            .setTitle("TITLE")
            .setVersion(1);
        assertEquals(expected, mapper.fromUpdateDto(arg));
    }

    @Test
    public void testFromAddDto_ReturnsPojo_WhenArgIsNotNll() {
        AddUserRoleDto arg = new AddUserRoleDto("TITLE");
        UserRole expected = new UserRole()
            .setTitle("TITLE");
        assertEquals(expected, mapper.fromAddDto(arg));
    }
}
