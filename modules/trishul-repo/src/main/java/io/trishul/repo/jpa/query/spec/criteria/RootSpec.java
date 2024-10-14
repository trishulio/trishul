package io.trishul.repo.jpa.query.spec.criteria;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

import io.trishul.model.base.pojo.BaseModel;

@SuppressWarnings("unchecked")
public class RootSpec<T> extends BaseModel implements CriteriaSpec<T> {
    @Override
    public Expression<T> getExpression(Root<?> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
        return (Expression<T>) root;
    }
}
