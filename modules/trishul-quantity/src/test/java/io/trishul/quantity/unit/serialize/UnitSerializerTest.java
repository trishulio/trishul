package io.trishul.quantity.unit.serialize;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import javax.measure.Unit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.JsonSerializer;

import io.trishul.quantity.unit.SupportedUnits;
import io.trishul.test.json.MockJsonGenerator;

public class UnitSerializerTest {
    private JsonSerializer<Unit> serializer;
    private MockJsonGenerator mGen;

    @BeforeEach
    public void init() {
        serializer = new UnitSerializer();
        mGen = new MockJsonGenerator();
    }

    @Test
    public void testSerialize_ReturnNull_WhenValueIsNull() throws IOException {
        serializer.serialize(null, mGen, null);

        assertEquals("null", mGen.json());
    }

    @Test
    public void testSerialize_ReturnsJsonUnit_WhenValueIsNotNull() throws IOException {
        serializer.serialize(SupportedUnits.GRAM, mGen, null);

        assertEquals("{\"symbol\":\"g\"}", mGen.json());
    }
}
