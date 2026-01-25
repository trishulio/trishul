package io.trishul.quantity.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import io.trishul.quantity.unit.accessor.BaseQuantityUnitAccessor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tec.uom.se.quantity.Quantities;

class IncompatibleQuantityUnitExceptionTest {
  private Exception ex;

  @BeforeEach
  void init() {
    ex = new IncompatibleQuantityUnitException();
  }

  @Test
  void testNoArgConstructor() {
    assertNull(ex.getMessage());
    assertNull(ex.getCause());
  }

  @Test
  void testConstructor_String_SetsMessage() {
    ex = new IncompatibleQuantityUnitException("Message");

    assertEquals("Message", ex.getMessage());
  }

  @Test
  void testValidateUnit_ThrowsException_WhenUnitsAreIncompatible() {
    BaseQuantityUnitAccessor<?> accessor = mock(BaseQuantityUnitAccessor.class);
    doReturn(SupportedUnits.LITRE).when(accessor).getBaseQuantityUnit();

    IncompatibleQuantityUnitException exception = assertThrows(
        IncompatibleQuantityUnitException.class, () -> IncompatibleQuantityUnitException
            .validateUnit(accessor, Quantities.getQuantity("10 g")));
  }

  @Test
  void testValidateUnit_DoesNothing_WhenUnitsAreNotIncompatible() {
    BaseQuantityUnitAccessor<?> accessor = mock(BaseQuantityUnitAccessor.class);
    doReturn(SupportedUnits.GRAM).when(accessor).getBaseQuantityUnit();

    IncompatibleQuantityUnitException.validateUnit(accessor, Quantities.getQuantity("10 g"));
  }

  @Test
  void testValidateCompatibleQuantities_ThrowsException_WhenUnitsAreIncompatible() {
    IncompatibleQuantityUnitException exception
        = assertThrows(IncompatibleQuantityUnitException.class,
            () -> IncompatibleQuantityUnitException.validateCompatibleQuantities(
                Quantities.getQuantity("10 l"), Quantities.getQuantity("10 g")));
  }

  @Test
  void testValidateCompatibleQuantities_DoesNothing_WhenUnitsAreNotIncompatible() {
    IncompatibleQuantityUnitException.validateCompatibleQuantities(Quantities.getQuantity("10 g"),
        Quantities.getQuantity("10 g"));
  }

  @Test
  void testValidateExpectedUnit_ThrowsException_WhenUnitIsUnexpected() {
    IncompatibleQuantityUnitException exception
        = assertThrows(IncompatibleQuantityUnitException.class,
            () -> IncompatibleQuantityUnitException.validateExpectedUnit(SupportedUnits.GRAM,
                Quantities.getQuantity(100, SupportedUnits.LITRE)));
  }

  @Test
  void testValidateExpectedUnit_DoesNothing_WhenUnitIsExpected() {
    IncompatibleQuantityUnitException.validateExpectedUnit(SupportedUnits.GRAM,
        Quantities.getQuantity(100, SupportedUnits.GRAM));
  }
}
