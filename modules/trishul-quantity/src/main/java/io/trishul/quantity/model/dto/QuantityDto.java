package io.trishul.quantity.model.dto;

import io.trishul.model.base.dto.BaseDto;
import java.math.BigDecimal;

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

    public final void setValue(BigDecimal value) {
        if (value != null) {
            value = new BigDecimal(value.stripTrailingZeros().toPlainString());
        }

        this.value = value;
    }

    public String getSymbol() {
        return symbol;
    }

    public final void setSymbol(String symbol) {
        if (symbol != null) {
            symbol = symbol.toLowerCase();
        }
        this.symbol = symbol;
    }
}
