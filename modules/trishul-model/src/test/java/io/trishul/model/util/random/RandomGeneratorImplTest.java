package io.trishul.model.util.random;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.trishul.base.types.util.random.RandomGenerator;

public class RandomGeneratorImplTest {
    private RandomGenerator generator;

    private Random mRandom;

    @BeforeEach
    public void init() {
        // Manually extending the class because Mockito cannot mock
        // java.util.Random class due to one of it's properties.
        class RandomMock extends Random {
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
    public void testString_ReturnsString_WithAlphaNumAndSpecialChars() {
        String s = generator.string(50);

        assertEquals(50, s.length());
        assertEquals("BCEIQg#wQg#wQg#wQg#wQg#wQg#wQg#wQg#wQg#wQg#wQg#wQg", s);
    }
}
