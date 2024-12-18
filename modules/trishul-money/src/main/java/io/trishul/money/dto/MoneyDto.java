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

    public final void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigDecimal getAmount() {
        BigDecimal amt = null;
        if (this.amount != null) {
            amt = new BigDecimal(this.amount.stripTrailingZeros().toPlainString());
        }
        return amt;
    }

    public final void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
