package io.trishul.base.types.base.pojo;

public interface OwnedByAccessor<T> {
    final String ATTR_OWNED_BY = "ownedBy";

    T getOwnedBy();

    void setOwnedBy(T user);
}
