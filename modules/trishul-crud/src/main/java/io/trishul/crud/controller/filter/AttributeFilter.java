package io.trishul.crud.controller.filter;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AttributeFilter {
    private static final Logger logger = LoggerFactory.getLogger(AttributeFilter.class);

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
                if (!predicate.apply(pd.getName())) {
                    continue;
                }

                Method setter = pd.getWriteMethod();
                if (setter != null) {
                    setter.invoke(o, NULL_VALUE);
                }
            }
        } catch (IntrospectionException e) {
            String msg = String.format("Failed to get the property descriptors for the the object");
            logger.error(msg);
            throw new RuntimeException(msg, e.getCause());
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            String msg = String.format("Failed to dynamically call setter because: %s", e.getMessage());
            logger.error(msg);
            throw new RuntimeException(msg, e.getCause());
        }
    }
}
