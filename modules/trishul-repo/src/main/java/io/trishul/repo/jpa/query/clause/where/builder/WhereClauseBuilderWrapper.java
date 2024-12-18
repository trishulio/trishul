package io.trishul.repo.jpa.query.clause.where.builder;

import io.trishul.repo.jpa.query.spec.accumulator.PredicateSpecAccumulator;
import java.util.Collection;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;

public class WhereClauseBuilderWrapper implements WhereClauseBuilder {
    @SuppressWarnings("unused")
    private static final Logger log = LoggerFactory.getLogger(WhereClauseBuilderWrapper.class);

    private final WhereClauseBuilderDelegate delegate;

    public WhereClauseBuilderWrapper() {
        this(new WhereClauseBuilderDelegate(new PredicateSpecAccumulator()));
    }

    protected WhereClauseBuilderWrapper(WhereClauseBuilderDelegate delegate) {
        this.delegate = delegate;
    }

    @Override
    public WhereClauseBuilder isNull(String path) {
        this.delegate.isNull(new String[] {path});
        return this;
    }

    @Override
    public WhereClauseBuilder isNull(String[] paths) {
        this.delegate.isNull(paths);
        return this;
    }

    @Override
    public WhereClauseBuilder in(String[] paths, Collection<?> collection) {
        this.delegate.in(paths, collection);
        return this;
    }

    @Override
    public WhereClauseBuilder in(String path, Collection<?> collection) {
        this.delegate.in(new String[] {path}, collection);
        return this;
    }

    @Override
    public WhereClauseBuilder is(String path, Object value) {
        this.delegate.is(new String[] {path}, value);
        return this;
    }

    @Override
    public WhereClauseBuilder is(String[] paths, Object value) {
        this.delegate.is(paths, value);
        return this;
    }

    @Override
    public WhereClauseBuilder not() {
        this.delegate.not();
        return this;
    }

    @Override
    public WhereClauseBuilder like(String[] paths, Set<String> queries) {
        this.delegate.like(paths, queries);
        return this;
    }

    @Override
    public WhereClauseBuilder like(String path, Set<String> queries) {
        this.delegate.like(new String[] {path}, queries);
        return this;
    }

    @Override
    public <C extends Comparable<C>> WhereClauseBuilder between(String[] paths, C start, C end) {
        this.delegate.between(paths, start, end);
        return this;
    }

    @Override
    public <C extends Comparable<C>> WhereClauseBuilder between(String path, C start, C end) {
        this.delegate.between(new String[] {path}, start, end);
        return this;
    }

    @Override
    public WhereClauseBuilder predicate(Boolean bool) {
        this.delegate.predicate(bool);
        return this;
    }

    @Override
    public WhereClauseBuilder negatePredicate(Boolean bool) {
        bool = bool == null ? null : !bool;
        this.delegate.predicate(bool);
        return this;
    }

    @Override
    public <T> Specification<T> build() {
        return this.delegate.build();
    }
}
