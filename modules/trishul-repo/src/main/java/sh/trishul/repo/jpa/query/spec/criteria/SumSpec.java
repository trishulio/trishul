package sh.trishul.repo.jpa.query.spec.criteria;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Root;
import sh.trishul.model.base.pojo.BaseModel;
import sh.trishul.repo.jpa.query.path.provider.PathProvider;

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
