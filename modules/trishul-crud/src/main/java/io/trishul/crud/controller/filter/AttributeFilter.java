package io.trishul.crud.controller.filter;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.Set;
import java.util.function.Function;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.trishul.model.reflection.ReflectionManipulator;

public class AttributeFilter {
  private static final Logger log = LoggerFactory.getLogger(AttributeFilter.class);

  private static final ReflectionManipulator util = ReflectionManipulator.INSTANCE;

  public void retain(Object o, Set<String> attributes) {
    setNullValueOnProps(o, propName -> !attributes.contains(propName));
  }

  public void remove(Object o, Set<String> attributes) {
    setNullValueOnProps(o, propName -> attributes.contains(propName));
  }

  private void setNullValueOnProps(Object o, Function<String, Boolean> predicate) {
    try {
      final Object NULL_VALUE = null;
      PropertyDescriptor[] pds = Introspector.getBeanInfo(o.getClass()).getPropertyDescriptors();
      for (PropertyDescriptor pd : pds) {
        String propertyName = pd.getName();
        if (!predicate.apply(propertyName)) {
          continue;
        }

        util.invokeSetter(o, pd, NULL_VALUE);
      }
    } catch (IntrospectionException e) {
      String msg = String.format("Failed to get the property descriptors for the the object");
      log.error(msg);
      throw new RuntimeException(msg, e.getCause());
    } catch (IllegalArgumentException e) {
      String msg = String.format("Failed to dynamically call setter because: %s", e.getMessage());
      log.error(msg);
      throw new RuntimeException(msg, e.getCause());
    }
  }
}
