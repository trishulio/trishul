package io.trishul.repo.jpa.query.spec.criteria;

import io.trishul.model.base.pojo.BaseModel;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Root;

public class LikeSpec extends BaseModel implements CriteriaSpec<Boolean> {
  private final CriteriaSpec<String> spec;
  private final String text;

  public LikeSpec(CriteriaSpec<String> spec, String text) {
    this.spec = spec;
    this.text = text;
  }

  @Override
  public Expression<Boolean> getExpression(Root<?> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
    return cb.like(cb.lower(this.spec.getExpression(root, cq, cb)),
        String.format("%%%s%%", text.toLowerCase()));
  }
}
