package io.trishul.repo.jpa.query.spec.criteria;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Root;

public interface CriteriaSpec<T> {
    Expression<T> getExpression(Root<?> root, CriteriaQuery<?> cq, CriteriaBuilder cb);
}
