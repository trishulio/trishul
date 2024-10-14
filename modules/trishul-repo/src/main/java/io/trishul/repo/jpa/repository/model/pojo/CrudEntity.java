package io.trishul.repo.jpa.repository.model.pojo;

import java.util.Set;

public interface CrudEntity<ID> extends UpdatableEntity<ID> {
    void outerJoin(Object other);

    void outerJoin(Object other, Set<String> include);

    void override(Object other);

    void override(Object other, Set<String> include);
}
