package io.trishul.repo.jpa.query.join.joiner;

import jakarta.persistence.criteria.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class JpaJoinerLocalCache {
    private final ThreadLocal<Map<Key<?, ?>, Path<?>>> cacheHolder;

    public JpaJoinerLocalCache() {
        this.cacheHolder = ThreadLocal.withInitial(() -> new HashMap<>());
    }

    @SuppressWarnings("unchecked")
    public <X, Y> Path<X> get(Key<X, Y> key, Supplier<Path<X>> pathSupplier) {
        Path<X> attribute = null;
        Map<Key<?, ?>, Path<?>> cache = cacheHolder.get();

        if (!cache.containsKey(key)) {
            attribute = pathSupplier.get();
            cache.put(key, attribute);
        } else {
            attribute = (Path<X>) cache.get(key);
        }

        return attribute;
    }
}
