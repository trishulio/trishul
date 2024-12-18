package io.trishul.quantity.model;

import io.trishul.model.base.entity.BaseEntity;
import io.trishul.quantity.unit.UnitEntity;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

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

    public void setUnit(UnitEntity unit) {
        this.unit = unit;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }
}
