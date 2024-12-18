package io.trishul.iaas.auth.session.context;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class IaasAuthorizationMapperTest {
    private IaasAuthorizationMapper mapper;

    @BeforeEach
    public void init() {
        mapper = IaasAuthorizationMapper.INSTANCE;
    }

    @Test
    public void testToDto_ReturnsNull_WhenArgIsNull() {
        assertNull(mapper.toDto(null));
    }

    @Test
    public void testToDto_ReturnsDto_WhenArgIsNotNull() {
        IaasAuthorization pojo =
                new IaasAuthorization(
                        "ACCESS_KEY_ID",
                        "ACCESS_SECRET_KEY",
                        "SESSION_TOKEN",
                        LocalDateTime.of(2000, 1, 1, 0, 0));

        IaasAuthorizationDto dto = mapper.toDto(pojo);

        IaasAuthorizationDto expected =
                new IaasAuthorizationDto(
                        "ACCESS_KEY_ID",
                        "ACCESS_SECRET_KEY",
                        "SESSION_TOKEN",
                        LocalDateTime.of(2000, 1, 1, 0, 0));
        assertEquals(expected, dto);
    }
}
