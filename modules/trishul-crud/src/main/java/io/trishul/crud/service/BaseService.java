package io.trishul.crud.service;

import io.trishul.model.reflection.ReflectionManipulator;
import java.util.Set;

public class BaseService {
    protected ReflectionManipulator util;

    public BaseService() {
        this(ReflectionManipulator.INSTANCE);
    }

    protected BaseService(ReflectionManipulator util) {
        this.util = util;
    }

    public Set<String> getPropertyNames(Class<?> clazz) {
        return this.getPropertyNames(clazz, null);
    }

    public Set<String> getPropertyNames(Class<?> clazz, Set<String> exclusions) {
        return this.util.getPropertyNames(clazz, exclusions);
    }
}
