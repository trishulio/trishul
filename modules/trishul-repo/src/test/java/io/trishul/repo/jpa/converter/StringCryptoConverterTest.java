package io.trishul.repo.jpa.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StringCryptoConverterTest {
  private StringCryptoConverter converter;

  @BeforeEach
  void setUp() {
    // AES/ECB/PKCS5Padding is a standard AES transformation supported by standard JVMs
    converter = new StringCryptoConverter("AES/ECB/PKCS5Padding", "my_secret_key_123");
  }

  @Test
  void testConvertToDatabaseColumn_returnsNull_whenAttributeIsNull() {
    assertNull(converter.convertToDatabaseColumn(null));
  }

  @Test
  void testConvertToDatabaseColumn_encryptsValue_whenAttributeIsNotNull() {
    String original = "hello_world";
    String encrypted = converter.convertToDatabaseColumn(original);
    assertNotNull(encrypted);

    // Decrypting should yield the original
    String decrypted = converter.convertToEntityAttribute(encrypted);
    assertEquals(original, decrypted);
  }

  @Test
  void testConvertToEntityAttribute_returnsNull_whenDbDataIsNull() {
    assertNull(converter.convertToEntityAttribute(null));
  }

  @Test
  void testConvertToDatabaseColumn_throwsRuntimeException_whenEncryptionFails() {
    // Use an invalid algorithm
    StringCryptoConverter invalidConverter = new StringCryptoConverter("INVALID_ALGO", "key");
    assertThrows(RuntimeException.class, () -> invalidConverter.convertToDatabaseColumn("data"));
  }

  @Test
  void testConvertToEntityAttribute_throwsRuntimeException_whenDecryptionFails() {
    // Use invalid encrypted data
    assertThrows(RuntimeException.class,
        () -> converter.convertToEntityAttribute("not_base64_encoded_and_invalid_ciphertext"));

    // Use an invalid algorithm
    StringCryptoConverter invalidConverter = new StringCryptoConverter("INVALID_ALGO", "key");
    assertThrows(RuntimeException.class,
        () -> invalidConverter.convertToEntityAttribute("base64data"));
  }

  @Test
  void testNoArgsConstructor_UsesDefaults() {
    StringCryptoConverter defaultConverter = new StringCryptoConverter();
    assertNotNull(defaultConverter);
    // Verify it functions correctly with default keys
    String original = "hello";
    String encrypted = defaultConverter.convertToDatabaseColumn(original);
    assertEquals(original, defaultConverter.convertToEntityAttribute(encrypted));
  }

  @Test
  void testConstructor_UsesKeyProvided() {
    StringCryptoConverter converter1
        = new StringCryptoConverter("AES/ECB/PKCS5Padding", "my_secret_key_123");
    StringCryptoConverter converter2
        = new StringCryptoConverter("AES/ECB/PKCS5Padding", "different_key_456");

    String plainText = "hello_world";
    String encrypted1 = converter1.convertToDatabaseColumn(plainText);
    String encrypted2 = converter2.convertToDatabaseColumn(plainText);

    assertNotEquals(encrypted1, encrypted2);
  }
}
