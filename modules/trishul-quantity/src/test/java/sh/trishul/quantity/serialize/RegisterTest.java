package sh.trishul.quantity.serialize;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.lang.reflect.Constructor;
import java.math.BigDecimal;
import javax.measure.Quantity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import sh.trishul.model.json.JacksonJsonMapper;
import sh.trishul.model.json.JsonMapper;
import sh.trishul.quantity.unit.SupportedUnits;
import tec.uom.se.quantity.Quantities;

class RegisterTest {
  @BeforeEach
  void resetMapper() {
    JacksonJsonMapper instance = (JacksonJsonMapper) JsonMapper.INSTANCE;
    instance.module = new SimpleModule();
    instance.mapper = new ObjectMapper().configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
        .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
        .registerModule(new JavaTimeModule());
  }

  @Test
  void testRegisterInit() {
    Register.init();

    Quantity<?> quantity = Quantities.getQuantity(new BigDecimal("10.5"), SupportedUnits.GRAM);
    String json = JsonMapper.INSTANCE.writeString(quantity);

    assertNotNull(json);
    assertEquals("{\"symbol\":\"g\",\"value\":10.5}", json);

    Quantity<?> deserialized = JsonMapper.INSTANCE.readString(json, Quantity.class);
    assertEquals(quantity, deserialized);
  }

  @Test
  void testInit_WithMock_RegistersAndCallsRegisterModule() {
    JacksonJsonMapper mockMapper = mock(JacksonJsonMapper.class);
    Register.init(mockMapper);

    verify(mockMapper).addSerializer(eq(Quantity.class), any());
    verify(mockMapper).addDeserializer(eq(Quantity.class), any());
    verify(mockMapper).registerModule();
  }

  @Test
  void testInit_DelegatesToOverloadedInit() {
    try (MockedStatic<Register> mocked = mockStatic(Register.class)) {
      mocked.when(Register::init).thenCallRealMethod();
      Register.init();
      mocked.verify(() -> Register.init(any(JacksonJsonMapper.class)));
    }
  }

  @Test
  void testPrivateConstructor() throws Exception {
    Constructor<Register> constructor = Register.class.getDeclaredConstructor();
    constructor.setAccessible(true);
    Register instance = constructor.newInstance();
    assertNotNull(instance);
  }
}
