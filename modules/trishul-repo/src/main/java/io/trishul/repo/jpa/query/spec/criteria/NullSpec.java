package io.trishul.repo.jpa.query.spec.criteria;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

import io.trishul.model.base.pojo.BaseModel;

public class NullSpec extends BaseModel implements CriteriaSpec<Integer> {
    @Override
    public Expression<Integer> getExpression(Root<?> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
        return cb.nullLiteral(Integer.class);
    }
}
