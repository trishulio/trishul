package io.trishul.quantity.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

public class UnitEntityTest {
    private UnitEntity unit;

    @BeforeEach
    public void init() {
        unit = new UnitEntity();
    }

    @Test
    public void testArgConstructor_String() {
        unit = new UnitEntity("g");

        assertEquals("g", unit.getSymbol());
        assertNull(unit.getName());
        assertNull(unit.getBaseUnitEntity());
    }

    @Test
    public void testArgConstructor_StringString() {
        unit = new UnitEntity("g", "gram");

        assertEquals("g", unit.getSymbol());
        assertEquals("gram", unit.getName());
        assertNull(unit.getBaseUnitEntity());
    }

    @Test
    public void testAllArgsConstructor() {
        unit = new UnitEntity("g", "gram", new UnitEntity("g"));
        assertEquals("g", unit.getSymbol());
        assertEquals("gram", unit.getName());
        assertEquals(new UnitEntity("g"), unit.getBaseUnitEntity());
    }

    @Test
    public void testAccessSymbol() {
        assertNull(unit.getSymbol());
        unit.setSymbol("g");
        assertEquals("g", unit.getSymbol());
    }

    @Test
    public void testAccessName() {
        assertNull(unit.getName());
        unit.setName("Kilogram");
        assertEquals("Kilogram", unit.getName());
    }

    @Test
    public void testGetSetBaseUnitEntity() {
        assertNull(unit.getBaseUnitEntity());
        unit.setBaseUnitEntity(new UnitEntity("g", "gram"));
        assertEquals(new UnitEntity("g", "gram"), unit.getBaseUnitEntity());
    }

    @Test
    public void testToString_ReturnsJsonifiedString() throws JSONException {
        unit = new UnitEntity("KG", "Kilogram");

        final String json = "{\"symbol\":\"KG\",\"name\":\"Kilogram\"}";
        JSONAssert.assertEquals(json, unit.toString(), JSONCompareMode.NON_EXTENSIBLE);
    }
}
