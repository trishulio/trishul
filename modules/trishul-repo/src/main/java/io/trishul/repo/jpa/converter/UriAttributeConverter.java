package io.trishul.repo.jpa.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.net.URI;

@Converter(autoApply = true)
public class UriAttributeConverter implements AttributeConverter<URI, String> {
  @Override
  public String convertToDatabaseColumn(URI uri) {
    return uri == null ? null : uri.toString();
  }

  @Override
  public URI convertToEntityAttribute(String s) {
    return s == null ? null : URI.create(s);
  }
}
