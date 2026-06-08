package io.trishul.quantity.serialize;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import io.trishul.quantity.model.dto.QuantityDto;
import io.trishul.quantity.unit.SupportedUnits;
import java.io.IOException;
import java.math.BigDecimal;
import javax.measure.Quantity;
import org.junit.jupiter.api.Test;
import tec.uom.se.quantity.Quantities;

class QuantityDeserializerTest {
  @Test
  void testDeserialize() throws IOException {
    JsonParser parser = mock(JsonParser.class);
    DeserializationContext context = mock(DeserializationContext.class);
    ObjectCodec codec = mock(ObjectCodec.class);
    JsonNode node = mock(JsonNode.class);

    when(parser.getCodec()).thenReturn(codec);
    when(codec.readTree(parser)).thenReturn(node);

    JsonNode symbolNode = mock(JsonNode.class);
    JsonNode valueNode = mock(JsonNode.class);
    when(node.get(QuantityDto.ATTR_SYMBOL)).thenReturn(symbolNode);
    when(node.get(QuantityDto.ATTR_VALUE)).thenReturn(valueNode);
    when(symbolNode.asText()).thenReturn("g");
    when(valueNode.asText()).thenReturn("10");

    QuantityDeserializer deserializer = new QuantityDeserializer();
    Quantity<?> result = deserializer.deserialize(parser, context);

    assertEquals(Quantities.getQuantity(new BigDecimal("10"), SupportedUnits.GRAM), result);
  }
}
