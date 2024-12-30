package io.trishul.quantity.unit.accessor;

import javax.measure.Unit;

public interface BaseQuantityUnitAccessor<T extends BaseQuantityUnitAccessor<T>> {
  Unit<?> getBaseQuantityUnit();

  T setBaseQuantityUnit(Unit<?> baseQuantityUnit);
}
