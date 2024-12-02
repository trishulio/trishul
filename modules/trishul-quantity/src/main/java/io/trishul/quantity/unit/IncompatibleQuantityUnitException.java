package io.trishul.quantity.unit;

import javax.measure.Quantity;
import javax.measure.Unit;

import io.trishul.quantity.QuantityCalculator;
import io.trishul.quantity.unit.accessor.BaseQuantityUnitAccessor;

public class IncompatibleQuantityUnitException extends IllegalArgumentException {
    private static final long serialVersionUID = 5114542775018005333L;

    public IncompatibleQuantityUnitException() {
        super();
    }

    public IncompatibleQuantityUnitException(String message) {
        super(message);
    }

    public static void validateUnit(BaseQuantityUnitAccessor unitAccessor, Quantity<?> quantity) {
        if (!QuantityCalculator.INSTANCE.isCompatibleQtyForUnitAccessor(quantity, unitAccessor)) {
            Unit<?> qtyUnit = null;
            Unit<?> accessorUnit = null;

            if (quantity != null) {
                qtyUnit = quantity.getUnit();
            }

            if(unitAccessor != null) {
                accessorUnit = unitAccessor.getBaseQuantityUnit();
            }

            String error = String.format("Quantity Unit: %s is not compatible with accessor unit: %s", qtyUnit, accessorUnit);

            throw new IncompatibleQuantityUnitException(error);
        }
    }

    public static void validateCompatibleQuantities(Quantity<?> quantity1, Quantity<?> quantity2) {
        if (!QuantityCalculator.INSTANCE.areCompatibleQuantities(quantity1, quantity2)) {
            Unit<?> unit1 = null;
            Unit<?> unit2 = null;
            if (quantity1 != null) {
                unit1 = quantity1.getUnit();
            }
            if (quantity2 != null) {
                unit2 = quantity2.getUnit();
            }

            String error = String.format("Quantity with units %s and %s are not compatible", unit1, unit2);

            throw new IncompatibleQuantityUnitException(error);
        }
    }

    public static void validateExpectedUnit(Unit<?> expectedUnit, Quantity<?> quantity) {
        if (!QuantityCalculator.INSTANCE.isExpectedUnit(expectedUnit, quantity)) {
            Unit<?> quantityUnit = quantity != null ? quantity.getUnit() : null;
            String error = String.format("Quantity Unit: %s does not match expected unit: %s", quantityUnit, expectedUnit);

            throw new IncompatibleQuantityUnitException(error);
        }
    }
}
