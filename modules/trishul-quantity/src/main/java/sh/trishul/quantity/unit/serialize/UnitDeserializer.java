package sh.trishul.quantity.unit.serialize;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import java.io.IOException;
import javax.measure.Unit;
import sh.trishul.quantity.unit.QuantityUnitMapper;
import sh.trishul.quantity.unit.dto.UnitDto;

public class UnitDeserializer extends JsonDeserializer<Unit<?>> {
  @Override
  public Unit<?> deserialize(JsonParser p, DeserializationContext ctxt)
      throws IOException, JsonProcessingException {
    JsonNode node = p.getCodec().readTree(p);
    return QuantityUnitMapper.INSTANCE.fromSymbol(node.get(UnitDto.ATTR_SYMBOL).asText());
  }
}
