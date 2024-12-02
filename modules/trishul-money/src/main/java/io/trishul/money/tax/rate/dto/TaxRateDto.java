package io.trishul.money.tax.rate.dto;

import java.math.BigDecimal;

import io.trishul.model.base.dto.BaseDto;

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

    public void setValue(BigDecimal value) {
        this.value = value;
    }
}
