package io.trishul.commodity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import io.trishul.commodity.model.Commodity;
import io.trishul.quantity.unit.SupportedUnits;
import java.math.BigDecimal;
import javax.measure.Quantity;
import org.joda.money.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tec.uom.se.quantity.Quantities;

public class CostCalculatorTest {
  private CostCalculator calculator;

  @BeforeEach
  public void init() {
    calculator = CostCalculator.INSTANCE;
  }

  @Test
  public void testGetCost_ReturnsNull_WhenCommodityIsNull() {
    assertNull(calculator.getCost(null));
  }

  @Test
  public void testGetCost_ReturnsNull_WhenCommodityFieldsAreNull() {
    Commodity commodity = new Commodity() {
      @Override
      public Quantity<?> getQuantity() {
        return null;
      }

      @Override
      public Money getPrice() {
        return null;
      }
    };
    assertNull(calculator.getCost(commodity));
  }

  @Test
  public void testGetCost_ReturnsNull_WhenQuantityIsNull() {
    Commodity commodity = new Commodity() {
      @Override
      public Quantity<?> getQuantity() {
        return null;
      }

      @Override
      public Money getPrice() {
        return Money.parse("CAD 100");
      }
    };
    assertNull(calculator.getCost(commodity));
  }

  @Test
  public void testGetCost_ReturnsNull_WhenPriceIsNull() {
    Commodity commodity = new Commodity() {
      @Override
      public Quantity<?> getQuantity() {
        return Quantities.getQuantity(new BigDecimal("10"), SupportedUnits.GRAM);
      }

      @Override
      public Money getPrice() {
        return null;
      }
    };
    assertNull(calculator.getCost(commodity));
  }

  @Test
  public void testGetCost_ReturnsCost_WhenNoFieldIsNulL() {
    Commodity commodity = new Commodity() {
      @Override
      public Quantity<?> getQuantity() {
        return Quantities.getQuantity(new BigDecimal("10"), SupportedUnits.GRAM);
      }

      @Override
      public Money getPrice() {
        return Money.parse("CAD 100");
      }
    };

    assertEquals(Money.parse("CAD 1000"), calculator.getCost(commodity));
  }
}
