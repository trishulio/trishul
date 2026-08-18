package sh.trishul.quantity.serialize;

import javax.measure.Quantity;
import sh.trishul.model.json.JacksonJsonMapper;
import sh.trishul.model.json.JsonMapper;

public class Register {
  public static void init() {
    init((JacksonJsonMapper) JsonMapper.INSTANCE);
  }

  public static void init(JacksonJsonMapper instance) {
    instance.addSerializer(Quantity.class, new QuantitySerializer());
    instance.addDeserializer(Quantity.class, new QuantityDeserializer());
    instance.registerModule();
  }
}
