package io.trishul.repo.jpa.query.spec.criteria;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

import io.trishul.model.base.pojo.BaseModel;

public class NotSpec extends BaseModel implements CriteriaSpec<Boolean> {
    private final CriteriaSpec<Boolean> spec;

    public NotSpec(CriteriaSpec<Boolean> spec) {
        this.spec = spec;
    }

    @Override
    public Expression<Boolean> getExpression(Root<?> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
        return cb.not(this.spec.getExpression(root, cq, cb));
    }
}
