package sh.trishul.quantity.unit.serialize;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.databind.JsonSerializer;
import java.io.IOException;
import javax.measure.Unit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sh.trishul.quantity.unit.SupportedUnits;
import sh.trishul.test.json.MockJsonGenerator;

class UnitSerializerTest {
  private JsonSerializer<Unit> serializer;
  private MockJsonGenerator mGen;

  @BeforeEach
  void init() {
    serializer = new UnitSerializer();
    mGen = new MockJsonGenerator();
  }

  @Test
  void testSerialize_ReturnNull_WhenValueIsNull() throws IOException {
    serializer.serialize(null, mGen, null);

    assertEquals("null", mGen.json());
  }

  @Test
  void testSerialize_ReturnsJsonUnit_WhenValueIsNotNull() throws IOException {
    serializer.serialize(SupportedUnits.GRAM, mGen, null);

    assertEquals("{\"symbol\":\"g\"}", mGen.json());
  }
}
