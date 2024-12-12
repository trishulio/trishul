package io.company.brewcraft.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.math.BigDecimal;

import javax.measure.Quantity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.JsonSerializer;

import tec.uom.se.quantity.Quantities;

public class QuantitySerializerTest {
    private JsonSerializer<Quantity> serializer;
    private MockJsonGenerator mGen;

    @BeforeEach
    public void init() {
        serializer = new QuantitySerializer();
        mGen = new MockJsonGenerator();
    }

    @Test
    public void testSerialize_ReturnNull_WhenValueIsNull() throws IOException {
        serializer.serialize(null, mGen, null);

        assertEquals("null", mGen.json());
    }

    @Test
    public void testSerialize_ReturnsJsonQuantity_WhenValueIsNotNull() throws IOException {
        serializer.serialize(Quantities.getQuantity(new BigDecimal("10"), SupportedUnits.GRAM), mGen, null);

        assertEquals("{\"symbol\":\"g\",\"value\":10}", mGen.json());
    }
}
