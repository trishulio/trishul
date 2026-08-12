package sh.trishul.repo.jpa.query.spec.criteria;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Root;
import sh.trishul.model.base.pojo.BaseModel;

public class ILikeSpec extends BaseModel implements CriteriaSpec<Boolean> {
  private final CriteriaSpec<String> spec;
  private final String text;

  public ILikeSpec(CriteriaSpec<String> spec, String text) {
    this.spec = spec;
    this.text = text;
  }

  @Override
  public Expression<Boolean> getExpression(Root<?> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
    return cb.like(cb.lower(this.spec.getExpression(root, cq, cb)),
        String.format("%%%s%%", text.toLowerCase()));
  }
}
