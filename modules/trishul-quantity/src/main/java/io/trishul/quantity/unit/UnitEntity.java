package io.trishul.quantity.unit;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.trishul.model.base.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity(name = "qty_unit")
@JsonIgnoreProperties({"hibernateLazyInitializer"})
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

  public UnitEntity setName(String name) {
    this.name = name;
    return this;
  }

  public String getSymbol() {
    return symbol;
  }

  public UnitEntity setSymbol(String symbol) {
    this.symbol = symbol;
    return this;
  }

  public UnitEntity getBaseUnitEntity() {
    return baseUnitEntity;
  }

  public UnitEntity setBaseUnitEntity(UnitEntity baseUnitEntity) {
    this.baseUnitEntity = baseUnitEntity;
    return this;
  }
}
