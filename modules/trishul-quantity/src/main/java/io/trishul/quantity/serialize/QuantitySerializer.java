package io.trishul.quantity.serialize;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import io.trishul.quantity.model.QuantityMapper;
import io.trishul.quantity.model.dto.QuantityDto;
import java.io.IOException;
import java.math.BigDecimal;
import javax.measure.Quantity;

@SuppressWarnings("rawtypes")
public class QuantitySerializer extends JsonSerializer<Quantity> {
    protected QuantitySerializer() {
        super();
    }

    @Override
    public void serialize(Quantity value, JsonGenerator gen, SerializerProvider provider)
            throws IOException {
        if (value == null) {
            gen.writeNull();
        } else {
            gen.writeStartObject();
            QuantityDto dto = QuantityMapper.INSTANCE.toDto(value);
            gen.writeStringField(QuantityDto.ATTR_SYMBOL, dto.getSymbol());
            gen.writeNumberField(QuantityDto.ATTR_VALUE, (BigDecimal) dto.getValue());
            gen.writeEndObject();
        }
    }
}
