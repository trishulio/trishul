package sh.trishul.model.json;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.util.MimeType;

class MimeTypeDeserializerTest {
  private ObjectMapper mapper;

  @BeforeEach
  void init() {
    mapper = new ObjectMapper();
    SimpleModule module = new SimpleModule();
    module.addDeserializer(MimeType.class, new MimeTypeDeserializer());
    mapper.registerModule(module);
  }

  @Test
  void testDeserialize_FromString() throws IOException {
    MimeType mimeType = mapper.readValue("\"image/png\"", MimeType.class);
    assertNotNull(mimeType);
    assertEquals("image", mimeType.getType());
    assertEquals("png", mimeType.getSubtype());
  }

  @Test
  void testDeserialize_FromObjectWithoutParameters() throws IOException {
    String json = "{\"type\":\"image\",\"subtype\":\"jpeg\"}";
    MimeType mimeType = mapper.readValue(json, MimeType.class);
    assertNotNull(mimeType);
    assertEquals("image", mimeType.getType());
    assertEquals("jpeg", mimeType.getSubtype());
  }

  @Test
  void testDeserialize_FromObjectWithParameters() throws IOException {
    String json
        = "{\"type\":\"text\",\"subtype\":\"plain\",\"parameters\":{\"charset\":\"utf-8\"}}";
    MimeType mimeType = mapper.readValue(json, MimeType.class);
    assertNotNull(mimeType);
    assertEquals("text", mimeType.getType());
    assertEquals("plain", mimeType.getSubtype());
    assertEquals("utf-8", mimeType.getParameter("charset"));
  }

  @Test
  void testDeserialize_ThrowsException_ForInvalidType() {
    assertThrows(Exception.class, () -> {
      mapper.readValue("123", MimeType.class);
    });
  }
}
