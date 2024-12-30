package io.trishul.base.types.base.pojo;

public interface UpdatableEntity<ID, T extends UpdatableEntity<ID, T>>
    extends IdentityAccessor<ID, T>, Versioned {
}
