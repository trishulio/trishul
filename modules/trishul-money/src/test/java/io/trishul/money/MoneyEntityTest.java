package io.trishul.money;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import io.trishul.money.currency.model.Currency;
import java.math.BigDecimal;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

public class MoneyEntityTest {
    private MoneyEntity money;

    @BeforeEach
    public void init() {
        money = new MoneyEntity();
    }

    @Test
    public void testAllArgsConstructor() {
        money = new MoneyEntity(new Currency(123, "CAD"), new BigDecimal("100"));
        assertEquals(new Currency(123, "CAD"), money.getCurrency());
        assertEquals(new BigDecimal("100"), money.getAmount());
    }

    @Test
    public void testAccessAmount() {
        assertNull(money.getAmount());
        money.setAmount(new BigDecimal("100"));
        assertEquals(new BigDecimal("100"), money.getAmount());
    }

    @Test
    public void testAccessCurrency() {
        assertNull(money.getCurrency());
        money.setCurrency(new Currency(123, "CAD"));
        assertEquals(new Currency(123, "CAD"), money.getCurrency());
    }

    @Test
    public void testToString_ReturnsJsonifiedString() throws JSONException {
        money = new MoneyEntity(new Currency(123, "CAD"), new BigDecimal("100"));

        final String json = "{\"currency\":{\"numericCode\":123,\"code\":\"CAD\"},\"amount\":100}";
        JSONAssert.assertEquals(json, money.toString(), JSONCompareMode.NON_EXTENSIBLE);
    }
}
