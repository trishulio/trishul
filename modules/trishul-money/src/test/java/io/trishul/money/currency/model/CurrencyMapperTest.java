package io.trishul.money.currency.model;

import static org.junit.jupiter.api.Assertions.*;

import org.joda.money.CurrencyUnit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CurrencyMapperTest {
    private CurrencyMapper mapper;

    @BeforeEach
    public void init() {
        mapper = CurrencyMapper.INSTANCE;
    }

    @Test
    public void testStringToEntity_ReturnsNull_WhenCodeIsNull() {
        assertNull(mapper.toEntity((String) null));
    }

    @Test
    public void testUnitToEntity_ReturnsNull_WhenUnitIsNull() {
        assertNull(mapper.toEntity((CurrencyUnit) null));
    }

    @Test
    public void testStringToEntity_ReturnPojoFromCode_WhenCodeIsValid() {
        Currency cad = mapper.toEntity("CAD");
        assertEquals(new Currency(124, "CAD"), cad);

        Currency usd = mapper.toEntity("USD");
        assertEquals(new Currency(840, "USD"), usd);

        Currency inr = mapper.toEntity("INR");
        assertEquals(new Currency(356, "INR"), inr);
    }

    @Test
    public void testUnitToEntity_ReturnsPojoFromUnit_WhenCodeAndNameAreValid() {
        Currency cad = mapper.toEntity(CurrencyUnit.of("CAD"));
        assertEquals(new Currency(124, "CAD"), cad);

        Currency usd = mapper.toEntity(CurrencyUnit.of("USD"));
        assertEquals(new Currency(840, "USD"), usd);

        Currency inr = mapper.toEntity(CurrencyUnit.of("INR"));
        assertEquals(new Currency(356, "INR"), inr);
    }
}
