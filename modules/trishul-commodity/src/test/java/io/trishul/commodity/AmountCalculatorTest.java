package io.trishul.commodity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import io.trishul.commodity.good.model.Good;
import io.trishul.money.amount.model.Amount;
import io.trishul.money.amount.model.AmountSupplier;
import io.trishul.money.tax.amount.TaxAmount;
import io.trishul.money.tax.model.Tax;
import io.trishul.money.tax.rate.TaxRate;
import io.trishul.quantity.unit.SupportedUnits;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.measure.Quantity;
import org.joda.money.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tec.uom.se.quantity.Quantities;

public class AmountCalculatorTest {
    private AmountCalculator calculator;

    @BeforeEach
    public void init() {
        calculator = AmountCalculator.INSTANCE;
    }

    @Test
    public void testGetAmount_ReturnsNull_WhenSubTotalIsNull() {
        Good good =
                new Good() {
                    @Override
                    public Tax getTax() {
                        return null;
                    }

                    @Override
                    public Quantity<?> getQuantity() {
                        return null;
                    }

                    @Override
                    public Money getPrice() {
                        return null;
                    }
                };

        assertNull(calculator.getAmount(good));
    }

    @Test
    public void testGetAmount_ReturnsAmountWithNullTaxAmount_WhenTaxIsNull() {
        Good good =
                new Good() {
                    @Override
                    public Tax getTax() {
                        return null;
                    }

                    @Override
                    public Quantity<?> getQuantity() {
                        return Quantities.getQuantity(new BigDecimal("10"), SupportedUnits.GRAM);
                    }

                    @Override
                    public Money getPrice() {
                        return Money.parse("CAD 5");
                    }
                };

        Amount amount = calculator.getAmount(good);

        Amount expected = new Amount(Money.parse("CAD 50"));
        assertEquals(expected, amount);
    }

    @Test
    public void testGetAmount_ReturnsAmountt_WhenArgsAreNotNull() {
        Good good =
                new Good() {
                    @Override
                    public Tax getTax() {
                        return new Tax(new TaxRate(new BigDecimal("0.1")));
                    }

                    @Override
                    public Quantity<?> getQuantity() {
                        return Quantities.getQuantity(new BigDecimal("10"), SupportedUnits.GRAM);
                    }

                    @Override
                    public Money getPrice() {
                        return Money.parse("CAD 5");
                    }
                };

        Amount amount = calculator.getAmount(good);

        Amount expected = new Amount(Money.parse("CAD 50"), new TaxAmount(Money.parse("CAD 5")));
        assertEquals(expected, amount);
    }

    @Test
    public void testGetTotal_ReturnsNull_WhenSuppliersAreNull() {
        assertNull(calculator.getTotal(null));
    }

    @Test
    public void testGetTotal_ReturnsEmptyAmountWithEmptyTaxes_WhenListIsEmpty() {
        Amount expected = new Amount();
        expected.setTaxAmount(new TaxAmount());

        assertEquals(expected, calculator.getTotal(new ArrayList<>()));
    }

    @Test
    public void testGetTotal_ReturnsTotalOfAmounts() {
        List<? extends AmountSupplier> suppliers =
                new ArrayList<>() {
                    private static final long serialVersionUID = 1L;

                    {
                        add(null);
                        add(() -> null);
                        add(null);
                        add(() -> null);
                        add(null);
                        add(() -> null);
                        add(() -> new Amount());
                        add(() -> new Amount(Money.parse("CAD 100")));
                        add(() -> new Amount(null, new TaxAmount(Money.parse("CAD 15"))));
                        add(
                                () ->
                                        new Amount(
                                                Money.parse("CAD 150"),
                                                new TaxAmount(Money.parse("CAD 25"))));
                        add(null);
                        add(() -> new Amount(null, new TaxAmount(Money.parse("CAD 35"))));
                        add(() -> null);
                    }
                };

        Amount total = calculator.getTotal(suppliers);

        Amount expected = new Amount(Money.parse("CAD 250"), new TaxAmount(Money.parse("CAD 75")));
        assertEquals(expected, total);
    }
}
