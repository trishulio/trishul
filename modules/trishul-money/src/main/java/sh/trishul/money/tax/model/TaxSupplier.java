package sh.trishul.money.tax.model;

public interface TaxSupplier {
  String ATTR_TAX = "tax";

  Tax getTax();
}
