package io.trishul.money.serialize;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.trishul.model.json.JacksonJsonMapper;
import io.trishul.model.json.JsonMapper;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

public class RegisterTest {
  @BeforeEach
  public void resetMapper() {
    JacksonJsonMapper instance = (JacksonJsonMapper) JsonMapper.INSTANCE;
    instance.module = new SimpleModule();
    instance.mapper = new ObjectMapper().configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
        .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
        .registerModule(new JavaTimeModule());
  }

  @Test
  public void testInit_RegistersMoneySerializerAndDeserializer() {
    Register.init();

    Money money = Money.of(CurrencyUnit.USD, 100.50);
    String json = JsonMapper.INSTANCE.writeString(money);

    assertNotNull(json);
    assertEquals("{\"currency\":\"USD\",\"amount\":100.5}", json);

    Money deserializedMoney = JsonMapper.INSTANCE.readString(json, Money.class);
    assertEquals(money, deserializedMoney);
  }

  @Test
  public void testInit_WithMock_RegistersAndCallsRegisterModule() {
    JacksonJsonMapper mockMapper = mock(JacksonJsonMapper.class);
    Register.init(mockMapper);

    verify(mockMapper).addSerializer(eq(Money.class), any());
    verify(mockMapper).addDeserializer(eq(Money.class), any());
    verify(mockMapper).registerModule();
  }

  @Test
  public void testInit_DelegatesToOverloadedInit() {
    try (MockedStatic<Register> mocked = mockStatic(Register.class)) {
      mocked.when(Register::init).thenCallRealMethod();
      Register.init();
      mocked.verify(() -> Register.init(any(JacksonJsonMapper.class)));
    }
  }

  @Test
  public void testConstructor() {
    assertNotNull(new Register());
  }
}
