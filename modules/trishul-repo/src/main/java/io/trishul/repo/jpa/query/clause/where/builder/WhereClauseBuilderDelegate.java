package io.trishul.repo.jpa.query.clause.where.builder;

import io.trishul.repo.jpa.query.spec.accumulator.PredicateSpecAccumulator;
import io.trishul.repo.jpa.query.spec.criteria.BetweenSpec;
import io.trishul.repo.jpa.query.spec.criteria.ColumnSpec;
import io.trishul.repo.jpa.query.spec.criteria.CriteriaSpec;
import io.trishul.repo.jpa.query.spec.criteria.InSpec;
import io.trishul.repo.jpa.query.spec.criteria.IsNullSpec;
import io.trishul.repo.jpa.query.spec.criteria.IsSpec;
import io.trishul.repo.jpa.query.spec.criteria.LikeSpec;
import java.util.Collection;
import java.util.Set;
import javax.persistence.criteria.Predicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;

public class WhereClauseBuilderDelegate {
    @SuppressWarnings("unused")
    private static final Logger log = LoggerFactory.getLogger(WhereClauseBuilderDelegate.class);

    private final PredicateSpecAccumulator accumulator;

    public WhereClauseBuilderDelegate(PredicateSpecAccumulator accumulator) {
        this.accumulator = accumulator;
    }

    public void not() {
        this.accumulator.setIsNot(true);
    }

    public void predicate(Boolean bool) {
        this.accumulator.setIsPredicate(bool);
    }

    public void isNull(String[] paths) {
        CriteriaSpec<Boolean> spec = new IsNullSpec(new ColumnSpec<>(paths));
        accumulator.add(spec);

        accumulator.setIsNot(false);
    }

    public void in(String[] paths, Collection<?> collection) {
        if (collection != null) {
            CriteriaSpec<Boolean> spec = new InSpec<>(new ColumnSpec<>(paths), collection);
            accumulator.add(spec);
        }

        accumulator.setIsNot(false);
    }

    public void is(String[] paths, Object value) {
        if (value != null) {
            CriteriaSpec<Boolean> spec = new IsSpec<>(new ColumnSpec<>(paths), value);
            accumulator.add(spec);
        }

        accumulator.setIsNot(false);
    }

    public void like(String[] paths, Set<String> queries) {
        if (queries != null) {
            for (String text : queries) {
                if (text != null) {
                    CriteriaSpec<Boolean> spec = new LikeSpec(new ColumnSpec<>(paths), text);
                    accumulator.add(spec);
                }
            }
        }

        accumulator.setIsNot(false);
    }

    public <C extends Comparable<C>> void between(String[] paths, C start, C end) {
        // Note: Null checks for start and end reduces the redundant clause for a true
        // literal. It only sort-of improves the Query performance but its not required.
        if (start != null || end != null) {
            CriteriaSpec<Boolean> spec = new BetweenSpec<>(new ColumnSpec<>(paths), start, end);
            accumulator.add(spec);
        }

        accumulator.setIsNot(false);
    }

    public <T> Specification<T> build() {
        return (root, query, criteriaBuilder) -> {
            Predicate[] predicates = this.accumulator.getPredicates(root, query, criteriaBuilder);
            return criteriaBuilder.and(predicates);
        };
    }
}
