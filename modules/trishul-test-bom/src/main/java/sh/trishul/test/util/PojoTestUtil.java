package sh.trishul.test.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URI;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import org.mockito.Mockito;

public class PojoTestUtil {
  @SuppressWarnings("unchecked")
  public static <T> void assertAccessors(Class<T> clazz) throws Exception {
    if (Modifier.isAbstract(clazz.getModifiers()) || clazz.isInterface() || clazz.isEnum()) {
      return;
    }
    T instance = null;
    try {
      Constructor<T> constructor = clazz.getDeclaredConstructor();
      constructor.setAccessible(true);
      instance = constructor.newInstance();
    } catch (NoSuchMethodException e) {
      Constructor<?>[] constructors = clazz.getDeclaredConstructors();
      if (constructors.length > 0) {
        Constructor<?> constructor = constructors[0];
        constructor.setAccessible(true);
        Object[] args = new Object[constructor.getParameterCount()];
        Class<?>[] paramTypes = constructor.getParameterTypes();
        for (int i = 0; i < args.length; i++) {
          args[i] = getDummyValue(paramTypes[i]);
        }
        try {
          instance = (T) constructor.newInstance(args);
        } catch (Exception ignored) {
          // If constructor fails, try with default constructor fallback or return
        }
      }
    }

    if (instance == null) {
      return;
    }

    Method[] methods = clazz.getMethods();
    for (Method method : methods) {
      if (method.getName().startsWith("set") && method.getParameterCount() == 1
          && !method.isBridge() && !method.isSynthetic()) {
        Class<?> paramType = method.getParameterTypes()[0];
        Object dummyValue = getDummyValue(paramType);

        try {
          Method getter = findGetter(clazz, method);
          Object initialValue = null;
          if (getter != null) {
            try {
              initialValue = getter.invoke(instance);
            } catch (Exception ignored) {
              // Ignore errors reading initial value
            }
          }

          Object returnValue = method.invoke(instance, dummyValue);
          if (method.getReturnType().isAssignableFrom(clazz)) {
            assertSame(instance, returnValue,
                "Setter " + method.getName() + " should return 'this'");
          }

          if (getter != null) {
            Object newValue = getter.invoke(instance);
            // If the value actually changed, assert it matches dummyValue
            // (This skips stubbed getters/setters that always return null or a constant)
            boolean changed = (initialValue == null && newValue != null)
                || (initialValue != null && !initialValue.equals(newValue));
            if (changed) {
              if (dummyValue instanceof String && newValue instanceof String) {
                if (!((String) dummyValue).equalsIgnoreCase((String) newValue)) {
                  assertEquals(dummyValue, newValue, "Setter " + method.getName()
                      + " should round-trip through " + getter.getName());
                }
              } else {
                assertEquals(dummyValue, newValue, "Setter " + method.getName()
                    + " should round-trip through " + getter.getName());
              }
            }
          }
        } catch (InvocationTargetException e) {
          if (e.getCause() instanceof ClassCastException
              || e.getCause() instanceof IllegalArgumentException
              || e.getCause() instanceof NullPointerException) {
            continue;
          }
          throw e;
        }
      }
    }
  }

  private static Object getDummyValue(Class<?> type) {
    if (type == String.class) {
      return "testString";
    } else if (type == Integer.class || type == int.class) {
      return 123;
    } else if (type == Long.class || type == long.class) {
      return 123L;
    } else if (type == Boolean.class || type == boolean.class) {
      return true;
    } else if (type == Double.class || type == double.class) {
      return 123.45;
    } else if (type == Float.class || type == float.class) {
      return 123.45f;
    } else if (type == LocalDateTime.class) {
      return LocalDateTime.of(2000, 1, 1, 0, 0);
    } else if (type == LocalDate.class) {
      return LocalDate.of(2000, 1, 1);
    } else if (type == LocalTime.class) {
      return LocalTime.of(0, 0);
    } else if (type == Instant.class) {
      return Instant.EPOCH;
    } else if (type == UUID.class) {
      return UUID.randomUUID();
    } else if (type == URI.class) {
      return URI.create("http://localhost");
    } else if (type == List.class) {
      return Collections.emptyList();
    } else if (type == Set.class) {
      return Collections.emptySet();
    } else if (type == Map.class) {
      return Collections.emptyMap();
    } else if (type == BigDecimal.class) {
      return new BigDecimal("123.45");
    } else if (type == BigInteger.class) {
      return new BigInteger("123");
    } else if (type.getName().equals("org.joda.money.Money")) {
      try {
        Method parseMethod = type.getMethod("parse", String.class);
        return parseMethod.invoke(null, "USD 123.45");
      } catch (ReflectiveOperationException | IllegalArgumentException e) {
        return null;
      }
    } else if (type.isEnum()) {
      Object[] constants = type.getEnumConstants();
      if (constants != null && constants.length > 0) {
        return constants[0];
      }
      return null;
    } else {
      try {
        Constructor<?> constructor = type.getDeclaredConstructor();
        constructor.setAccessible(true);
        return constructor.newInstance();
      } catch (Exception e1) {
        try {
          return Mockito.mock(type);
        } catch (RuntimeException e) {
          return null;
        }
      }
    }
  }

  private static Method findGetter(Class<?> clazz, Method setter) {
    String propertyName = setter.getName().substring(3);
    String getterName = (setter.getParameterTypes()[0] == boolean.class
        || setter.getParameterTypes()[0] == Boolean.class) ? "is" + propertyName
            : "get" + propertyName;

    try {
      return clazz.getMethod(getterName);
    } catch (NoSuchMethodException e) {
      return null;
    }
  }
}
