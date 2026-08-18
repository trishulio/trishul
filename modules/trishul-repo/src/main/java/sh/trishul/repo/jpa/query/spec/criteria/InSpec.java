package sh.trishul.repo.jpa.query.spec.criteria;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Root;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import sh.trishul.model.base.pojo.BaseModel;

public class InSpec<T> extends BaseModel implements CriteriaSpec<Boolean> {
  private CriteriaSpec<T> spec;
  private final Collection<T> collection;

  public InSpec(CriteriaSpec<T> spec, Collection<T> collection) {
    this.spec = spec;
    this.collection = Set.copyOf(null == collection ? Collections.emptyList() : collection);
  }

  @Override
  public Expression<Boolean> getExpression(Root<?> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
    return this.spec.getExpression(root, cq, cb).in(this.collection);
  }
}
