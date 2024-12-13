package io.trishul.base.types.base.pojo;

public interface IdentityAccessor<T> extends Identified<T> {
    void setId(T id);
}
