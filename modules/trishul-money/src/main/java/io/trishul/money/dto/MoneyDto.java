package io.trishul.money.dto;

import java.math.BigDecimal;
import io.trishul.model.base.dto.BaseDto;

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

  public MoneyDto setCurrency(String currency) {
    this.currency = currency;
    return this;
  }

  public BigDecimal getAmount() {
    BigDecimal amt = null;
    if (this.amount != null) {
      amt = new BigDecimal(this.amount.stripTrailingZeros().toPlainString());
    }
    return amt;
  }

  public MoneyDto setAmount(BigDecimal amount) {
    this.amount = amount;
    return this;
  }
}
