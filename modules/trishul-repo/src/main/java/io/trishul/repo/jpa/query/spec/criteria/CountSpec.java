package io.trishul.repo.jpa.query.spec.criteria;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

import io.trishul.model.base.pojo.BaseModel;

public class CountSpec<T> extends BaseModel implements CriteriaSpec<Long> {
    private CriteriaSpec<T> spec;

    public CountSpec(CriteriaSpec<T> spec) {
        this.spec = spec;
    }

    @Override
    public Expression<Long> getExpression(Root<?> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
        return cb.count(this.spec.getExpression(root, cq, cb));
    }
}