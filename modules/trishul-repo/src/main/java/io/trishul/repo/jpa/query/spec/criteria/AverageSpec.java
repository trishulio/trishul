package io.trishul.repo.jpa.query.spec.criteria;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

import io.trishul.model.base.pojo.BaseModel;

public class AverageSpec<T extends Number> extends BaseModel implements CriteriaSpec<Double> {
    private final CriteriaSpec<T> spec;

    public AverageSpec(CriteriaSpec<T> spec) {
        this.spec = spec;
    }

    @Override
    public Expression<Double> getExpression(Root<?> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
        return cb.avg(this.spec.getExpression(root, cq, cb));
    }
}
