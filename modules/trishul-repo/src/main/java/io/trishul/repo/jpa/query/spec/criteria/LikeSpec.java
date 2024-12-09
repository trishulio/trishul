package io.trishul.repo.jpa.query.spec.criteria;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

import io.trishul.model.base.pojo.BaseModel;

public class LikeSpec extends BaseModel implements CriteriaSpec<Boolean> {
    private final CriteriaSpec<String> spec;
    private final String text;

    public LikeSpec(CriteriaSpec<String> spec, String text) {
        this.spec = spec;
        this.text = text;
    }

    @Override
    public Expression<Boolean> getExpression(Root<?> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
        return cb.like(cb.lower(this.spec.getExpression(root, cq, cb)), String.format("%%%s%%", text.toLowerCase()));
    }
}
