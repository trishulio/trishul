package io.trishul.model.util.random;

import com.google.common.collect.ImmutableList;
import io.trishul.base.types.util.random.RandomGenerator;
import java.security.SecureRandom;
import java.util.List;

public class RandomGeneratorImpl implements RandomGenerator {
    private static final String CAPITAL_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWERCASE_CHARS = CAPITAL_CHARS.toLowerCase();
    private static final String DIGITS = "0123456789";
    private static final String SYMBOLS = "!@#$%^&*_=+-/.?<>)";
    private static final List<Character> ALL_CHARS;

    static {
        String allChars = CAPITAL_CHARS + LOWERCASE_CHARS + DIGITS + SYMBOLS;
        ALL_CHARS =
                allChars.chars().mapToObj(c -> (char) c).collect(ImmutableList.toImmutableList());
    }

    private final SecureRandom random;

    public RandomGeneratorImpl(SecureRandom random) {
        // Defensive copying: Use a new SecureRandom instance if none is provided
        this.random = (random != null) ? random : new SecureRandom();
    }

    @Override
    public String string(int len) {
        List<Character> charSet = ALL_CHARS;

        char[] password = new char[len];
        for (int i = 0; i < len; i++) {
            password[i] = charSet.get(this.random.nextInt(charSet.size()));
        }

        return new String(password);
    }
}
