package sh.trishul.repo.jpa.converter;

import jakarta.persistence.AttributeConverter;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class StringCryptoConverter implements AttributeConverter<String, String> {

  private static final Logger log = LoggerFactory.getLogger(StringCryptoConverter.class);

  private final String algorithm;
  private final SecretKeySpec secretKey;

  public StringCryptoConverter() {
    this(
        System.getenv("DB_ENCRYPTION_ALGORITHM") != null ? System.getenv("DB_ENCRYPTION_ALGORITHM")
            : "AES/ECB/PKCS5Padding",
        System.getenv("DB_ENCRYPTION_KEY") != null ? System.getenv("DB_ENCRYPTION_KEY")
            : "fake-encryption-key-for-test");
  }

  public StringCryptoConverter(@Value("${db.encryption.algorithm}") String algorithm,
      @Value("${db.encryption.key}") String encryptionKey) {
    this.algorithm = algorithm;

    // Ensure key is 16 bytes for AES-128 if not provided correctly
    byte[] keyBytes = new byte[16];
    byte[] sourceKeyBytes = encryptionKey.getBytes(StandardCharsets.UTF_8);
    System.arraycopy(sourceKeyBytes, 0, keyBytes, 0, Math.min(sourceKeyBytes.length, 16));
    this.secretKey = new SecretKeySpec(keyBytes, "AES");
  }

  @Override
  public String convertToDatabaseColumn(String attribute) {
    if (attribute == null) {
      return null;
    }
    try {
      Cipher cipher = Cipher.getInstance(algorithm);
      cipher.init(Cipher.ENCRYPT_MODE, secretKey);
      return Base64.getEncoder()
          .encodeToString(cipher.doFinal(attribute.getBytes(StandardCharsets.UTF_8)));
    } catch (Exception e) {
      log.error("Error encrypting attribute", e);
      throw new RuntimeException("Error encrypting attribute", e);
    }
  }

  @Override
  public String convertToEntityAttribute(String dbData) {
    if (dbData == null) {
      return null;
    }
    try {
      Cipher cipher = Cipher.getInstance(algorithm);
      cipher.init(Cipher.DECRYPT_MODE, secretKey);
      return new String(cipher.doFinal(Base64.getDecoder().decode(dbData)), StandardCharsets.UTF_8);
    } catch (Exception e) {
      log.error("Error decrypting attribute", e);
      throw new RuntimeException("Error decrypting attribute", e);
    }
  }
}
