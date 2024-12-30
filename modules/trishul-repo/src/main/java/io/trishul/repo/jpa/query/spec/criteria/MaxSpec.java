package io.trishul.repo.jpa.query.spec.criteria;

import io.trishul.model.base.pojo.BaseModel;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Root;

public class MaxSpec<T extends Number> extends BaseModel implements CriteriaSpec<T> {
  private final CriteriaSpec<T> spec;

  public MaxSpec(CriteriaSpec<T> spec) {
    this.spec = spec;
  }

  @Override
  public Expression<T> getExpression(Root<?> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
    return cb.max(this.spec.getExpression(root, cq, cb));
  }
}
