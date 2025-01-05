package io.trishul.money.currency.model;

import org.joda.money.CurrencyUnit;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.trishul.model.base.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity(name = "currency")
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class Currency extends BaseEntity implements UpdateCurrency<Currency> {
  @Id
  @Column(name = "numeric_code")
  private Integer numericCode;

  @Column(name = "code", nullable = false, length = 3)
  private String code;

  public Currency() {
    this(null, null);
  }

  public Currency(String code) {
    this(CurrencyUnit.of(code).getNumericCode(), code);
  }

  public Currency(Integer numericCode, String code) {
    setNumericCode(numericCode);
    setCode(code);
  }

  @Override
  public Integer getNumericCode() {
    return numericCode;
  }

  @Override
  public Currency setNumericCode(Integer numericCode) {
    this.numericCode = numericCode;
    return this;
  }

  @Override
  public String getCode() {
    return code;
  }

  @Override
  public Currency setCode(String code) {
    this.code = code;
    return this;
  }
}
