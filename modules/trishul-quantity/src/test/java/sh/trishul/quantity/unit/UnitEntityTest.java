package sh.trishul.quantity.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

class UnitEntityTest {
  private UnitEntity unit;

  @BeforeEach
  void init() {
    unit = new UnitEntity();
  }

  @Test
  void testArgConstructor_String() {
    unit = new UnitEntity("g");

    assertEquals("g", unit.getSymbol());
    assertNull(unit.getName());
    assertNull(unit.getBaseUnitEntity());
  }

  @Test
  void testArgConstructor_StringString() {
    unit = new UnitEntity("g", "gram");

    assertEquals("g", unit.getSymbol());
    assertEquals("gram", unit.getName());
    assertNull(unit.getBaseUnitEntity());
  }

  @Test
  void testAllArgsConstructor() {
    unit = new UnitEntity("g", "gram", new UnitEntity("g"));
    assertEquals("g", unit.getSymbol());
    assertEquals("gram", unit.getName());
    assertEquals(new UnitEntity("g"), unit.getBaseUnitEntity());
  }

  @Test
  void testAccessSymbol() {
    assertNull(unit.getSymbol());
    assertSame(unit, unit.setSymbol("g"));
    assertEquals("g", unit.getSymbol());
  }

  @Test
  void testAccessName() {
    assertNull(unit.getName());
    assertSame(unit, unit.setName("Kilogram"));
    assertEquals("Kilogram", unit.getName());
  }

  @Test
  void testGetSetBaseUnitEntity() {
    assertNull(unit.getBaseUnitEntity());
    unit.setBaseUnitEntity(new UnitEntity("g", "gram"));
    assertEquals(new UnitEntity("g", "gram"), unit.getBaseUnitEntity());
  }

  @Test
  void testToString_ReturnsJsonifiedString() throws JSONException {
    unit = new UnitEntity("KG", "Kilogram");

    final String json = "{\"symbol\":\"KG\",\"name\":\"Kilogram\"}";
    JSONAssert.assertEquals(json, unit.toString(), JSONCompareMode.NON_EXTENSIBLE);
  }

  @Test
  void testAccessBaseUnitEntity() throws Exception {
    UnitEntity accessor = new UnitEntity();
    UnitEntity value = new UnitEntity();
    assertSame(accessor, accessor.setBaseUnitEntity(value));
    assertEquals(value, accessor.getBaseUnitEntity());
  }

}
