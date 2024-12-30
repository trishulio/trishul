package io.trishul.money.tax.dto;

import io.trishul.model.base.dto.BaseDto;
import io.trishul.money.tax.rate.dto.TaxRateDto;

public class TaxDto extends BaseDto {
  private TaxRateDto gstRate;
  private TaxRateDto pstRate;
  private TaxRateDto hstRate;

  public TaxDto() {
    super();
  }

  public TaxDto(TaxRateDto hstRate) {
    this();
    setHstRate(hstRate);
  }

  public TaxDto(TaxRateDto pstRate, TaxRateDto gstRate) {
    this();
    setPstRate(pstRate);
    setGstRate(gstRate);
  }

  public TaxRateDto getGstRate() {
    return gstRate;
  }

  public final TaxDto setGstRate(TaxRateDto gstRate) {
    this.gstRate = gstRate;
    return this;
  }

  public TaxRateDto getPstRate() {
    return pstRate;
  }

  public final TaxDto setPstRate(TaxRateDto pstRate) {
    this.pstRate = pstRate;
    return this;
  }

  public TaxRateDto getHstRate() {
    return hstRate;
  }

  public final TaxDto setHstRate(TaxRateDto hstRate) {
    this.hstRate = hstRate;
    return this;
  }
}
