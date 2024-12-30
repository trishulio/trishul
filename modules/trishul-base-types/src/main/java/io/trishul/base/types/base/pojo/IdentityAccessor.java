package io.trishul.base.types.base.pojo;

public interface IdentityAccessor<ID, T extends IdentityAccessor<ID, T>> extends Identified<ID> {
  T setId(ID id);
}
