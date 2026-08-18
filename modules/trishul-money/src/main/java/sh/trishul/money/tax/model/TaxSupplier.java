package sh.trishul.money.tax.model;

public interface TaxSupplier {
  final String ATTR_TAX = "tax";

  Tax getTax();
}
