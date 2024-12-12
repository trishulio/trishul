package io.company.brewcraft.dto.user;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AddUserRoleDtoTest {
    private AddUserRoleDto dto;

    @BeforeEach
    public void init() {
        dto = new AddUserRoleDto();
    }

    @Test
    public void testAllArgConstructor_SetsAllFields() {
        dto = new AddUserRoleDto("NAME");
        assertEquals("NAME", dto.getName());
    }

    @Test
    public void testAccessName() {
        assertNull(dto.getName());
        dto.setName("NAME_1");
        assertEquals("NAME_1", dto.getName());
    }
}