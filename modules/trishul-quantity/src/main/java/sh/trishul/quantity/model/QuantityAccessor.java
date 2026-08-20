package sh.trishul.quantity.model;

import javax.measure.Quantity;

public interface QuantityAccessor<T extends QuantityAccessor<T>> {
  String ATTR_QUANTITY = "quantity";

  Quantity<?> getQuantity();

  T setQuantity(Quantity<?> quantity);
}
