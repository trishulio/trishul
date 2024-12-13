package io.trishul.quantity.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;

import javax.measure.MetricPrefix;
import javax.measure.Unit;
import javax.measure.quantity.AmountOfSubstance;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import io.trishul.quantity.unit.dto.UnitDto;
import tec.uom.se.quantity.QuantityDimension;
import tec.uom.se.unit.BaseUnit;
import tec.uom.se.unit.Units;

public class QuantityUnitMapperTest {
    private QuantityUnitMapper mapper;

    @BeforeEach
    public void init() {
        mapper = QuantityUnitMapper.INSTANCE;
    }

    @Test
    public void testFromSymbol_ReturnsNull_WhenSymbolIsNull() {
        assertNull(mapper.fromSymbol(null));
    }

    @Test
    @Disabled
    public void testFromSymbol_ReturnsPojoMatchingSymbol_WhenSymbolIsNotNull() {
        assertSame(Units.AMPERE, mapper.fromSymbol("A"));
        assertSame(Units.BECQUEREL, mapper.fromSymbol(Units.BECQUEREL.toString()));
        assertSame(Units.CANDELA, mapper.fromSymbol("cd"));
        assertSame(Units.CELSIUS, mapper.fromSymbol("°C"));
        assertSame(Units.COULOMB, mapper.fromSymbol("C"));
        assertSame(Units.CUBIC_METRE, mapper.fromSymbol("㎥"));
        assertSame(Units.DAY, mapper.fromSymbol("day"));
        assertSame(Units.FARAD, mapper.fromSymbol("F"));
        assertSame(Units.GRAM, mapper.fromSymbol("g"));
        assertSame(Units.GRAY, mapper.fromSymbol("Gy"));
        assertSame(Units.HENRY, mapper.fromSymbol("H"));
        assertSame(Units.HERTZ, mapper.fromSymbol("Hz"));
        assertSame(Units.HOUR, mapper.fromSymbol("h"));
        assertSame(Units.JOULE, mapper.fromSymbol("J"));
        assertSame(Units.KATAL, mapper.fromSymbol("kat"));
        assertSame(Units.KELVIN, mapper.fromSymbol("K"));
        assertSame(Units.GRAM, mapper.fromSymbol("g"));
        assertSame(Units.KILOMETRE_PER_HOUR, mapper.fromSymbol("km/h"));
        assertSame(Units.LITRE, mapper.fromSymbol("l"));
        assertSame(Units.LUMEN, mapper.fromSymbol("lm"));
        assertSame(Units.LUX, mapper.fromSymbol("lx"));
        assertSame(Units.METRE, mapper.fromSymbol("m"));
        assertSame(Units.METRE_PER_SECOND, mapper.fromSymbol("m/s"));
        assertSame(Units.MINUTE, mapper.fromSymbol("min"));
        assertSame(Units.MOLE, mapper.fromSymbol("mol"));
        assertSame(Units.NEWTON, mapper.fromSymbol("N"));
        assertSame(Units.OHM, mapper.fromSymbol("Ω"));
        assertSame(Units.PERCENT, mapper.fromSymbol("%"));
        assertSame(Units.RADIAN, mapper.fromSymbol("rad"));
        assertSame(Units.SECOND, mapper.fromSymbol("s"));
        assertSame(Units.SIEMENS, mapper.fromSymbol("S"));
        assertSame(Units.SIEVERT, mapper.fromSymbol("Sv"));
        assertSame(Units.SQUARE_METRE, mapper.fromSymbol("m²"));
        assertSame(Units.STERADIAN, mapper.fromSymbol("sr"));
        assertSame(Units.TESLA, mapper.fromSymbol("T"));
        assertSame(Units.VOLT, mapper.fromSymbol("V"));
        assertSame(Units.WATT, mapper.fromSymbol("W"));
        assertSame(Units.WEBER, mapper.fromSymbol("Wb"));
        assertSame(Units.WEEK, mapper.fromSymbol("week"));
        assertSame(Units.YEAR, mapper.fromSymbol("year"));

        // Custom values tests
        assertEquals(new BaseUnit<AmountOfSubstance>("each", QuantityDimension.AMOUNT_OF_SUBSTANCE), mapper.fromSymbol("each"));
        assertEquals(MetricPrefix.HECTO(Units.LITRE), mapper.fromSymbol("hl"));
    }

    @Test
    public void testFromEntity_ReturnsPojo_WhenEntityIsNotNull() {
        mapper = spy(mapper);
        doReturn(Units.GRAM).when(mapper).fromSymbol("TEST_SYMBOL");

        Unit<?> unit = mapper.fromEntity(new UnitEntity("TEST_SYMBOL"));
        assertSame(Units.GRAM, unit);
    }

    @Test
    public void testFromEntity_ReturnsNull_WhenEntityIsNull() {
        assertNull(mapper.fromEntity(null));
    }

    @Test
    public void testFromDto_ReturnsPojo_WhenDtoIsNotNull() {
        mapper = spy(mapper);
        doReturn(Units.GRAM).when(mapper).fromSymbol("TEST_SYMBOL");

        Unit<?> unit = mapper.fromDto(new UnitDto("TEST_SYMBOL"));
        assertSame(Units.GRAM, unit);
    }

    @Test
    public void testFromDto_ReturnsNull_WhenDtoIsNull() {
        assertNull(mapper.fromDto(null));
    }

    @Test
    public void testToSymbol_ReturnsNull_WhenArgIsNull() {
        assertNull(mapper.toSymbol(null));
    }

    @Test
    public void testToSymbol_ReturnsSymbol_WhenUnitIsNotNull() {
        assertEquals(Units.GRAM.toString(), mapper.toSymbol(Units.GRAM));
        assertEquals("g", mapper.toSymbol(Units.GRAM));
    }

    @Test
    public void testToDto_ReturnsNull_WhenPojoIsNull() {
        assertNull(mapper.toDto((Unit<?>)null));
    }

    @Test
    public void testToDto_ReturnsDto_WhenPojoIsNotNull() {
        assertEquals(new UnitDto("g"), mapper.toDto(Units.GRAM));
    }

    @Test
    public void testToDto_ReturnsNull_WhenEntityIsNull() {
        assertNull(mapper.toDto((UnitEntity)null));
    }

    @Test
    public void testToDto_ReturnsDto_WhenEntityIsNotNull() {
        assertEquals(new UnitDto("g"), mapper.toDto(new UnitEntity("g")));
    }
}
