package io.trishul.repo.jpa.query.spec.criteria;

import io.trishul.model.base.pojo.BaseModel;
import io.trishul.repo.jpa.query.path.provider.PathProvider;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Root;

public class SumSpec<T extends Number> extends BaseModel implements CriteriaSpec<T> {
  private final CriteriaSpec<T> path;

  public SumSpec(PathProvider provider) {
    this(provider.getPath());
  }

  public SumSpec(String[] paths) {
    this(new ColumnSpec<>(paths));
  }

  public SumSpec(CriteriaSpec<T> path) {
    this.path = path;
  }

  @Override
  public Expression<T> getExpression(Root<?> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
    Expression<T> x = this.path.getExpression(root, cq, cb);

    return cb.sum(x);
  }
}
