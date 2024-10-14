package io.trishul.repo.jpa.repository.refresher;

import java.util.Collection;

public interface Refresher<E, A> {
    void refresh(Collection<E> entities);

    void refreshAccessors(Collection<? extends A> accessors);
}
