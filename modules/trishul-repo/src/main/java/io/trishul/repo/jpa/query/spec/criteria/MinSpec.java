package io.trishul.repo.jpa.query.spec.criteria;

import io.trishul.model.base.pojo.BaseModel;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Root;

public class MinSpec<T extends Number> extends BaseModel implements CriteriaSpec<T> {
  private final CriteriaSpec<T> spec;

  public MinSpec(CriteriaSpec<T> spec) {
    this.spec = spec;
  }

  @Override
  public Expression<T> getExpression(Root<?> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
    return cb.min(this.spec.getExpression(root, cq, cb));
  }
}
