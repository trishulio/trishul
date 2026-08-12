package sh.trishul.money.tax.rate.dto;

import java.math.BigDecimal;
import sh.trishul.model.base.dto.BaseDto;

public class TaxRateDto extends BaseDto {
  private BigDecimal value;

  public TaxRateDto() {
    super();
  }

  public TaxRateDto(BigDecimal value) {
    this();
    setValue(value);
  }

  public BigDecimal getValue() {
    return value;
  }

  public TaxRateDto setValue(BigDecimal value) {
    this.value = value;
    return this;
  }
}
