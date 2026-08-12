package sh.trishul.quantity.serialize;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.databind.JsonSerializer;
import java.io.IOException;
import java.math.BigDecimal;
import javax.measure.Quantity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sh.trishul.quantity.unit.SupportedUnits;
import sh.trishul.test.json.MockJsonGenerator;
import tec.uom.se.quantity.Quantities;

class QuantitySerializerTest {
  private JsonSerializer<Quantity> serializer;
  private MockJsonGenerator mGen;

  @BeforeEach
  void init() {
    serializer = new QuantitySerializer();
    mGen = new MockJsonGenerator();
  }

  @Test
  void testSerialize_ReturnNull_WhenValueIsNull() throws IOException {
    serializer.serialize(null, mGen, null);

    assertEquals("null", mGen.json());
  }

  @Test
  void testSerialize_ReturnsJsonQuantity_WhenValueIsNotNull() throws IOException {
    serializer.serialize(Quantities.getQuantity(new BigDecimal("10"), SupportedUnits.GRAM), mGen,
        null);

    assertEquals("{\"symbol\":\"g\",\"value\":10}", mGen.json());
  }
}
