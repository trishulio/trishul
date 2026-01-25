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

class JacksonJsonMapperTest {
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

    public void setX(int x) {
      this.x = x;
    }

    public int getY() {
      return y;
    }

    public void setY(int y) {
      this.y = y;
    }
  }

  private JsonMapper mapper;

  private ObjectMapper mObjectMapper;

  @BeforeEach
  void init() {
    mObjectMapper = spy(new ObjectMapper());
    mapper = new JacksonJsonMapper(mObjectMapper);
  }

  @Test
  void testWriteString_ReturnsJson_WhenObjectIsNotNull() {
    TestData data = new TestData(10, 20);
    String json = mapper.writeString(data);

    assertEquals("{\"x\":10,\"y\":20}", json);
  }

  @Test
  void testWriteString_ReturnsNull_WhenObjectIsNull() {
    String json = mapper.writeString(null);

    assertEquals("null", json);
  }

  @Test
  void testRead_Returns() {
    String json = "{\"x\":10,\"y\":20}";

    TestData data = mapper.readString(json, TestData.class);

    assertEquals(10, data.getX());
    assertEquals(20, data.getY());
  }
  @Test
  void testLocalDateTimeDeserialization_ReturnsTimestamp_WhenStringIsNotNull() {
    String json = "\"2020-12-31T12:00:00\"";

    LocalDateTime d = mapper.readString(json, LocalDateTime.class);

    LocalDateTime expected = LocalDateTime.of(2020, 12, 31, 12, 0, 0);
    assertEquals(expected, d);
  }

  @Test
  void testLocalDateTimeSerialization_ReturnsISOTimestamp_WhenDateIsNotNull() {
    LocalDateTime date = LocalDateTime.of(2020, 12, 31, 12, 0, 0);

    String json = mapper.writeString(date);

    assertEquals("\"2020-12-31T12:00:00\"", json);
  }

  @Test
      throws JsonProcessingException {
    doThrow(JsonProcessingException.class).when(mObjectMapper).writeValueAsString(any());

    class OverrideToStringWithJackson extends TestData {
      @Override
      public String toString() {
        return mapper.writeString(this);
      }
    }
    RuntimeException exception = assertThrows(RuntimeException.class, () -> mapper.writeString(o));
  }
}
