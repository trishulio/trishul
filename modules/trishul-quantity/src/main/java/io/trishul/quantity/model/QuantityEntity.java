package io.trishul.quantity.model;

import java.math.BigDecimal;
import io.trishul.model.base.entity.BaseEntity;
import io.trishul.quantity.unit.UnitEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import io.trishul.quantity.serialize.Register;

@Embeddable
public class QuantityEntity extends BaseEntity {
  static {
    Register.init();
  }

  public static final String FIELD_UNIT = "unit";
  public static final String FIELD_VALUE = "value";

  @ManyToOne
  @JoinColumn(name = "unit_symbol", referencedColumnName = "symbol")
  private UnitEntity unit;

  @Column(name = "value", precision = 20, scale = 4)
  private BigDecimal value;

  public QuantityEntity() {}

  public QuantityEntity(UnitEntity unit, BigDecimal value) {
    this();
    setUnit(unit);
    setValue(value);
  }

  public UnitEntity getUnit() {
    return unit;
  }

  public QuantityEntity setUnit(UnitEntity unit) {
    this.unit = unit;
    return this;
  }

  public BigDecimal getValue() {
    return value;
  }

  public QuantityEntity setValue(BigDecimal value) {
    this.value = value;
    return this;
  }
}
