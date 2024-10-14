package io.trishul.repo.jpa.query.join.joiner;

import javax.persistence.criteria.From;
import javax.persistence.criteria.Path;

import io.trishul.model.base.pojo.BaseModel;

public class JpaJoinerCachingProxy implements JpaJoiner {
    private JpaJoinerLocalCache cache;
    private JpaJoiner cjProcessor;

    public JpaJoinerCachingProxy(JpaJoinerLocalCache cache, JpaJoiner cjProcessor) {
        this.cache = cache;
        this.cjProcessor = cjProcessor;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <X, Y> From<X, Y> join(From<X, Y> join, String fieldName) {
        Key<X, Y> key = new Key<>(join, fieldName);

        return (From<X, Y>) this.cache.get(key, () -> (Path<X>) this.cjProcessor.join(join, fieldName));
    }

    @Override
    public <X, Y> Path<X> get(From<X, Y> join, String fieldName) {
        Key<X, Y> key = new Key<>(join, fieldName);

        return this.cache.get(key, () -> this.cjProcessor.get(join, fieldName));
    }
}

class Key<X, Y> extends BaseModel {
    @SuppressWarnings("unused")
    private From<X, Y> join;
    @SuppressWarnings("unused")
    private String fieldName;

    public Key(From<X, Y> join, String fieldName) {
        this.join = join;
        this.fieldName = fieldName;
    }
}