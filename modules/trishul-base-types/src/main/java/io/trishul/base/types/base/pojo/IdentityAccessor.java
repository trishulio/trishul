package io.trishul.model.base.pojo;

public interface IdentityAccessor<T> extends Identified<T> {
    void setId(T id);
}
