package io.company.brewcraft.dto;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UnitDtoTest {
    private UnitDto dto;

    @BeforeEach
    public void init() {
        dto = new UnitDto();
    }

    @Test
    public void testNoArgConstructor() {
        assertNull(dto.getSymbol());
    }

    @Test
    public void testAllArgConstructor() {
        dto = new UnitDto("SYMBOL");

        assertEquals("SYMBOL", dto.getSymbol());
    }

    @Test
    public void testAccessSymbol() {
        dto.setSymbol("SYMBOL_1");
        assertEquals("SYMBOL_1", dto.getSymbol());
    }
}
