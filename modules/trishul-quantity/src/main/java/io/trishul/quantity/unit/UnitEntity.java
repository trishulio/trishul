package io.trishul.quantity.unit;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.trishul.model.base.entity.BaseEntity;

@Entity(name = "qty_unit")
@JsonIgnoreProperties({ "hibernateLazyInitializer" })
public class UnitEntity extends BaseEntity {
    public static final String FIELD_SYMBOL = "symbol";
    public static final String FIELD_NAME = "name";
    public static final String FIELD_BASE_UNIT_ENTITY = "baseUnitEntity";

    @Id
    @Column(name = "symbol", unique = true, updatable = false, length = 4)
    private String symbol;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "base_unit_symbol", referencedColumnName = "symbol")
    @JsonBackReference
    private UnitEntity baseUnitEntity;

    public UnitEntity() {
        this(null, null, null);
    }

    public UnitEntity(String symbol) {
        this(symbol, null, null);
    }

    public UnitEntity(String symbol, String name) {
        this(symbol, name, null);
    }

    public UnitEntity(String symbol, String name, UnitEntity baseUnitEntity) {
        setName(name);
        setSymbol(symbol);
        setBaseUnitEntity(baseUnitEntity);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public UnitEntity getBaseUnitEntity() {
        return baseUnitEntity;
    }

    public void setBaseUnitEntity(UnitEntity baseUnitEntity) {
        this.baseUnitEntity = baseUnitEntity;
    }
}
