package io.trishul.repo.jpa.query.spec.criteria;

import io.trishul.model.base.pojo.BaseModel;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Root;

public class BetweenSpec<C extends Comparable<C>> extends BaseModel
        implements CriteriaSpec<Boolean> {
    private final CriteriaSpec<C> spec;
    private final C start;
    private final C end;

    public BetweenSpec(CriteriaSpec<C> spec, C start, C end) {
        this.spec = spec;
        this.start = start;
        this.end = end;
    }

    @Override
    public Expression<Boolean> getExpression(
            Root<?> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
        Expression<Boolean> expr = null;
        Expression<? extends C> innerExpr = this.spec.getExpression(root, cq, cb);

        if (start != null && end != null) {
            expr = cb.between(innerExpr, start, end);

        } else if (start != null) {
            expr = cb.greaterThanOrEqualTo(innerExpr, start);

        } else if (end != null) {
            expr = cb.lessThanOrEqualTo(innerExpr, end);

        } else {
            // Note: When start and end are both null, a "true" literal is returned,
            // which makes sense but is that a better than throwing an error?
            expr = cb.literal(true);
        }

        return expr;
    }
}
