package io.trishul.repo.jpa.repository.model;

public interface Identified<T> {
    final String ATTR_ID = "id";

    T getId();
}
