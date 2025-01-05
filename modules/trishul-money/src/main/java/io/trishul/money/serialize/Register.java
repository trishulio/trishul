package io.trishul.money.serialize;

import org.joda.money.Money;

import io.trishul.model.json.JacksonJsonMapper;
import io.trishul.model.json.JsonMapper;

public class Register {
    public static void init() {
        JacksonJsonMapper instance = (JacksonJsonMapper) JsonMapper.INSTANCE;
        instance.addSerializer(Money.class, new MoneySerializer());
        instance.addDeserializer(Money.class, new MoneyDeserializer());
        instance.registerModule();
    }
}
