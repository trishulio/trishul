package io.trishul.repo.jpa.query.join.joiner;

import jakarta.persistence.criteria.From;
import jakarta.persistence.criteria.Path;

public interface JpaJoiner {
    final JpaJoiner JPA_JOINER =
            new JpaJoinerCachingProxy(
                    new JpaJoinerLocalCache(), new CriteriaJoinAnnotationJoiner());

    <X, Y> From<X, Y> join(From<X, Y> join, String fieldName);

    <X, Y> Path<X> get(From<X, Y> join, String fieldName);
}
