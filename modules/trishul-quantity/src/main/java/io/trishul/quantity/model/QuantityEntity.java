package io.trishul.quantity.model;

import io.trishul.model.base.entity.BaseEntity;
import io.trishul.quantity.unit.UnitEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.math.BigDecimal;

@Embeddable
public class QuantityEntity extends BaseEntity {
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

    public final void setUnit(UnitEntity unit) {
        this.unit = unit;
    }

    public BigDecimal getValue() {
        return value;
    }

    public final void setValue(BigDecimal value) {
        this.value = value;
    }
}
