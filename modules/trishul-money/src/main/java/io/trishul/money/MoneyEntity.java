package io.trishul.money;

import io.trishul.model.base.entity.BaseEntity;
import io.trishul.money.currency.model.Currency;
import java.math.BigDecimal;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Embeddable
public class MoneyEntity extends BaseEntity {
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
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
