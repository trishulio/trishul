package io.trishul.model.validator;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Validator {
    private static final Logger logger = LoggerFactory.getLogger(Validator.class);

    private final List<String> errors;

    public Validator() {
        this.errors = new ArrayList<>(0);
    }

//    public void rule(Supplier<Boolean> test, String err) {
//        rule(test.get(), err);
//    }
    public boolean hasErrors() {
        return !this.errors.isEmpty();
    }

    public boolean rule(boolean pass, String err, Object... args) {
        if (!pass) {
            this.errors.add(String.format(err, args));
        }

        return pass;
    }

    public void raiseErrors() {
        String err = concatIntoNumberedList(this.errors);
        assertion(this.errors.isEmpty(), ValidationException.class, err);
    }

    public static void assertion(boolean pass, Class<? extends RuntimeException> clazz, Object... args) {
        if (!pass) {
            try {
                Class<?>[] argClasses = new Class[args.length];
                argClasses = Arrays.stream(args).map(arg -> arg.getClass()).toList().toArray(argClasses);

                Constructor<? extends RuntimeException> constructor = clazz.getDeclaredConstructor(argClasses);
                RuntimeException e = constructor.newInstance(args);
                throw e;

            } catch (NoSuchMethodException | SecurityException e) {
                String err = String.format("Failed to load the constructor with String parameter for class: '%s' because '%s'", clazz.getName(), e.getMessage());
                logger.error(err);
                throw new IllegalStateException(err, e);

            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                String err = String.format("Failed to instantiate class: '%s' because '%s'", clazz.getName(), e.getMessage());
                logger.error(err);
                throw new IllegalStateException(err, e);
            }
        }
    }

//    public void assertion(Supplier<Boolean> test, Class<? extends RuntimeException> clazz, Object... args) {
//        boolean pass = test.get();
//        assertion(pass, clazz, args);
//    }

    private static String concatIntoNumberedList(List<String> msgs) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < msgs.size(); i++) {
            sb.append(i + 1);
            sb.append(". ");
            sb.append(msgs.get(i));
            sb.append(System.lineSeparator());
        }

        return sb.toString();
    }
}
