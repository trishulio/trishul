package io.trishul.repo.jpa.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import java.net.URI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UriAttributeConverterTest {
  private UriAttributeConverter converter;

  @BeforeEach
  void setUp() {
    converter = new UriAttributeConverter();
  }

  @Test
  void testConvertToDatabaseColumn_returnsNull_whenUriIsNull() {
    assertNull(converter.convertToDatabaseColumn(null));
  }

  @Test
  void testConvertToDatabaseColumn_returnsString_whenUriIsNotNull() {
    URI uri = URI.create("http://localhost:8080");
    assertEquals("http://localhost:8080", converter.convertToDatabaseColumn(uri));
  }

  @Test
  void testConvertToEntityAttribute_returnsNull_whenStringIsNull() {
    assertNull(converter.convertToEntityAttribute(null));
  }

  @Test
  void testConvertToEntityAttribute_returnsUri_whenStringIsNotNull() {
    String s = "http://localhost:8080";
    assertEquals(URI.create(s), converter.convertToEntityAttribute(s));
  }
}
