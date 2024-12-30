package io.trishul.repo.jpa.query.spec.criteria;

import io.trishul.model.base.pojo.BaseModel;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Root;

public class QueryLiteral<T> extends BaseModel implements CriteriaSpec<T> {
  private final T value;

  public QueryLiteral(T value) {
    this.value = value;
  }

  @Override
  public Expression<T> getExpression(Root<?> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
    return cb.literal(value);
  }
}
