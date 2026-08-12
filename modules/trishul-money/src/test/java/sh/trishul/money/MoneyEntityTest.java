package sh.trishul.money;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.math.BigDecimal;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import sh.trishul.money.currency.model.Currency;

class MoneyEntityTest {
  private MoneyEntity money;

  @BeforeEach
  void init() {
    money = new MoneyEntity();
  }

  @Test
  void testNoArgsConstructor() {
    assertNull(money.getCurrency());
    assertNull(money.getAmount());
  }

  @Test
  void testAllArgsConstructor() {
    money = new MoneyEntity(new Currency(123, "CAD"), new BigDecimal("100"));
    assertEquals(new Currency(123, "CAD"), money.getCurrency());
    assertEquals(new BigDecimal("100"), money.getAmount());
  }

  @Test
  void testAccessAmount() {
    assertNull(money.getAmount());
    MoneyEntity returned = money.setAmount(new BigDecimal("100"));
    assertSame(money, returned);
    assertEquals(new BigDecimal("100"), money.getAmount());
  }

  @Test
  void testAccessCurrency() {
    assertNull(money.getCurrency());
    Currency currency = new Currency(123, "CAD");
    MoneyEntity returned = money.setCurrency(currency);
    assertSame(money, returned);
    assertEquals(currency, money.getCurrency());
    assertNotSame(currency, money.getCurrency());

    money.setCurrency(null);
    assertNull(money.getCurrency());
  }

  @Test
  void testToString_ReturnsJsonifiedString() throws JSONException {
    money = new MoneyEntity(new Currency(123, "CAD"), new BigDecimal("100"));

    final String json = "{\"currency\":{\"numericCode\":123,\"code\":\"CAD\"},\"amount\":100}";
    JSONAssert.assertEquals(json, money.toString(), JSONCompareMode.NON_EXTENSIBLE);
  }

  @Test
  void testEqualsAndHashCode() {
    MoneyEntity money1 = new MoneyEntity(new Currency(123, "CAD"), new BigDecimal("100"));
    MoneyEntity money2 = new MoneyEntity(new Currency(123, "CAD"), new BigDecimal("100"));
    MoneyEntity money3 = new MoneyEntity(new Currency(456, "USD"), new BigDecimal("200"));

    assertEquals(money1, money2);
    assertEquals(money1.hashCode(), money2.hashCode());
    assertNotEquals(money1, money3);
    assertNotEquals(money1.hashCode(), money3.hashCode());
  }
}
