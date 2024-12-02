package io.trishul.quantity.unit.accessor;

import javax.measure.Unit;

public interface BaseQuantityUnitAccessor {
    Unit<?> getBaseQuantityUnit();

    void setBaseQuantityUnit(Unit<?> baseQuantityUnit);
}
