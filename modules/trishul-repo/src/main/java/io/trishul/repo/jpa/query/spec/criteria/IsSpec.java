package io.trishul.repo.jpa.query.spec.criteria;

import io.trishul.model.base.pojo.BaseModel;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Root;

public class IsSpec<T> extends BaseModel implements CriteriaSpec<Boolean> {
  private final CriteriaSpec<T> spec;
  private final Object value;

  public IsSpec(CriteriaSpec<T> spec, Object value) {
    this.spec = spec;
    this.value = value;
  }

  @Override
  public Expression<Boolean> getExpression(Root<?> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
    return cb.equal(this.spec.getExpression(root, cq, cb), value);
  }
}
