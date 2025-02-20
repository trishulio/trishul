package io.trishul.money;

import java.math.BigDecimal;
import io.trishul.model.base.entity.BaseEntity;
import io.trishul.money.currency.model.Currency;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import io.trishul.money.serialize.Register;

@Embeddable
public class MoneyEntity extends BaseEntity {
  static {
    Register.init();
  }

  public static final String FIELD_CURRENCY = "currency";
  public static final String FIELD_AMOUNT = "amount";

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "currency_id", referencedColumnName = "numeric_code")
  private Currency currency;

  @Column(name = "amount", precision = 20, scale = 4)
  private BigDecimal amount;

  public MoneyEntity() {}

  public MoneyEntity(Currency currency, BigDecimal amount) {
    setCurrency(currency);
    setAmount(amount);
  }

  public Currency getCurrency() {
    return currency == null ? null : currency.deepClone();
  }

  public MoneyEntity setCurrency(Currency currency) {
    this.currency = currency == null ? null : currency.deepClone();
    return this;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public MoneyEntity setAmount(BigDecimal amount) {
    this.amount = amount;
    return this;
  }
}
