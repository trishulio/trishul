package io.trishul.repo.jpa.query.join.joiner;

import javax.persistence.criteria.From;
import javax.persistence.criteria.Path;

public interface JpaJoiner {
    final JpaJoiner JPA_JOINER =
            new JpaJoinerCachingProxy(
                    new JpaJoinerLocalCache(), new CriteriaJoinAnnotationJoiner());

    <X, Y> From<X, Y> join(From<X, Y> join, String fieldName);

    <X, Y> Path<X> get(From<X, Y> join, String fieldName);
}
