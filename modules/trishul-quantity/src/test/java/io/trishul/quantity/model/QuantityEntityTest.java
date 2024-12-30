package io.trishul.quantity.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import io.trishul.quantity.unit.UnitEntity;
import java.math.BigDecimal;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

public class QuantityEntityTest {
  private QuantityEntity quantity;

  @BeforeEach
  public void init() {
    quantity = new QuantityEntity();
  }

  @Test
  public void testAllArgsConstructor() {
    quantity = new QuantityEntity(new UnitEntity("g"), new BigDecimal("100"));
    assertEquals(new UnitEntity("g"), quantity.getUnit());
    assertEquals(new BigDecimal("100"), quantity.getValue());
  }

  @Test
  public void testAccessUnit() {
    assertNull(quantity.getUnit());
    quantity.setUnit(new UnitEntity("L"));
    assertEquals(new UnitEntity("L"), quantity.getUnit());
  }

  @Test
  public void testAccessValue() {
    assertNull(quantity.getValue());
    quantity.setValue(new BigDecimal("99"));
    assertEquals(new BigDecimal("99"), quantity.getValue());
  }

  @Test
  public void testToString_ReturnsJsonifiedString() throws JSONException {
    quantity = new QuantityEntity(new UnitEntity("KG"), new BigDecimal("100"));

    final String json = "{\"unit\":{\"symbol\":\"KG\",\"name\":null},\"value\":100}";
    JSONAssert.assertEquals(json, quantity.toString(), JSONCompareMode.NON_EXTENSIBLE);
  }
}
