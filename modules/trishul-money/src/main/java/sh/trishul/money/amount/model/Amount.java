package sh.trishul.money.amount.model;

import jakarta.persistence.AssociationOverride;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.PrePersist;
import org.joda.money.Money;
import sh.trishul.model.base.entity.BaseEntity;
import sh.trishul.money.MoneyEntity;
import sh.trishul.money.MoneyMapper;
import sh.trishul.money.tax.amount.TaxAmount;

@Embeddable
public class Amount extends BaseEntity {
  public static final String FIELD_TOTAL = "total";
  public static final String FIELD_SUB_TOTAL = "subTotal";
  public static final String FIELD_TAX_AMOUNT = "taxAmount";

  @Embedded
  @AttributeOverride(name = "amount", column = @Column(name = "total_amount"))
  @AssociationOverride(name = "currency",
      joinColumns = @JoinColumn(name = "total_amount_currency_code",
          referencedColumnName = "numeric_code"))
  private MoneyEntity total;

  @Embedded
  @AttributeOverride(name = "amount", column = @Column(name = "sub_total_amount"))
  @AssociationOverride(name = "currency",
      joinColumns = @JoinColumn(name = "sub_total_amount_currency_code",
          referencedColumnName = "numeric_code"))
  private MoneyEntity subTotal;

  @Embedded
  private TaxAmount taxAmount;

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

  public Amount setSubTotal(Money subTotal) {
    this.subTotal = MoneyMapper.INSTANCE.toEntity(subTotal);
    return this;
  }

  public TaxAmount getTaxAmount() {
    return taxAmount;
  }

  public Amount setTaxAmount(TaxAmount taxAmount) {
    this.taxAmount = taxAmount;
    return this;
  }
}
