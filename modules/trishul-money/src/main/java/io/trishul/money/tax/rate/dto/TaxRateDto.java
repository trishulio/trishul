package io.trishul.money.tax.rate.dto;

import io.trishul.model.base.dto.BaseDto;
import java.math.BigDecimal;

public class TaxRateDto extends BaseDto {
    private BigDecimal value;

    public TaxRateDto() {
        super();
    }

    public TaxRateDto(BigDecimal value) {
        this();
        setValue(value);
    }

    public BigDecimal getValue() {
        return value;
    }

    public final void setValue(BigDecimal value) {
        this.value = value;
    }
}
