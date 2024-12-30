package io.trishul.repo.jpa.query.spec.criteria;

import io.trishul.model.base.pojo.BaseModel;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Root;

public class CountSpec<T> extends BaseModel implements CriteriaSpec<Long> {
  private final CriteriaSpec<T> spec;

  public CountSpec(CriteriaSpec<T> spec) {
    this.spec = spec;
  }

  @Override
  public Expression<Long> getExpression(Root<?> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
    return cb.count(this.spec.getExpression(root, cq, cb));
  }
}
