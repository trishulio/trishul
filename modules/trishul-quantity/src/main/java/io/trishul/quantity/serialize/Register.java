package io.trishul.quantity.serialize;

import io.trishul.model.json.JacksonJsonMapper;
import io.trishul.model.json.JsonMapper;
import javax.measure.Quantity;

public class Register {
  public static void init() {
    JacksonJsonMapper instance = (JacksonJsonMapper) JsonMapper.INSTANCE;
    instance.addSerializer(Quantity.class, new QuantitySerializer());
    instance.addDeserializer(Quantity.class, new QuantityDeserializer());
    instance.registerModule();
  }
}
