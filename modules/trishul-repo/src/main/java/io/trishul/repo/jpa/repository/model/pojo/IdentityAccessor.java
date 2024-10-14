package io.trishul.repo.jpa.repository.model.pojo;

public interface IdentityAccessor<T> extends Identified<T> {
    void setId(T id);
}
