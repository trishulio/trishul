package io.trishul.quantity.model.dto;

import java.math.BigDecimal;

import io.trishul.model.base.dto.BaseDto;

public class QuantityDto extends BaseDto {
    public static final String ATTR_SYMBOL = "symbol";
    public static final String ATTR_VALUE = "value";

    private String symbol;
    private BigDecimal value;

    public QuantityDto() {
        this(null, null);
    }

    public QuantityDto(String symbol, BigDecimal value) {
        setSymbol(symbol);
        setValue(value);
    }

    public Number getValue() {
        return this.value;
    }

    public void setValue(BigDecimal value) {
        if (value != null) {
            value = new BigDecimal(value.stripTrailingZeros().toPlainString());
        }

        this.value = value;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        if (symbol != null) {
            symbol = symbol.toLowerCase();
        }
        this.symbol = symbol;
    }
}
