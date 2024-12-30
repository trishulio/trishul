package io.trishul.quantity.unit.serialize;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import javax.measure.Unit;

@SuppressWarnings("rawtypes")
public class UnitSerializer extends JsonSerializer<Unit> {
  @Override
  public void serialize(Unit value, JsonGenerator gen, SerializerProvider serializers)
      throws IOException {
    if (value == null) {
      gen.writeNull();
    } else {
      gen.writeStartObject();
      gen.writeStringField("symbol", value.getSymbol());
      gen.writeEndObject();
    }
  }
}
