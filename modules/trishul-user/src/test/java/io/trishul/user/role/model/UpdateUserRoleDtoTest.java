package io.company.brewcraft.dto;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.dto.user.UpdateUserRoleDto;

public class UpdateUserRoleDtoTest {
    private UpdateUserRoleDto dto;

    @BeforeEach
    public void init() {
        dto = new UpdateUserRoleDto();
    }

    @Test
    public void testAllArgs_SetsAllFields() {
        dto = new UpdateUserRoleDto(
            1L,
            "ROLE_NAME",
            1
        );

        assertEquals(1L, dto.getId());
        assertEquals("ROLE_NAME", dto.getName());
        assertEquals(1, dto.getVersion());
    }

    @Test
    public void testAccessId() {
        assertNull(dto.getId());
        dto.setId(1L);
        assertEquals(1L, dto.getId());
    }

    @Test
    public void testAccessName() {
        assertNull(dto.getName());
        dto.setName("roleName");
        assertEquals("roleName", dto.getName());
    }

    @Test
    public void testAccessVersion() {
        assertNull(dto.getVersion());
        dto.setVersion(1);
        assertEquals(1, dto.getVersion());
    }
}
