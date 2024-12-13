package io.trishul.money.dto;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class MoneyDtoTest {
    MoneyDto dto;

    @BeforeEach
    public void init() {
        dto = new MoneyDto();
    }

    @Test
    public void testAllArgsConstructor() {
        dto = new MoneyDto("CAD", new BigDecimal("999"));
        assertEquals("CAD", dto.getCurrency());
        assertEquals(new BigDecimal("999"), dto.getAmount());
    }

    @Test
    public void testAccessCurrency() {
        assertNull(dto.getCurrency());
        dto.setCurrency("CAD");
        assertEquals("CAD", dto.getCurrency());
    }

    @Test
    public void testAccessAmount() {
        assertNull(dto.getAmount());
        dto.setAmount(new BigDecimal("999.00"));
        assertEquals(new BigDecimal("999"), dto.getAmount());

        dto.setAmount(new BigDecimal("999"));
        assertEquals(new BigDecimal("999"), dto.getAmount());
    }

    @Test
    @Disabled("The equality check does not uses getters to compare the values")
    public void testEquality() {
        MoneyDto dto1 = new MoneyDto("CAD", new BigDecimal("10"));
        MoneyDto dto2 = new MoneyDto("CAD", new BigDecimal("10.00"));

        assertEquals(dto1, dto2);
    }
}
