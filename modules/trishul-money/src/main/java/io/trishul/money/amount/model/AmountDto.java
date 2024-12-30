package io.trishul.money.amount.model;

import io.trishul.model.base.dto.BaseDto;
import io.trishul.money.dto.MoneyDto;
import io.trishul.money.tax.amount.dto.TaxAmountDto;

public class AmountDto extends BaseDto {
  private MoneyDto total;
  private MoneyDto subTotal;
  private TaxAmountDto taxAmount;

  public AmountDto() {
    super();
  }

  public AmountDto(MoneyDto subTotal) {
    this();
    setSubTotal(subTotal);
  }

  public AmountDto(MoneyDto total, MoneyDto subTotal, TaxAmountDto taxAmount) {
    this(subTotal);
    setTotal(total);
    setTaxAmount(taxAmount);
  }

  public MoneyDto getTotal() {
    return total;
  }

  public final AmountDto setTotal(MoneyDto total) {
    this.total = total;
    return this;
  }

  public MoneyDto getSubTotal() {
    return subTotal;
  }

  public final AmountDto setSubTotal(MoneyDto subTotal) {
    this.subTotal = subTotal;
    return this;
  }

  public TaxAmountDto getTaxAmount() {
    return taxAmount;
  }

  public final AmountDto setTaxAmount(TaxAmountDto taxAmount) {
    this.taxAmount = taxAmount;
    return this;
  }
}
