package io.trishul.money.tax.amount.dto;

import io.trishul.model.base.dto.BaseDto;
import io.trishul.money.dto.MoneyDto;

public class TaxAmountDto extends BaseDto {
    private MoneyDto pstAmount;

    private MoneyDto gstAmount;

    private MoneyDto hstAmount;

    private MoneyDto totalTaxAmount;

    public TaxAmountDto() {
        super();
    }

    public TaxAmountDto(MoneyDto pstAmount, MoneyDto gstAmount, MoneyDto hstAmount, MoneyDto totalTaxAmount) {
        this();
        setPstAmount(pstAmount);
        setGstAmount(gstAmount);
        setHstAmount(hstAmount);
        setTotalTaxAmount(totalTaxAmount);
    }

    public TaxAmountDto(MoneyDto pstAmount, MoneyDto gstAmount, MoneyDto totalTaxAmount) {
        this(pstAmount, gstAmount, null, totalTaxAmount);
    }

    public TaxAmountDto(MoneyDto hstAmount, MoneyDto totalTaxAmount) {
        this(null, null, hstAmount, totalTaxAmount);
    }

    public MoneyDto getPstAmount() {
        return pstAmount;
    }

    public void setPstAmount(MoneyDto pstAmount) {
        this.pstAmount = pstAmount;
    }

    public MoneyDto getGstAmount() {
        return gstAmount;
    }

    public void setGstAmount(MoneyDto gstAmount) {
        this.gstAmount = gstAmount;
    }

    public MoneyDto getHstAmount() {
        return hstAmount;
    }

    public void setHstAmount(MoneyDto hstAmount) {
        this.hstAmount = hstAmount;
    }

    public MoneyDto getTotalTaxAmount() {
        return totalTaxAmount;
    }

    public void setTotalTaxAmount(MoneyDto totalTaxAmount) {
        this.totalTaxAmount = totalTaxAmount;
    }
}
