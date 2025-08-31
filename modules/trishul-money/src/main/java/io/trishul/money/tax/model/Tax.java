package io.trishul.money.tax.model;

import io.trishul.model.base.entity.BaseEntity;
import io.trishul.money.tax.rate.TaxRate;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import io.trishul.model.base.exception.IllegalArgException;

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
    IllegalArgException.assertion(
        !TaxRate.isSet(gstRate) || (TaxRate.isSet(gstRate) && !TaxRate.isSet(getHstRate())),
        "Cannot set GST when HST is present. Remove HST");
    this.gstRate = gstRate;
    return this;
  }

  public TaxRate getPstRate() {
    return pstRate;
  }

  public Tax setPstRate(TaxRate pstRate) {
    IllegalArgException.assertion(
        !TaxRate.isSet(pstRate) || (TaxRate.isSet(pstRate) && !TaxRate.isSet(getHstRate())),
        "Cannot set PST when HST is present. Remove HST");
    this.pstRate = pstRate;
    return this;
  }

  public TaxRate getHstRate() {
    return hstRate;
  }

  public Tax setHstRate(TaxRate hstRate) {
    if (TaxRate.isSet(hstRate)) {
      IllegalArgException.assertion(!TaxRate.isSet(getPstRate()),
          "Cannot set HST when PST is present. Remove PST");
      IllegalArgException.assertion(!TaxRate.isSet(getGstRate()),
          "Cannot set HST when GST is present. Remove GST");
    }
    this.hstRate = hstRate;
    return this;
  }
}
