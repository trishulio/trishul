package io.trishul.repo.jpa.repository.model.pojo;

public interface Identified<T> {
    final String ATTR_ID = "id";

    T getId();
}
