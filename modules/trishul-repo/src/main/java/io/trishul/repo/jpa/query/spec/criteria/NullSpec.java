package io.trishul.repo.jpa.query.spec.criteria;

import io.trishul.model.base.pojo.BaseModel;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Root;

public class NullSpec extends BaseModel implements CriteriaSpec<Integer> {
    @Override
    public Expression<Integer> getExpression(
            Root<?> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
        return cb.nullLiteral(Integer.class);
    }
}
