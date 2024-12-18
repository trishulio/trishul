package io.trishul.money.amount.model;

import io.trishul.model.base.entity.BaseEntity;
import io.trishul.money.MoneyEntity;
import io.trishul.money.MoneyMapper;
import io.trishul.money.tax.amount.TaxAmount;
import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.JoinColumn;
import javax.persistence.PrePersist;
import org.joda.money.Money;

@Embeddable
public class Amount extends BaseEntity {
    public static final String FIELD_TOTAL = "total";
    public static final String FIELD_SUB_TOTAL = "subTotal";
    public static final String FIELD_TAX_AMOUNT = "taxAmount";

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "amount", column = @Column(name = "total_amount"))
    })
    @AssociationOverrides({
        @AssociationOverride(
                name = "currency",
                joinColumns =
                        @JoinColumn(
                                name = "total_amount_currency_code",
                                referencedColumnName = "numeric_code"))
    })
    private MoneyEntity total;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "amount", column = @Column(name = "sub_total_amount"))
    })
    @AssociationOverrides({
        @AssociationOverride(
                name = "currency",
                joinColumns =
                        @JoinColumn(
                                name = "sub_total_amount_currency_code",
                                referencedColumnName = "numeric_code"))
    })
    private MoneyEntity subTotal;

    @Embedded private TaxAmount taxAmount;

    public Amount() {
        super();
    }

    public Amount(Money subTotal) {
        this();
        setSubTotal(subTotal);
    }

    public Amount(Money subTotal, TaxAmount taxAmount) {
        this(subTotal);
        setTaxAmount(taxAmount);
    }

    public Money getTotal() {
        setTotal();
        return MoneyMapper.INSTANCE.fromEntity(total);
    }

    @PrePersist
    public void setTotal() {
        Money total = null;

        Money subTotal = getSubTotal();
        TaxAmount taxAmount = getTaxAmount();

        Money totalTaxAmount = null;
        if (taxAmount != null) {
            totalTaxAmount = taxAmount.getTotalTaxAmount();
        }

        if (subTotal != null && totalTaxAmount != null) {
            total = subTotal.plus(totalTaxAmount);
        }

        this.total = MoneyMapper.INSTANCE.toEntity(total);
    }

    public Money getSubTotal() {
        return MoneyMapper.INSTANCE.fromEntity(subTotal);
    }

    public void setSubTotal(Money subTotal) {
        this.subTotal = MoneyMapper.INSTANCE.toEntity(subTotal);
    }

    public TaxAmount getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(TaxAmount taxAmount) {
        this.taxAmount = taxAmount;
    }
}
