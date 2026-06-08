package io.trishul.quantity.unit.serialize;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import io.trishul.quantity.unit.SupportedUnits;
import io.trishul.quantity.unit.dto.UnitDto;
import java.io.IOException;
import javax.measure.Unit;
import org.junit.jupiter.api.Test;

class UnitDeserializerTest {
  @Test
  void testDeserialize() throws IOException {
    JsonParser parser = mock(JsonParser.class);
    DeserializationContext context = mock(DeserializationContext.class);
    ObjectCodec codec = mock(ObjectCodec.class);
    JsonNode node = mock(JsonNode.class);

    when(parser.getCodec()).thenReturn(codec);
    when(codec.readTree(parser)).thenReturn(node);

    JsonNode symbolNode = mock(JsonNode.class);
    when(node.get(UnitDto.ATTR_SYMBOL)).thenReturn(symbolNode);
    when(symbolNode.asText()).thenReturn("g");

    UnitDeserializer deserializer = new UnitDeserializer();
    Unit<?> result = deserializer.deserialize(parser, context);

    assertEquals(SupportedUnits.GRAM, result);
  }
}
