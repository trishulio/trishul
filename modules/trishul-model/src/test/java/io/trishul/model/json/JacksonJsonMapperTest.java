package io.trishul.model.json;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.spy;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class JacksonJsonMapperTest {
  public static class TestData {
    private int x, y;

    public TestData() {}

    public TestData(int x, int y) {
      this.x = x;
      this.y = y;
    }

    public int getX() {
      return x;
    }

    public final void setX(int x) {
      this.x = x;
    }

    public int getY() {
      return y;
    }

    public final void setY(int y) {
      this.y = y;
    }
  }

  private JsonMapper mapper;

  private ObjectMapper mObjectMapper;

  @BeforeEach
  public void init() {
    mObjectMapper = spy(new ObjectMapper());
    mapper = new JacksonJsonMapper(mObjectMapper);
  }

  @Test
  public void testWriteString_ReturnsJson_WhenObjectIsNotNull() {
    TestData data = new TestData(10, 20);
    String json = mapper.writeString(data);

    assertEquals("{\"x\":10,\"y\":20}", json);
  }

  @Test
  public void testWriteString_ReturnsNull_WhenObjectIsNull() {
    String json = mapper.writeString(null);

    assertEquals("null", json);
  }

  @Test
  public void testRead_Returns() {
    String json = "{\"x\":10,\"y\":20}";

    TestData data = mapper.readString(json, TestData.class);

    assertEquals(10, data.getX());
    assertEquals(20, data.getY());
  }

  // @Test
  // public void
  // testQuantitySerialization_ReturnsJsonWithSymbolAndValue_WhenQuantityIsNotNull()
  // {
  // String json = mapper.writeString(Quantities.getQuantity(new
  // BigDecimal("10.99"),
  // SupportedUnits.GRAM));

  // assertEquals("{\"symbol\":\"g\",\"value\":10.99}", json);
  // }

  // @Test
  // public void
  // testQuantityDeserialization_ReturnsQuantityWithValueAndUnit_WhenJsonIsNotNull()
  // {
  // String json = "{\"symbol\":\"kg\",\"value\":10.99}";

  // Quantity<?> qty = mapper.readString(json, Quantity.class);

  // assertEquals(Quantities.getQuantity("10.99 kg"), qty);
  // }

  // @Test
  // public void testQuantityDeserialization_ReturnsNull_WhenJsonIsNull() {
  // Quantity<?> qty = mapper.readString("null", Quantity.class);
  // assertNull(qty);
  // }

  // @Test
  // public void testUnitSerialization_ReturnsJsonWithSymbol_WhenUnitIsNotNull() {
  // String json = mapper.writeString(SupportedUnits.KILOGRAM);

  // assertEquals("{\"symbol\":\"kg\"}", json);
  // }

  // @Test
  // public void testUnitDeserialization_ReturnsUnit_WhenJsonIsNotNull() {
  // String json = "{\"symbol\":\"g\"}";

  // Unit<?> unit = mapper.readString(json, Unit.class);

  // assertEquals(SupportedUnits.GRAM, unit);
  // }

  // @Test
  // public void testUnitDeserialization_ReturnsNull_WhenJsonIsNull() {
  // String json = "null";

  // Unit<?> unit = mapper.readString(json, Unit.class);

  // assertNull(unit);
  // }

  // @Test
  // public void testMoneySerialization_ReturnsMoneyJson_WhenEntityIsNotNull() {
  // Money money = Money.parse("CAD 10");
  // String json = mapper.writeString(money);

  // assertEquals("{\"currency\":\"CAD\",\"amount\":10}", json);
  // }

  // @Test
  // public void testMoneySerialization_ReturnsNullString_WhenEntityIsNull() {
  // String json = mapper.writeString((Money) null);

  // assertEquals("null", json);
  // }

  // @Test
  // public void testMoneyDeserialization_ReturnsMoney_WhenJsonIsNotNull() {
  // String json = "{\"currency\":\"CAD\",\"amount\":10}";
  // Money money = mapper.readString(json, Money.class);

  // assertEquals(Money.parse("CAD 10"), money);
  // }

  // @Test
  // public void testMoneyDeserialization_ReturnsNull_WhenJsonStringIsNull() {
  // String json = "null";
  // Money money = mapper.readString(json, Money.class);

  // assertNull(money);
  // }

  @Test
  public void testLocalDateTimeDeserialization_ReturnsTimestamp_WhenStringIsNotNull() {
    String json = "\"2020-12-31T12:00:00\"";

    LocalDateTime d = mapper.readString(json, LocalDateTime.class);

    LocalDateTime expected = LocalDateTime.of(2020, 12, 31, 12, 0, 0);
    assertEquals(expected, d);
  }

  @Test
  public void testLocalDateTimeSerialization_ReturnsISOTimestamp_WhenDateIsNotNull() {
    LocalDateTime date = LocalDateTime.of(2020, 12, 31, 12, 0, 0);

    String json = mapper.writeString(date);

    assertEquals("\"2020-12-31T12:00:00\"", json);
  }

  @Test
  public void testWriteString_DoesNotCauseStackOverflowInCatchClause_WhenEntityOverridesToStringToCallJacksonMapper()
      throws JsonProcessingException {
    doThrow(JsonProcessingException.class).when(mObjectMapper).writeValueAsString(any());

    class OverrideToStringWithJackson extends TestData {
      @Override
      public String toString() {
        return mapper.writeString(this);
      }
    }

    OverrideToStringWithJackson o = new OverrideToStringWithJackson();
    RuntimeException exception = assertThrows(RuntimeException.class, () -> mapper.writeString(o));
  }
}
