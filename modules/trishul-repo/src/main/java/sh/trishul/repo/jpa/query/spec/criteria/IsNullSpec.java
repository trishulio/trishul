package sh.trishul.repo.jpa.query.spec.criteria;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Root;
import sh.trishul.model.base.pojo.BaseModel;

public class IsNullSpec extends BaseModel implements CriteriaSpec<Boolean> {
  private final CriteriaSpec<?> spec;

  public IsNullSpec(CriteriaSpec<?> spec) {
    this.spec = spec;
  }

  @Override
  public Expression<Boolean> getExpression(Root<?> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
    return cb.isNull(this.spec.getExpression(root, cq, cb));
  }
}
