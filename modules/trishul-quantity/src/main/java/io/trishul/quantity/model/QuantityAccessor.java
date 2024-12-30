package io.trishul.quantity.model;

import javax.measure.Quantity;

public interface QuantityAccessor<T extends QuantityAccessor<T>> {
  final String ATTR_QUANTITY = "quantity";

  Quantity<?> getQuantity();

  T setQuantity(Quantity<?> quantity);
}
