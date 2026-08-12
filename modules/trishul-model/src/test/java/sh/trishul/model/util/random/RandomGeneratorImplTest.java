package sh.trishul.model.util.random;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.security.SecureRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sh.trishul.base.types.util.random.RandomGenerator;

class RandomGeneratorImplTest {
  private RandomGenerator generator;

  private SecureRandom mRandom;

  @BeforeEach
  void init() {
    // Manually extending the class because Mockito cannot mock
    // Random class due to one of it's properties.
    class RandomMock extends SecureRandom {
      int i = 1;

      @Override
      public int nextInt(int l) {
        int r = i;
        i = (i * 2) % l;
        return r;
      }
    }

    mRandom = new RandomMock();
    generator = new RandomGeneratorImpl(mRandom);
  }

  @Test
  void testConstructor_UsesNewSecureRandom_WhenNullPassed() {
    generator = new RandomGeneratorImpl(null);
    String s = generator.string(10);
    assertEquals(10, s.length());
  }

  @Test
  void testString_ReturnsString_WithAlphaNumAndSpecialChars() {
    String s = generator.string(50);

    assertEquals(50, s.length());
    assertEquals("BCEIQg#wQg#wQg#wQg#wQg#wQg#wQg#wQg#wQg#wQg#wQg#wQg", s);
  }

  @Test
  void testString_ReturnsEmptyString_WhenLengthIsZero() {
    String s = generator.string(0);
    assertEquals(0, s.length());
    assertEquals("", s);
  }
}
