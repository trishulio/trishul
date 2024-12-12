package io.trishul.model.base.pojo.refresher.accessor;

public interface OwnedByAccessor<T> {
    final String ATTR_OWNED_BY = "ownedBy";

    T getOwnedBy();

    void setOwnedBy(T user);
}
