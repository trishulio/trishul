package io.trishul.repo.jpa.query.spec.criteria;

import io.trishul.model.base.pojo.BaseModel;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class AndSpec extends BaseModel implements CriteriaSpec<Boolean> {
    private final CriteriaSpec<Boolean> spec;

    public AndSpec(CriteriaSpec<Boolean> spec) {
        this.spec = spec;
    }

    @Override
    public Expression<Boolean> getExpression(
            Root<?> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
        return cb.and((Predicate) this.spec.getExpression(root, cq, cb));
    }
}
