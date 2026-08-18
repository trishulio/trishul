package sh.trishul.money.amount.model;

import sh.trishul.model.base.dto.BaseDto;
import sh.trishul.money.dto.MoneyDto;
import sh.trishul.money.tax.amount.dto.TaxAmountDto;

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

  public AmountDto setTotal(MoneyDto total) {
    this.total = total;
    return this;
  }

  public MoneyDto getSubTotal() {
    return subTotal;
  }

  public AmountDto setSubTotal(MoneyDto subTotal) {
    this.subTotal = subTotal;
    return this;
  }

  public TaxAmountDto getTaxAmount() {
    return taxAmount;
  }

  public AmountDto setTaxAmount(TaxAmountDto taxAmount) {
    this.taxAmount = taxAmount;
    return this;
  }
}
