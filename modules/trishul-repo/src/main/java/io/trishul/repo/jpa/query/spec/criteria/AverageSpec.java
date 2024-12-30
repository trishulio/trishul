package io.trishul.repo.jpa.query.spec.criteria;

import io.trishul.model.base.pojo.BaseModel;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Root;

public class AverageSpec<T extends Number> extends BaseModel implements CriteriaSpec<Double> {
  private final CriteriaSpec<T> spec;

  public AverageSpec(CriteriaSpec<T> spec) {
    this.spec = spec;
  }

  @Override
  public Expression<Double> getExpression(Root<?> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
    return cb.avg(this.spec.getExpression(root, cq, cb));
  }
}
