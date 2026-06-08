package io.trishul.money.serialize;

import io.trishul.model.json.JacksonJsonMapper;
import io.trishul.model.json.JsonMapper;
import org.joda.money.Money;

public class Register {
  public static void init() {
    JacksonJsonMapper instance = (JacksonJsonMapper) JsonMapper.INSTANCE;
    instance.addSerializer(Money.class, new MoneySerializer());
    instance.addDeserializer(Money.class, new MoneyDeserializer());
    instance.registerModule();
  }
}
