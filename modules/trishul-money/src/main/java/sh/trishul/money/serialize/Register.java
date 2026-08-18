package sh.trishul.money.serialize;

import org.joda.money.Money;
import sh.trishul.model.json.JacksonJsonMapper;
import sh.trishul.model.json.JsonMapper;

public class Register {
  public static void init() {
    init((JacksonJsonMapper) JsonMapper.INSTANCE);
  }

  public static void init(JacksonJsonMapper instance) {
    instance.addSerializer(Money.class, new MoneySerializer());
    instance.addDeserializer(Money.class, new MoneyDeserializer());
    instance.registerModule();
  }
}
