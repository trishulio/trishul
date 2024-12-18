package io.trishul.repo.jpa.query.join.joiner;

import io.trishul.model.base.pojo.BaseModel;
import jakarta.persistence.criteria.From;
import jakarta.persistence.criteria.Path;

public class JpaJoinerCachingProxy implements JpaJoiner {
    private final JpaJoinerLocalCache cache;
    private final JpaJoiner cjProcessor;

    public JpaJoinerCachingProxy(JpaJoinerLocalCache cache, JpaJoiner cjProcessor) {
        this.cache = cache;
        this.cjProcessor = cjProcessor;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <X, Y> From<X, Y> join(From<X, Y> join, String fieldName) {
        Key<X, Y> key = new Key<>(join, fieldName);

        return (From<X, Y>)
                this.cache.get(key, () -> (Path<X>) this.cjProcessor.join(join, fieldName));
    }

    @Override
    public <X, Y> Path<X> get(From<X, Y> join, String fieldName) {
        Key<X, Y> key = new Key<>(join, fieldName);

        return this.cache.get(key, () -> this.cjProcessor.get(join, fieldName));
    }
}

class Key<X, Y> extends BaseModel {
    @SuppressWarnings("unused")
    private final From<X, Y> join;

    @SuppressWarnings("unused")
    private final String fieldName;

    public Key(From<X, Y> join, String fieldName) {
        this.join = join;
        this.fieldName = fieldName;
    }
}
