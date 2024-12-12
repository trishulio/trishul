package io.trishul.model.base.pojo;

public interface Identified<T> {
    final String ATTR_ID = "id";

    T getId();
}
