package io.trishul.model.reflection;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.ImmutableSet;
import io.trishul.base.types.lambda.CheckedFunction;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import javax.annotation.Nonnull;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReflectionManipulator {
    private static final Logger logger = LoggerFactory.getLogger(ReflectionManipulator.class);

    static class PropNameKey {
        private Class<?> clazz;
        private Set<String> exclusions;

        public PropNameKey(Class<?> clazz, Set<String> exclusions) {
            this.clazz = clazz;
            this.exclusions = exclusions;
        }

        public Class<?> getClazz() {
            return this.clazz;
        }

        public void setClazz(Class<?> clazz) {
            this.clazz = clazz;
        }

        public Set<String> getExclusions() {
            return this.exclusions;
        }

        public void setExclusions(Set<String> exclusions) {
            this.exclusions = exclusions;
        }

        @Override
        public boolean equals(Object o) {
            return EqualsBuilder.reflectionEquals(this, o);
        }

        @Override
        public int hashCode() {
            return super.hashCode();
        }
    }

    private final LoadingCache<Class<?>, Set<String>> propNamesCache;
    private final LoadingCache<PropNameKey, Set<String>> propNamesCacheWithExclusions;

    public boolean equals(Object o, Object that) {
        return EqualsBuilder.reflectionEquals(o, that);
    }

    public int hashCode(Object o) {
        return HashCodeBuilder.reflectionHashCode(o, false);
    }

    public static final ReflectionManipulator INSTANCE = new ReflectionManipulator();

    public ReflectionManipulator() {
        this.propNamesCache =
                CacheBuilder.newBuilder()
                        .build(
                                new CacheLoader<Class<?>, Set<String>>() {
                                    @Override
                                    public Set<String> load(@Nonnull Class<?> clazz)
                                            throws Exception {
                                        final Method[] methods = clazz.getMethods();
                                        final Set<String> propertyNames =
                                                Arrays.stream(methods)
                                                        .filter(
                                                                m ->
                                                                        m.getName()
                                                                                        .startsWith(
                                                                                                "get")
                                                                                || m.getName()
                                                                                        .startsWith(
                                                                                                "set"))
                                                        .map(
                                                                method ->
                                                                        method.getName()
                                                                                .replaceFirst(
                                                                                        "get|set",
                                                                                        ""))
                                                        .map(
                                                                prop -> {
                                                                    final char c =
                                                                            Character.toLowerCase(
                                                                                    prop.charAt(0));
                                                                    final String l =
                                                                            Character.toString(c);
                                                                    return prop.replaceFirst(
                                                                            "\\w", l);
                                                                })
                                                        .collect(ImmutableSet.toImmutableSet());
                                        return propertyNames;
                                    }
                                });

        this.propNamesCacheWithExclusions =
                CacheBuilder.newBuilder()
                        .build(
                                new CacheLoader<PropNameKey, Set<String>>() {
                                    @Override
                                    public Set<String> load(@Nonnull PropNameKey key)
                                            throws Exception {
                                        Set<String> propNames =
                                                ReflectionManipulator.this.propNamesCache.get(
                                                        key.getClazz());

                                        if (key.getExclusions() != null) {
                                            propNames =
                                                    propNames.stream()
                                                            .filter(
                                                                    prop ->
                                                                            !key.getExclusions()
                                                                                    .contains(prop))
                                                            .collect(ImmutableSet.toImmutableSet());
                                        }

                                        return propNames;
                                    }
                                });
    }

    public void copy(
            Object o1,
            Object o2,
            CheckedFunction<Boolean, PropertyDescriptor, ReflectiveOperationException> predicate) {
        if (o1 == null || o2 == null) {
            throw new NullPointerException("Outer Joins can not be on null objects");
        }

        try {
            final PropertyDescriptor[] pds =
                    Introspector.getBeanInfo(o2.getClass(), Object.class).getPropertyDescriptors();

            for (final PropertyDescriptor pd : pds) {
                final Method getter = pd.getReadMethod();
                final Method setter = pd.getWriteMethod();

                if (getter == null || setter == null) {
                    continue;
                }

                final boolean pass = predicate.apply(pd);

                if (pass) {
                    final Object value = getter.invoke(o2);
                    setter.invoke(o1, value);
                }
            }
        } catch (final IntrospectionException e) {
            final String msg =
                    String.format("Failed to introspect object because: %s", e.getMessage());
            this.handleException(msg, e);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            final String msg =
                    String.format(
                            "Failed to access the value using dynamic method because: %s",
                            e.getMessage());
            this.handleException(msg, e);
        } catch (final ReflectiveOperationException e) {
            final String msg =
                    String.format("Failed to execute the predicate because: %s", e.getMessage());
            this.handleException(msg, e);
        }
    }

    public Set<String> getPropertyNames(Class<?> clazz, Set<String> exclusions) {
        if (clazz == null) {
            return null;
        }

        final PropNameKey key = new PropNameKey(clazz, exclusions);
        try {
            return this.propNamesCacheWithExclusions.get(key);

        } catch (final ExecutionException e) {
            throw new RuntimeException(
                    String.format("Failed to fetch properties names because: %s", e.getMessage()),
                    e);
        }
    }

    public <T> T construct(Class<T> clazz, Map<String, Object> props) {
        if (clazz == null) {
            return null;
        }

        T obj = null;
        try {
            final Constructor<T> constructor = clazz.getConstructor();
            obj = constructor.newInstance();

            final PropertyDescriptor[] pds =
                    Introspector.getBeanInfo(clazz, Object.class).getPropertyDescriptors();
            for (final PropertyDescriptor pd : pds) {
                if (props.containsKey(pd.getName())) {
                    final Method setter = pd.getWriteMethod();
                    setter.invoke(obj, props.get(pd.getName()));
                }
            }
        } catch (final IntrospectionException e) {
            final String msg =
                    String.format("Failed to introspect object because: %s", e.getMessage());
            this.handleException(msg, e);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            final String msg =
                    String.format(
                            "Failed to access the value using dynamic method because: %s",
                            e.getMessage());
            this.handleException(msg, e);
        } catch (final ReflectiveOperationException e) {
            final String msg =
                    String.format("Failed to execute the predicate because: %s", e.getMessage());
            this.handleException(msg, e);
        }

        return obj;
    }

    public <T> T construct(Class<T> clazz) {
        return this.construct(clazz, new HashMap<>());
    }

    private void handleException(String msg, Exception e) {
        logger.error(msg);
        throw new RuntimeException(msg, e);
    }
}
