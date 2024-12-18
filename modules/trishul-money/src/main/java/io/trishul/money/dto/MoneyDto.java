package io.trishul.money.dto;

import io.trishul.model.base.dto.BaseDto;
import java.math.BigDecimal;

public class MoneyDto extends BaseDto {
    public static final String ATTR_CURRENCY = "currency";
    public static final String ATTR_AMOUNT = "amount";

    private String currency;
    private BigDecimal amount;

    public MoneyDto() {
        this(null, null);
    }

    public MoneyDto(String currency, BigDecimal amount) {
        setCurrency(currency);
        setAmount(amount);
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigDecimal getAmount() {
        BigDecimal amount = null;
        if (this.amount != null) {
            amount = new BigDecimal(this.amount.stripTrailingZeros().toPlainString());
        }
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
