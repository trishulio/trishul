package io.trishul.user.salutation.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserSalutationMapperTest {
    private UserSalutationMapper mapper;

    @BeforeEach
    public void init() {
        mapper = UserSalutationMapper.INSTANCE;
    }

    @Test
    public void testFromDto_ReturnEntity_WhenIdIsNotNull() {
        UserSalutation salutation = mapper.fromDto(1L);

        UserSalutation expected = new UserSalutation(1L);

        assertEquals(expected, salutation);
    }

    @Test
    public void testFromDto_ReturnsEntity_WhenIdIsNull() {
        assertNull(mapper.fromDto((Long) null));
    }

    @Test
    public void testToDto_ReturnsNull_WhenEntityIsNull() {
        assertNull(mapper.toDto(null));
    }

    @Test
    public void testToDto_ReturnsDto_WhenEntityIsNotNull() {
        UserSalutation salutation =
                new UserSalutation(
                        1L,
                        "title",
                        LocalDateTime.of(1999, 1, 1, 0, 0),
                        LocalDateTime.of(1999, 1, 1, 0, 0),
                        1);

        UserSalutationDto dto = mapper.toDto(salutation);

        UserSalutationDto expected =
                new UserSalutationDto(
                        1L,
                        "title",
                        LocalDateTime.of(1999, 1, 1, 0, 0),
                        LocalDateTime.of(1999, 1, 1, 0, 0),
                        1);

        assertEquals(expected, dto);
    }
}
