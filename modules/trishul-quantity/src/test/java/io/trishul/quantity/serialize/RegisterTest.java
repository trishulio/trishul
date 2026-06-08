package io.trishul.quantity.serialize;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import java.lang.reflect.Constructor;

class RegisterTest {
  @Test
  void testRegisterInit() {
    Register.init();
  }

  @Test
  void testPrivateConstructor() throws Exception {
    Constructor<Register> constructor = Register.class.getDeclaredConstructor();
    constructor.setAccessible(true);
    Register instance = constructor.newInstance();
    assertNotNull(instance);
  }
}
