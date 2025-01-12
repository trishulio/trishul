package io.trishul.money.tax.model;

import io.trishul.model.base.entity.BaseEntity;
import io.trishul.model.validator.Validator;
import io.trishul.money.tax.rate.TaxRate;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;

@Embeddable
public class Tax extends BaseEntity {
  public static final String FIELD_GST_RATE = "gstRate";
  public static final String FIELD_HST_RATE = "hstRate";
  public static final String FIELD_PST_RATE = "pstRate";

  @Embedded
  @AttributeOverrides({@AttributeOverride(name = "value", column = @Column(name = "gst_rate"))})
  private TaxRate gstRate;

  @Embedded
  @AttributeOverrides({@AttributeOverride(name = "value", column = @Column(name = "pst_rate"))})
  private TaxRate pstRate;

  @Embedded
  @AttributeOverrides({@AttributeOverride(name = "value", column = @Column(name = "hst_rate"))})
  private TaxRate hstRate;

  public Tax() {
    super();
  }

  public Tax(TaxRate hstRate) {
    setHstRate(hstRate);
  }

  public Tax(TaxRate pstRate, TaxRate gstRate) {
    this();
    setPstRate(pstRate);
    setGstRate(gstRate);
  }

  public TaxRate getGstRate() {
    return gstRate;
  }

  public Tax setGstRate(TaxRate gstRate) {
    Validator.assertion(
        !TaxRate.isSet(gstRate) || (TaxRate.isSet(gstRate) && !TaxRate.isSet(getHstRate())),
        IllegalArgumentException.class, "Cannot set GST when HST is present. Remove HST");
    this.gstRate = gstRate;
    return this;
  }

  public TaxRate getPstRate() {
    return pstRate;
  }

  public Tax setPstRate(TaxRate pstRate) {
    Validator.assertion(
        !TaxRate.isSet(pstRate) || (TaxRate.isSet(pstRate) && !TaxRate.isSet(getHstRate())),
        IllegalArgumentException.class, "Cannot set PST when HST is present. Remove HST");
    this.pstRate = pstRate;
    return this;
  }

  public TaxRate getHstRate() {
    return hstRate;
  }

  public Tax setHstRate(TaxRate hstRate) {
    if (TaxRate.isSet(hstRate)) {
      Validator.assertion(!TaxRate.isSet(getPstRate()), IllegalArgumentException.class,
          "Cannot set HST when PST is present. Remove PST");
      Validator.assertion(!TaxRate.isSet(getGstRate()), IllegalArgumentException.class,
          "Cannot set HST when GST is present. Remove GST");
    }
    this.hstRate = hstRate;
    return this;
  }
}
