package io.trishul.money.tax.amount;

import java.util.ArrayList;
import java.util.List;
import org.joda.money.Money;
import io.trishul.model.base.entity.BaseEntity;
import io.trishul.money.MoneyCalculator;
import io.trishul.money.MoneyEntity;
import io.trishul.money.MoneyMapper;
import jakarta.persistence.AssociationOverride;
import jakarta.persistence.AssociationOverrides;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.PrePersist;

@Embeddable
public class TaxAmount extends BaseEntity {
  public static final String FIELD_PST_AMOUNT = "pstAmount";
  public static final String FIELD_GST_AMOUNT = "gstAmount";
  public static final String FIELD_HST_AMOUNT = "hstAmount";
  public static final String FIELD_TOTAL_TAX_AMOUNT = "totalTaxAmount";

  @Embedded
  @AttributeOverrides({@AttributeOverride(name = "amount", column = @Column(name = "pst_amount"))})
  @AssociationOverrides({@AssociationOverride(name = "currency",
      joinColumns = @JoinColumn(name = "pst_amount_currency_code",
          referencedColumnName = "numeric_code"))})
  private MoneyEntity pstAmount;

  @Embedded
  @AttributeOverrides({@AttributeOverride(name = "amount", column = @Column(name = "gst_amount"))})
  @AssociationOverrides({@AssociationOverride(name = "currency",
      joinColumns = @JoinColumn(name = "gst_amount_currency_code",
          referencedColumnName = "numeric_code"))})
  private MoneyEntity gstAmount;

  @Embedded
  @AttributeOverrides({@AttributeOverride(name = "amount", column = @Column(name = "hst_amount"))})
  @AssociationOverrides({@AssociationOverride(name = "currency",
      joinColumns = @JoinColumn(name = "hst_amount_currency_code",
          referencedColumnName = "numeric_code"))})
  private MoneyEntity hstAmount;

  @Embedded
  @AttributeOverrides({
      @AttributeOverride(name = "amount", column = @Column(name = "total_tax_amount"))})
  @AssociationOverrides({@AssociationOverride(name = "currency",
      joinColumns = @JoinColumn(name = "total_tax_amount_currency_code",
          referencedColumnName = "numeric_code"))})
  private MoneyEntity totalTaxAmount;

  public TaxAmount() {
    super();
  }

  public TaxAmount(Money pstAmount, Money gstAmount, Money hstAmount) {
    this();
    setPstAmount(pstAmount);
    setGstAmount(gstAmount);
    setHstAmount(hstAmount);
  }

  public TaxAmount(Money pstAmount, Money gstAmount) {
    this(pstAmount, gstAmount, null);
  }

  public TaxAmount(Money hstAmount) {
    this(null, null, hstAmount);
  }

  public Money getPstAmount() {
    return MoneyMapper.INSTANCE.fromEntity(pstAmount);
  }

  public Money getGstAmount() {
    return MoneyMapper.INSTANCE.fromEntity(gstAmount);
  }

  public Money getHstAmount() {
    return MoneyMapper.INSTANCE.fromEntity(hstAmount);
  }

  public final TaxAmount setPstAmount(Money pstAmount) {
    this.pstAmount = MoneyMapper.INSTANCE.toEntity(pstAmount);
    setTotalTaxAmount();
    return this;
  }

  public final TaxAmount setGstAmount(Money gstAmount) {
    this.gstAmount = MoneyMapper.INSTANCE.toEntity(gstAmount);
    setTotalTaxAmount();
    return this;
  }

  public final TaxAmount setHstAmount(Money hstAmount) {
    this.hstAmount = MoneyMapper.INSTANCE.toEntity(hstAmount);
    setTotalTaxAmount();
    return this;
  }

  public Money getTotalTaxAmount() {
    return MoneyMapper.INSTANCE.fromEntity(totalTaxAmount);
  }

  @PrePersist
  public final void setTotalTaxAmount() {
    List<Money> amounts = new ArrayList<>(3);
    amounts.add(getPstAmount());
    amounts.add(getGstAmount());
    amounts.add(getHstAmount());
    Money totalTaxAmt = MoneyCalculator.INSTANCE.totalAmount(amounts);

    this.totalTaxAmount = MoneyMapper.INSTANCE.toEntity(totalTaxAmt);
  }
}
