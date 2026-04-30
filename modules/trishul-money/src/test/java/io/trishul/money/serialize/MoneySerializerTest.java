package io.trishul.money.serialize;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import io.trishul.money.dto.MoneyDto;
import java.io.IOException;
import java.math.BigDecimal;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MoneySerializerTest {
  private MoneySerializer serializer;

  @BeforeEach
  void init() {
    serializer = new MoneySerializer();
  }

  @Test
  void testSerialize_WritesNull_WhenMoneyIsNull() throws IOException {
    JsonGenerator mockGen = mock(JsonGenerator.class);
    SerializerProvider mockProvider = mock(SerializerProvider.class);

    serializer.serialize(null, mockGen, mockProvider);

    verify(mockGen).writeNull();
  }

  @Test
  void testSerialize_WritesMoneyObject_WhenMoneyIsNotNull() throws IOException {
    JsonGenerator mockGen = mock(JsonGenerator.class);
    SerializerProvider mockProvider = mock(SerializerProvider.class);

    Money money = Money.of(CurrencyUnit.CAD, new BigDecimal("100.50"));

    serializer.serialize(money, mockGen, mockProvider);

    verify(mockGen).writeStartObject();
    verify(mockGen).writeStringField(MoneyDto.ATTR_CURRENCY, "CAD");
    verify(mockGen).writeNumberField(MoneyDto.ATTR_AMOUNT, new BigDecimal("100.5"));
    verify(mockGen).writeEndObject();
  }

  @Test
  void testSerialize_WritesZeroAmount_WhenMoneyAmountIsZero() throws IOException {
    JsonGenerator mockGen = mock(JsonGenerator.class);
    SerializerProvider mockProvider = mock(SerializerProvider.class);

    Money money = Money.of(CurrencyUnit.USD, BigDecimal.ZERO);

    serializer.serialize(money, mockGen, mockProvider);

    verify(mockGen).writeStartObject();
    verify(mockGen).writeStringField(MoneyDto.ATTR_CURRENCY, "USD");
    verify(mockGen).writeNumberField(MoneyDto.ATTR_AMOUNT, new BigDecimal("0"));
    verify(mockGen).writeEndObject();
  }

  @Test
  void testSerialize_WritesNegativeAmount_WhenMoneyAmountIsNegative() throws IOException {
    JsonGenerator mockGen = mock(JsonGenerator.class);
    SerializerProvider mockProvider = mock(SerializerProvider.class);

    Money money = Money.of(CurrencyUnit.EUR, new BigDecimal("-50.25"));

    serializer.serialize(money, mockGen, mockProvider);

    verify(mockGen).writeStartObject();
    verify(mockGen).writeStringField(MoneyDto.ATTR_CURRENCY, "EUR");
    verify(mockGen).writeNumberField(MoneyDto.ATTR_AMOUNT, new BigDecimal("-50.25"));
    verify(mockGen).writeEndObject();
  }
}
