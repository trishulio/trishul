package sh.trishul.money.tax.dto;

import sh.trishul.model.base.dto.BaseDto;
import sh.trishul.money.tax.rate.dto.TaxRateDto;

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

  public TaxDto setGstRate(TaxRateDto gstRate) {
    this.gstRate = gstRate;
    return this;
  }

  public TaxRateDto getPstRate() {
    return pstRate;
  }

  public TaxDto setPstRate(TaxRateDto pstRate) {
    this.pstRate = pstRate;
    return this;
  }

  public TaxRateDto getHstRate() {
    return hstRate;
  }

  public TaxDto setHstRate(TaxRateDto hstRate) {
    this.hstRate = hstRate;
    return this;
  }
}
