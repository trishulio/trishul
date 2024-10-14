package io.trishul.repo.jpa.query.spec.criteria;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

public interface CriteriaSpec<T> {
    Expression<T> getExpression(Root<?> root, CriteriaQuery<?> cq, CriteriaBuilder cb);
}
