package io.trishul.repo.jpa.query.spec.criteria;

import io.trishul.model.base.pojo.BaseModel;
import io.trishul.repo.jpa.query.root.RootUtil;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Root;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ColumnSpec<T> extends BaseModel implements CriteriaSpec<T> {
  @SuppressWarnings("unused")
  private static final Logger log = LoggerFactory.getLogger(ColumnSpec.class);

  private final RootUtil rootUtil;

  private final String[] paths;

  protected ColumnSpec(RootUtil rootUtil, String[] paths) {
    this.rootUtil = rootUtil;
    this.paths = paths;
  }

  public ColumnSpec(String[] paths) {
    this(RootUtil.INSTANCE, paths);
  }

  @Override
  public Expression<T> getExpression(Root<?> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
    return this.rootUtil.getPath(root, paths);
  }
}
