package io.company.brewcraft.dto.user;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserSalutationDtoTest {
    private UserSalutationDto dto;

    @BeforeEach
    public void init() {
        dto = new UserSalutationDto();
    }

    @Test
    public void testAllArgConstructor() {
        dto = new UserSalutationDto(
            1L,
            "TITLE",
            LocalDateTime.of(2000, 1, 1, 0, 0),
            LocalDateTime.of(2001, 1, 1, 0, 0),
            1
        );

        assertEquals(1L, dto.getId());
        assertEquals("TITLE", dto.getTitle());
        assertEquals(LocalDateTime.of(2000, 1, 1, 0, 0), dto.getCreatedAt());
        assertEquals(LocalDateTime.of(2001, 1, 1, 0, 0), dto.getLastUpdated());
        assertEquals(1, dto.getVersion());
    }

    @Test
    public void testAccessId() {
        assertNull(dto.getId());
        dto.setId(1L);
        assertEquals(1L, dto.getId());
    }

    @Test
    public void testAccessTitle() {
        assertNull(dto.getTitle());
        dto.setTitle("title");
        assertEquals("title", dto.getTitle());
    }

    @Test
    public void testAccessCreatedAt() {
        assertNull(dto.getCreatedAt());
        dto.setCreatedAt(LocalDateTime.of(2000, 1, 1, 0, 0));
        assertEquals(LocalDateTime.of(2000, 1, 1, 0, 0), dto.getCreatedAt());
    }

    @Test
    public void testAccessLastUpdated() {
        assertNull(dto.getLastUpdated());
        dto.setLastUpdated(LocalDateTime.of(2001, 1, 1, 0, 0));
        assertEquals(LocalDateTime.of(2001, 1, 1, 0, 0), dto.getLastUpdated());
    }

    @Test
    public void testAccessVersion() {
        assertNull(dto.getVersion());
        dto.setVersion(1);
        assertEquals(1, dto.getVersion());
    }
}
