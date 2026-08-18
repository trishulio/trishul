package sh.trishul.test.bom.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class DummyTest {
  @Test
  void testNoArgConstructor() {
    Dummy dummy = new Dummy();
    assertNull(dummy.getString());
    assertNull(dummy.getLongg());
    assertNull(dummy.getDoublee());
    assertNull(dummy.getInteger());
    assertNull(dummy.getCharacter());
    assertNull(dummy.getBool());
  }

  @Test
  void testAllArgConstructorAndGetters() {
    Dummy dummy = new Dummy("str", 10L, 5.5, 3, 'c', true);
    assertEquals("str", dummy.getString());
    assertEquals(10L, dummy.getLongg());
    assertEquals(5.5, dummy.getDoublee());
    assertEquals(3, dummy.getInteger());
    assertEquals('c', dummy.getCharacter());
    assertTrue(dummy.getBool());
  }

  @Test
  void testSetters() {
    Dummy dummy = new Dummy().setString("hello").setLongg(100L).setDoublee(1.23).setInteger(45)
        .setCharacter('z').setBool(true);

    assertEquals("hello", dummy.getString());
    assertEquals(100L, dummy.getLongg());
    assertEquals(1.23, dummy.getDoublee());
    assertEquals(45, dummy.getInteger());
    assertEquals('z', dummy.getCharacter());
    assertTrue(dummy.getBool());
  }
}
