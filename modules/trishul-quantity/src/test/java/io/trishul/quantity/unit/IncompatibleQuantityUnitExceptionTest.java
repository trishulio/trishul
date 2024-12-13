package io.trishul.quantity.unit;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.trishul.quantity.unit.accessor.BaseQuantityUnitAccessor;
import tec.uom.se.quantity.Quantities;

public class IncompatibleQuantityUnitExceptionTest {
    private Exception ex;

    @BeforeEach
    public void init() {
        ex = new IncompatibleQuantityUnitException();
    }

    @Test
    public void testNoArgConstructor() {
        assertNull(ex.getMessage());
        assertNull(ex.getCause());
    }

    @Test
    public void testConstructor_String_SetsMessage() {
        ex = new IncompatibleQuantityUnitException("Message");

        assertEquals("Message", ex.getMessage());
    }

    @Test
    public void testValidateUnit_ThrowsException_WhenUnitsAreIncompatible() {
        BaseQuantityUnitAccessor accessor = mock(BaseQuantityUnitAccessor.class);
        doReturn(SupportedUnits.LITRE).when(accessor).getBaseQuantityUnit();

        assertThrows(IncompatibleQuantityUnitException.class, () -> IncompatibleQuantityUnitException.validateUnit(accessor, Quantities.getQuantity("10 g")));
    }

    @Test
    public void testValidateUnit_DoesNothing_WhenUnitsAreNotIncompatible() {
        BaseQuantityUnitAccessor accessor = mock(BaseQuantityUnitAccessor.class);
        doReturn(SupportedUnits.GRAM).when(accessor).getBaseQuantityUnit();

        IncompatibleQuantityUnitException.validateUnit(accessor, Quantities.getQuantity("10 g"));
    }

    @Test
    public void testValidateCompatibleQuantities_ThrowsException_WhenUnitsAreIncompatible() {
        assertThrows(IncompatibleQuantityUnitException.class, () -> IncompatibleQuantityUnitException.validateCompatibleQuantities(Quantities.getQuantity("10 l"), Quantities.getQuantity("10 g")));
    }

    @Test
    public void testValidateCompatibleQuantities_DoesNothing_WhenUnitsAreNotIncompatible() {
        IncompatibleQuantityUnitException.validateCompatibleQuantities(Quantities.getQuantity("10 g"), Quantities.getQuantity("10 g"));
    }

    @Test
    public void testValidateExpectedUnit_ThrowsException_WhenUnitIsUnexpected() {
        assertThrows(IncompatibleQuantityUnitException.class, () -> IncompatibleQuantityUnitException.validateExpectedUnit(SupportedUnits.GRAM, Quantities.getQuantity(100, SupportedUnits.LITRE)));
    }

    @Test
    public void testValidateExpectedUnit_DoesNothing_WhenUnitIsExpected() {
        IncompatibleQuantityUnitException.validateExpectedUnit(SupportedUnits.GRAM, Quantities.getQuantity(100, SupportedUnits.GRAM));
    }
}
