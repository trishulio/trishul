package io.trishul.model.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class JacksonJsonMapper implements JsonMapper {
  public ObjectMapper mapper;
  public SimpleModule module;

  public JacksonJsonMapper(ObjectMapper mapper) {
    this.module = new SimpleModule();
    this.mapper = mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
        .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
        .registerModule(new JavaTimeModule());
  }

  public <T> SimpleModule addSerializer(Class<? extends T> type, JsonSerializer<T> serializer) {
    return this.module.addSerializer(type, serializer);
  }

  public <T> SimpleModule addDeserializer(Class<T> type,
      JsonDeserializer<? extends T> deserializer) {
    return this.module.addDeserializer(type, deserializer);
  }

  public void registerModule() {
    this.mapper.registerModule(module);
    this.module = new SimpleModule();
  }

  @Override
  public <T> String writeString(T o) {
    try {
      return this.mapper.writeValueAsString(o);
    } catch (final JsonProcessingException e) {
      throw new RuntimeException(String.format(
          "Failed to serialize object of class: '%s' because %s", o.getClass(), e.getMessage()), e);
    }
  }

  @Override
  public <T> T readString(String json, Class<T> clazz) {
    try {
      return this.mapper.readValue(json, clazz);
    } catch (final JsonProcessingException e) {
      throw new RuntimeException(
          String.format("Failed to de-serialize string: '%s' because %s", json, e.getMessage()), e);
    }
  }
}
