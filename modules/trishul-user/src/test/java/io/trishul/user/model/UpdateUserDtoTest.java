package io.trishul.user.model;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URI;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UpdateUserDtoTest {
    private UpdateUserDto dto;

    @BeforeEach
    public void init() {
        dto = new UpdateUserDto();
    }

    @Test
    public void testAllArgConstructor_SetsAllFields() {
        dto = new UpdateUserDto(
            1L,
            "userName",
            "displayName",
            "firstName",
            "lastName",
            1L,
            2L,
            "phoneNumber",
            URI.create("imageSrc"),
            List.of(10L),
            1
        );

        assertEquals(1L, dto.getId());
        assertEquals("userName", dto.getUserName());
        assertEquals("displayName", dto.getDisplayName());
        assertEquals("firstName", dto.getFirstName());
        assertEquals("lastName", dto.getLastName());
        assertEquals(1L, dto.getStatusId());
        assertEquals(2L, dto.getSalutationId());
        assertEquals("phoneNumber", dto.getPhoneNumber());
        assertEquals(URI.create("imageSrc"), dto.getImageSrc());
        assertEquals(List.of(10L), dto.getRoleIds());
        assertEquals(1, dto.getVersion());
    }

    @Test
    public void testAccessDisplayName() {
        assertNull(dto.getDisplayName());
        dto.setDisplayName("displayName");
        assertEquals("displayName", dto.getDisplayName());
    }

    @Test
    public void testAccessFirstName() {
        assertNull(dto.getFirstName());
        dto.setFirstName("firstName");
        assertEquals("firstName", dto.getFirstName());
    }

    @Test
    public void testAccessLastName() {
        assertNull(dto.getLastName());
        dto.setLastName("lastName");
        assertEquals("lastName", dto.getLastName());
    }

    @Test
    public void testAccessStatusId() {
        assertNull(dto.getStatusId());
        dto.setStatusId(1L);
        assertEquals(1L, dto.getStatusId());
    }

    @Test
    public void testAccessSalutationId() {
        assertNull(dto.getSalutationId());
        dto.setSalutationId(10L);
        assertEquals(10L, dto.getSalutationId());
    }

    @Test
    public void testAccessPhoneNumber() {
        assertNull(dto.getPhoneNumber());
        dto.setPhoneNumber("phoneNumber");
        assertEquals("phoneNumber", dto.getPhoneNumber());
    }

    @Test
    public void testAccessImageSrc() {
        assertNull(dto.getImageSrc());
        dto.setImageSrc(URI.create("imageSrc"));
        assertEquals(URI.create("imageSrc"), dto.getImageSrc());
    }

    @Test
    public void testAccessRoleIds() {
        assertNull(dto.getRoleIds());
        dto.setRoleIds(List.of(10L));
        assertEquals(List.of(10L), dto.getRoleIds());

        dto.setRoleIds(List.of(20L));
        assertEquals(List.of(20L), dto.getRoleIds());
    }

    @Test
    public void testAccessVersion() {
        assertNull(dto.getVersion());
        dto.setVersion(1);
        assertEquals(1, dto.getVersion());
    }
}