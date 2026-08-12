package sh.trishul.test.util;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URI;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import org.junit.jupiter.api.Test;

public class PojoTestUtilTest {

  public static class SamplePojo {
    private String stringVal;
    private Integer intVal;
    private int primitiveIntVal;
    private Long longVal;
    private long primitiveLongVal;
    private Boolean boolVal;
    private boolean primitiveBoolVal;
    private Double doubleVal;
    private double primitiveDoubleVal;
    private Float floatVal;
    private float primitiveFloatVal;
    private LocalDateTime localDateTimeVal;
    private LocalDate localDateVal;
    private LocalTime localTimeVal;
    private Instant instantVal;
    private UUID uuidVal;
    private URI uriVal;
    private List<String> listVal;
    private Set<String> setVal;
    private Map<String, String> mapVal;
    private BigDecimal bigDecimalVal;
    private BigInteger bigIntegerVal;
    private SampleEnum enumVal;

    public SamplePojo setStringVal(String stringVal) {
      this.stringVal = stringVal;
      return this;
    }

    public SamplePojo setIntVal(Integer intVal) {
      this.intVal = intVal;
      return this;
    }

    public SamplePojo setPrimitiveIntVal(int primitiveIntVal) {
      this.primitiveIntVal = primitiveIntVal;
      return this;
    }

    public SamplePojo setLongVal(Long longVal) {
      this.longVal = longVal;
      return this;
    }

    public SamplePojo setPrimitiveLongVal(long primitiveLongVal) {
      this.primitiveLongVal = primitiveLongVal;
      return this;
    }

    public SamplePojo setBoolVal(Boolean boolVal) {
      this.boolVal = boolVal;
      return this;
    }

    public SamplePojo setPrimitiveBoolVal(boolean primitiveBoolVal) {
      this.primitiveBoolVal = primitiveBoolVal;
      return this;
    }

    public SamplePojo setDoubleVal(Double doubleVal) {
      this.doubleVal = doubleVal;
      return this;
    }

    public SamplePojo setPrimitiveDoubleVal(double primitiveDoubleVal) {
      this.primitiveDoubleVal = primitiveDoubleVal;
      return this;
    }

    public SamplePojo setFloatVal(Float floatVal) {
      this.floatVal = floatVal;
      return this;
    }

    public SamplePojo setPrimitiveFloatVal(float primitiveFloatVal) {
      this.primitiveFloatVal = primitiveFloatVal;
      return this;
    }

    public SamplePojo setLocalDateTimeVal(LocalDateTime localDateTimeVal) {
      this.localDateTimeVal = localDateTimeVal;
      return this;
    }

    public SamplePojo setLocalDateVal(LocalDate localDateVal) {
      this.localDateVal = localDateVal;
      return this;
    }

    public SamplePojo setLocalTimeVal(LocalTime localTimeVal) {
      this.localTimeVal = localTimeVal;
      return this;
    }

    public SamplePojo setInstantVal(Instant instantVal) {
      this.instantVal = instantVal;
      return this;
    }

    public SamplePojo setUuidVal(UUID uuidVal) {
      this.uuidVal = uuidVal;
      return this;
    }

    public SamplePojo setUriVal(URI uriVal) {
      this.uriVal = uriVal;
      return this;
    }

    public SamplePojo setListVal(List<String> listVal) {
      this.listVal = listVal;
      return this;
    }

    public SamplePojo setSetVal(Set<String> setVal) {
      this.setVal = setVal;
      return this;
    }

    public SamplePojo setMapVal(Map<String, String> mapVal) {
      this.mapVal = mapVal;
      return this;
    }

    public SamplePojo setBigDecimalVal(BigDecimal bigDecimalVal) {
      this.bigDecimalVal = bigDecimalVal;
      return this;
    }

    public SamplePojo setBigIntegerVal(BigInteger bigIntegerVal) {
      this.bigIntegerVal = bigIntegerVal;
      return this;
    }

    public SamplePojo setEnumVal(SampleEnum enumVal) {
      this.enumVal = enumVal;
      return this;
    }
  }

  public enum SampleEnum {
    VAL1
  }

  @Test
  public void testAssertAccessors() throws Exception {
    PojoTestUtil.assertAccessors(SamplePojo.class);
  }

  @Test
  public void testGetDummyValue() throws Exception {
    Method method = PojoTestUtil.class.getDeclaredMethod("getDummyValue", Class.class);
    method.setAccessible(true);

    Class<?>[] types = {String.class, Integer.class, int.class, Long.class, long.class,
        Boolean.class, boolean.class, Double.class, double.class, Float.class, float.class,
        LocalDateTime.class, LocalDate.class, LocalTime.class, Instant.class, UUID.class, URI.class,
        List.class, Set.class, Map.class, BigDecimal.class, BigInteger.class, SampleEnum.class,
        Runnable.class};

    for (Class<?> type : types) {
      Object value = method.invoke(null, type);
      assertNotNull(value, "Value for " + type.getName() + " should not be null");
    }
  }
}
