package io.trishul.money.serialize;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import io.trishul.model.json.JsonMapper;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.junit.jupiter.api.Test;

public class RegisterTest {

  @Test
  public void testInit_RegistersMoneySerializerAndDeserializer() {
    Register.init();

    Money money = Money.of(CurrencyUnit.USD, 100.50);
    String json = JsonMapper.INSTANCE.writeString(money);

    assertNotNull(json);

    Money deserializedMoney = JsonMapper.INSTANCE.readString(json, Money.class);
    assertEquals(money, deserializedMoney);
  }

  @Test
  public void testConstructor() {
    assertNotNull(new Register());
  }
}
