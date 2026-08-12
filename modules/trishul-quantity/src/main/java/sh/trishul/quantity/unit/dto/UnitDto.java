package sh.trishul.quantity.unit.dto;

import sh.trishul.model.base.dto.BaseDto;

public class UnitDto extends BaseDto {
  public static final String ATTR_SYMBOL = "symbol";

  private String symbol;

  public UnitDto() {}

  public UnitDto(String symbol) {
    this.symbol = symbol;
  }

  public String getSymbol() {
    return symbol;
  }

  public UnitDto setSymbol(String symbol) {
    this.symbol = symbol;
    return this;
  }
}
