package sh.trishul.repo.jpa.query.spec.criteria;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Root;
import sh.trishul.model.base.pojo.BaseModel;

@SuppressWarnings("unchecked")
public class RootSpec<T> extends BaseModel implements CriteriaSpec<T> {
  @Override
  public Expression<T> getExpression(Root<?> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
    return (Expression<T>) root;
  }
}
