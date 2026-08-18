package sh.trishul.model.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.util.MimeType;
import org.springframework.util.MimeTypeUtils;

@JsonComponent
public class MimeTypeDeserializer extends JsonDeserializer<MimeType> {
  @Override
  public MimeType deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
    if (p.hasToken(JsonToken.VALUE_STRING)) {
      String value = p.getText();
      return MimeTypeUtils.parseMimeType(value);
    }

    if (p.hasToken(JsonToken.START_OBJECT)) {
      JsonNode node = p.getCodec().readTree(p);
      String type = node.has("type") ? node.get("type").asText() : "";
      String subtype = node.has("subtype") ? node.get("subtype").asText() : "";

      Map<String, String> parameters = null;
      if (node.has("parameters")) {
        JsonNode paramsNode = node.get("parameters");
        if (paramsNode.isObject()) {
          parameters = new HashMap<>();
          Iterator<Map.Entry<String, JsonNode>> fields = paramsNode.fields();
          while (fields.hasNext()) {
            Map.Entry<String, JsonNode> field = fields.next();
            parameters.put(field.getKey(), field.getValue().asText());
          }
        }
      }

      if (parameters != null) {
        return new MimeType(type, subtype, parameters);
      } else {
        return new MimeType(type, subtype);
      }
    }

    throw ctxt.instantiationException(MimeType.class,
        "Expected string or object representing MimeType");
  }
}
