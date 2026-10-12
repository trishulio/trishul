package sh.trishul.money.tax.calculator;

import java.util.Iterator;
import java.util.Objects;
import java.util.stream.StreamSupport;
import org.joda.money.Money;
import sh.trishul.money.tax.amount.TaxAmount;
import sh.trishul.money.tax.model.Tax;
import sh.trishul.money.tax.rate.calculator.TaxRateCalculator;

public class TaxCalculator {
  public static final TaxCalculator INSTANCE = new TaxCalculator(TaxRateCalculator.INSTANCE);

  private final TaxRateCalculator rateCalculator;

  protected TaxCalculator(TaxRateCalculator rateCalculator) {
    this.rateCalculator = rateCalculator;
  }

  public TaxAmount getTaxAmount(Tax tax, Money amount) {
    TaxAmount taxAmount = null;

    if (tax != null && amount != null) {
      Money pst = rateCalculator.getTaxAmount(tax.getPstRate(), amount);
      Money gst = rateCalculator.getTaxAmount(tax.getGstRate(), amount);
      Money hst = rateCalculator.getTaxAmount(tax.getHstRate(), amount);

      taxAmount = new TaxAmount(pst, gst, hst);
    }

    return taxAmount;
  }

  public TaxAmount getTaxAmountTotal(Iterable<TaxAmount> taxAmounts) {
    TaxAmount taxAmount = null;

    if (taxAmounts != null) {
      Money pst = null;
      Money gst = null;
      Money hst = null;

      Iterator<TaxAmount> it = StreamSupport.stream(taxAmounts.spliterator(), false)
          .filter(Objects::nonNull).iterator();

      while (it.hasNext()) {
        TaxAmount amount = it.next();
        pst = addMoney(pst, amount.getPstAmount());
        gst = addMoney(gst, amount.getGstAmount());
        hst = addMoney(hst, amount.getHstAmount());
      }

      taxAmount = new TaxAmount(pst, gst, hst);
    }

    return taxAmount;
  }

  private static Money addMoney(Money total, Money toAdd) {
    if (total == null) {
      return toAdd;
    }
    if (toAdd != null) {
      return total.plus(toAdd);
    }
    return total;
  }
}
