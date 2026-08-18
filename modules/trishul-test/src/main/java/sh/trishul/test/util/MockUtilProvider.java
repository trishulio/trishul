package sh.trishul.test.util;

import sh.trishul.model.reflection.ReflectionManipulator;

public class MockUtilProvider extends ReflectionManipulator {
  public static final MockUtilProvider INSTANCE = new MockUtilProvider();
}
