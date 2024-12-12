package io.company.brewcraft.model;

import static org.junit.jupiter.api.Assertions.*;

import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

public class CurrencyTest {
    private Currency currency;

    @BeforeEach
    public void init() {
        currency = new Currency();
    }

    @Test
    public void testAllArgsConstructor() {
        currency = new Currency(1234, "CAD");
        assertEquals(1234, currency.getNumericCode());
        assertEquals("CAD", currency.getCode());
    }

    @Test
    public void testAccessNumericCode() {
        assertNull(currency.getNumericCode());
        currency.setNumericCode(123);
        assertEquals(123, currency.getNumericCode());
    }

    @Test
    public void testAccessCode() {
        assertNull(currency.getCode());
        currency.setCode("USD");
        assertEquals("USD", currency.getCode());
    }

    @Test
    public void testToString_ReturnsJsonifiedString() throws JSONException {
        currency = new Currency(1234, "CAD");

        final String json = "{\"numericCode\":1234,\"code\":\"CAD\"}";
        JSONAssert.assertEquals(json, currency.toString(), JSONCompareMode.NON_EXTENSIBLE);
    }
}
