package io.trishul.money.serialize;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import java.io.IOException;

import org.joda.money.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import io.trishul.money.dto.MoneyDto;

public class MoneyDeserializerTest {

    JsonDeserializer<Money> deserializer;

    @BeforeEach
    public void init() {
        deserializer = new MoneyDeserializer();
    }

    @Test
    public void testDeserialize() throws JacksonException, IOException {
        JsonNode mCurrencyNode = mock(JsonNode.class);
        doReturn("CAD").when(mCurrencyNode).asText();

        JsonNode mAmountNode = mock(JsonNode.class);
        doReturn("100").when(mAmountNode).asText();

        JsonNode mMoneyNode = mock(JsonNode.class);
        doReturn(mCurrencyNode).when(mMoneyNode).get(MoneyDto.ATTR_CURRENCY);
        doReturn(mAmountNode).when(mMoneyNode).get(MoneyDto.ATTR_AMOUNT);

        ObjectCodec codec = mock(ObjectCodec.class);
        JsonParser p = mock(JsonParser.class);
        doReturn(codec).when(p).getCodec();
        doReturn(mMoneyNode).when(codec).readTree(p);

        Money money = deserializer.deserialize(p, null);

        Money expected = Money.parse("CAD 100");
        assertEquals(expected, money);
    }
}
