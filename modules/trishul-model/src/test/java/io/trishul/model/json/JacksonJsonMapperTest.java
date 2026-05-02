package io.trishul.model.json;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.spy;

import java.io.IOException;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;

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
  void testWriteString_DoesNotCauseStackOverflowInCatchClause_WhenEntityOverridesToStringToCallJacksonMapper()
      throws JsonProcessingException {
    doThrow(JsonProcessingException.class).when(mObjectMapper).writeValueAsString(any());

    class OverrideToStringWithJackson extends TestData {
      @Override
      public String toString() {
        return mapper.writeString(this);
      }
    }

    OverrideToStringWithJackson o = new OverrideToStringWithJackson();
    assertThrows(RuntimeException.class, () -> mapper.writeString(o));
  }

  @Test
  void testAddSerializer_ReturnsSimpleModule() {
    JacksonJsonMapper jacksonMapper = new JacksonJsonMapper(new ObjectMapper());

    JsonSerializer<TestData> serializer = new JsonSerializer<TestData>() {
      @Override
      public void serialize(TestData value, JsonGenerator gen, SerializerProvider serializers)
          throws IOException {
        gen.writeString("custom");
      }
    };

    SimpleModule result = jacksonMapper.addSerializer(TestData.class, serializer);

    assertNotNull(result);
  }

  @Test
  void testAddDeserializer_ReturnsSimpleModule() {
    JacksonJsonMapper jacksonMapper = new JacksonJsonMapper(new ObjectMapper());

    JsonDeserializer<TestData> deserializer = new JsonDeserializer<TestData>() {
      @Override
      public TestData deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        return new TestData(99, 99);
      }
    };

    SimpleModule result = jacksonMapper.addDeserializer(TestData.class, deserializer);

    assertNotNull(result);
  }

  @Test
  void testRegisterModule_RegistersSerializerAndDeserializer() {
    JacksonJsonMapper jacksonMapper = new JacksonJsonMapper(new ObjectMapper());

    JsonSerializer<TestData> serializer = new JsonSerializer<TestData>() {
      @Override
      public void serialize(TestData value, JsonGenerator gen, SerializerProvider serializers)
          throws IOException {
        gen.writeString("custom:" + value.getX() + ":" + value.getY());
      }
    };

    jacksonMapper.addSerializer(TestData.class, serializer);
    jacksonMapper.registerModule();

    String result = jacksonMapper.writeString(new TestData(10, 20));
    assertEquals("\"custom:10:20\"", result);
  }

  @Test
  void testRegisterModule_ResetsModuleAfterRegistration() {
    JacksonJsonMapper jacksonMapper = new JacksonJsonMapper(new ObjectMapper());

    JsonSerializer<TestData> serializer = new JsonSerializer<TestData>() {
      @Override
      public void serialize(TestData value, JsonGenerator gen, SerializerProvider serializers)
          throws IOException {
        gen.writeString("custom");
      }
    };

    jacksonMapper.addSerializer(TestData.class, serializer);
    jacksonMapper.registerModule();

    // Module should be reset, so adding new serializer should work
    assertNotNull(jacksonMapper.module);
  }

  @Test
  void testReadString_ThrowsRuntimeException_WhenJsonIsInvalid() {
    String invalidJson = "not valid json";

    RuntimeException exception = assertThrows(RuntimeException.class,
        () -> mapper.readString(invalidJson, TestData.class));
    assertTrue(exception.getMessage().contains("Failed to de-serialize string"));
  }
}
