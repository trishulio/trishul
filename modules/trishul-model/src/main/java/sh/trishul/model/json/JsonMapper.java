package sh.trishul.model.json;

import com.fasterxml.jackson.databind.ObjectMapper;

public interface JsonMapper {
  JsonMapper INSTANCE = new JacksonJsonMapper(new ObjectMapper());

  <T> String writeString(T o);

  <T> T readString(String json, Class<T> clazz);
}
