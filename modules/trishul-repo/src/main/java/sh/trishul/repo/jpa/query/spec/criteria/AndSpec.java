package sh.trishul.repo.jpa.query.spec.criteria;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import sh.trishul.model.base.pojo.BaseModel;

public class AndSpec extends BaseModel implements CriteriaSpec<Boolean> {
  private final CriteriaSpec<Boolean> spec;

  public AndSpec(CriteriaSpec<Boolean> spec) {
    this.spec = spec;
  }

  @Override
  public Expression<Boolean> getExpression(Root<?> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
    return cb.and((Predicate) this.spec.getExpression(root, cq, cb));
  }
}
