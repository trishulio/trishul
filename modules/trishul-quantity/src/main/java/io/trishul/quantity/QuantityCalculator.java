package io.trishul.quantity;

import io.trishul.quantity.unit.SupportedUnits;
import io.trishul.quantity.unit.accessor.BaseQuantityUnitAccessor;
import javax.measure.Quantity;
import javax.measure.Unit;
import javax.measure.quantity.AmountOfSubstance;
import javax.measure.quantity.Mass;
import javax.measure.quantity.Volume;
import tec.uom.se.quantity.Quantities;

public class QuantityCalculator {
    public static final QuantityCalculator INSTANCE = new QuantityCalculator();

    private QuantityCalculator() {}
    ;

    @SuppressWarnings("unchecked")
    public Quantity<?> subtract(Quantity<?> q1, Quantity<?> q2) {
        if (SupportedUnits.DEFAULT_MASS.isCompatible(q1.getUnit())) {
            Quantity<Mass> mass1 = (Quantity<Mass>) q1;
            Quantity<Mass> mass2 = (Quantity<Mass>) q2;

            return mass1.subtract(mass2);
        } else if (SupportedUnits.DEFAULT_VOLUME.isCompatible(q1.getUnit())) {
            Quantity<Volume> volume1 = (Quantity<Volume>) q1;
            Quantity<Volume> volume2 = (Quantity<Volume>) q2;

            return volume1.subtract(volume2);
        } else if (SupportedUnits.EACH.isCompatible(q1.getUnit())) {
            Quantity<AmountOfSubstance> amount1 = (Quantity<AmountOfSubstance>) q1;
            Quantity<AmountOfSubstance> amount2 = (Quantity<AmountOfSubstance>) q2;

            return amount1.subtract(amount2);
        } else {
            throw new RuntimeException("Unsupported quantity type");
        }
    }

    @SuppressWarnings("unchecked")
    public Quantity<?> add(Quantity<?> q1, Quantity<?> q2) {
        if (SupportedUnits.DEFAULT_MASS.isCompatible(q1.getUnit())) {
            Quantity<Mass> mass1 = (Quantity<Mass>) q1;
            Quantity<Mass> mass2 = (Quantity<Mass>) q2;

            return mass1.add(mass2);
        } else if (SupportedUnits.DEFAULT_VOLUME.isCompatible(q1.getUnit())) {
            Quantity<Volume> volume1 = (Quantity<Volume>) q1;
            Quantity<Volume> volume2 = (Quantity<Volume>) q2;

            return volume1.add(volume2);
        } else if (SupportedUnits.EACH.isCompatible(q1.getUnit())) {
            Quantity<AmountOfSubstance> amount1 = (Quantity<AmountOfSubstance>) q1;
            Quantity<AmountOfSubstance> amount2 = (Quantity<AmountOfSubstance>) q2;

            return amount1.add(amount2);
        } else {
            throw new RuntimeException("Unsupported quantity type");
        }
    }

    public Quantity<?> toSystemQuantityValueWithDisplayUnit(Quantity<?> quantityWithDisplayUnit) {
        Quantity<?> systemUnitValueWithDisplayUnit = null;

        if (quantityWithDisplayUnit != null) {
            Unit<?> displayUnit = quantityWithDisplayUnit.getUnit();
            Number valueInSystemUnit = quantityWithDisplayUnit.toSystemUnit().getValue();

            systemUnitValueWithDisplayUnit = Quantities.getQuantity(valueInSystemUnit, displayUnit);
        }

        return systemUnitValueWithDisplayUnit;
    }

    public <T extends Quantity<T>> Quantity<T> fromSystemQuantityValueWithDisplayUnit(
            Quantity<T> quantityWithDisplayUnit) {
        Quantity<T> qtyInDisplayUnit = null;

        if (quantityWithDisplayUnit != null) {
            Unit<T> displayUnit = quantityWithDisplayUnit.getUnit();

            Number valueInSystemUnit = quantityWithDisplayUnit.getValue();
            Quantity<T> qtyInSystemUnit =
                    Quantities.getQuantity(valueInSystemUnit, displayUnit.getSystemUnit());

            Number valueInDisplayUnit = qtyInSystemUnit.to(displayUnit).getValue();

            qtyInDisplayUnit = Quantities.getQuantity(valueInDisplayUnit, displayUnit);
        }

        return qtyInDisplayUnit;
    }

    public boolean isCompatibleQtyForUnitAccessor(
            Quantity<?> quantity, BaseQuantityUnitAccessor unitAccessor) {
        boolean isCompatible = true;

        if (unitAccessor != null
                && unitAccessor.getBaseQuantityUnit() != null
                && quantity != null) {
            isCompatible = unitAccessor.getBaseQuantityUnit().isCompatible(quantity.getUnit());
        }

        return isCompatible;
    }

    public boolean areCompatibleQuantities(Quantity<?> quantity1, Quantity<?> quantity2) {
        boolean areCompatible = true;

        Unit<?> unit1 = null;
        Unit<?> unit2 = null;

        if (quantity1 != null) {
            unit1 = quantity1.getUnit();
        }

        if (quantity2 != null) {
            unit2 = quantity2.getUnit();
        }

        if (unit1 != null && unit2 != null) {
            areCompatible = unit1.isCompatible(unit2);
        }

        return areCompatible;
    }

    public boolean isExpectedUnit(Unit<?> expectedUnit, Quantity<?> quantity) {
        boolean isExpectedUnit = false;

        Unit<?> quantityUnit = quantity != null ? quantity.getUnit() : null;
        if (quantityUnit == expectedUnit) {
            isExpectedUnit = true;
        }

        return isExpectedUnit;
    }
}
