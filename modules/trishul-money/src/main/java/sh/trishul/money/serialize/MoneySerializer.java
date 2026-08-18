package sh.trishul.money.serialize;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import org.joda.money.Money;
import sh.trishul.money.MoneyMapper;
import sh.trishul.money.dto.MoneyDto;

public class MoneySerializer extends JsonSerializer<Money> {
  @Override
  public void serialize(Money value, JsonGenerator gen, SerializerProvider serializers)
      throws IOException {
    if (value == null) {
      gen.writeNull();
    } else {
      gen.writeStartObject();
      MoneyDto dto = MoneyMapper.INSTANCE.toDto(value);
      gen.writeStringField(MoneyDto.ATTR_CURRENCY, dto.getCurrency());
      gen.writeNumberField(MoneyDto.ATTR_AMOUNT, dto.getAmount());
      gen.writeEndObject();
    }
  }
}
