package sh.trishul.money.serialize;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import java.io.IOException;
import java.math.BigDecimal;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import sh.trishul.money.currency.model.CurrencyMapper;
import sh.trishul.money.dto.MoneyDto;

public class MoneyDeserializer extends JsonDeserializer<Money> {
  @Override
  public Money deserialize(JsonParser p, DeserializationContext ctx)
      throws IOException, JsonProcessingException {
    JsonNode node = p.getCodec().readTree(p);

    CurrencyUnit currency
        = CurrencyMapper.INSTANCE.toUnit(node.get(MoneyDto.ATTR_CURRENCY).asText());
    BigDecimal amount = new BigDecimal(node.get(MoneyDto.ATTR_AMOUNT).asText());

    return Money.of(currency, amount);
  }
}
