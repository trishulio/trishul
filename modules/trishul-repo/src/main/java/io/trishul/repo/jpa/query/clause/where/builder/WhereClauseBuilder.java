package io.trishul.repo.jpa.query.clause.where.builder;

import java.util.Collection;
import java.util.Set;

import org.springframework.data.jpa.domain.Specification;

public interface WhereClauseBuilder {
    static WhereClauseBuilder builder() {
        return new WhereClauseBuilderWrapper();
    }

    WhereClauseBuilder isNull(String path);

    WhereClauseBuilder isNull(String[] paths);

    WhereClauseBuilder in(String[] paths, Collection<?> collection);

    WhereClauseBuilder in(String path, Collection<?> collection);

    WhereClauseBuilder is(String path, Object value);

    WhereClauseBuilder is(String[] path, Object value);

    WhereClauseBuilder not();

    WhereClauseBuilder predicate(Boolean bool);

    WhereClauseBuilder negatePredicate(Boolean bool);

    WhereClauseBuilder like(String[] paths, Set<String> queries);

    WhereClauseBuilder like(String path, Set<String> queries);

    <C extends Comparable<C>> WhereClauseBuilder between(String[] paths, C start, C end);

    <C extends Comparable<C>> WhereClauseBuilder between(String path, C start, C end);

    <T> Specification<T> build();
}
