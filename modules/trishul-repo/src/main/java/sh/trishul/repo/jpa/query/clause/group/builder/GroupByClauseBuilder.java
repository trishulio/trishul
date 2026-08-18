package sh.trishul.repo.jpa.query.clause.group.builder;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Root;
import java.util.List;
import sh.trishul.model.base.pojo.BaseModel;
import sh.trishul.repo.jpa.query.path.provider.PathProvider;
import sh.trishul.repo.jpa.query.spec.accumulator.ColumnSpecAccumulator;
import sh.trishul.repo.jpa.query.spec.criteria.ColumnSpec;
import sh.trishul.repo.jpa.query.spec.criteria.CriteriaSpec;

public class GroupByClauseBuilder extends BaseModel {
  private final ColumnSpecAccumulator accumulator;

  public GroupByClauseBuilder() {
    this(new ColumnSpecAccumulator());
  }

  protected GroupByClauseBuilder(ColumnSpecAccumulator accumulator) {
    this.accumulator = accumulator;
  }

  public GroupByClauseBuilder groupBy(PathProvider provider) {
    if (provider != null) {
      groupBy(provider.getPath());
    }

    return this;
  }

  public GroupByClauseBuilder groupBy(String... paths) {
    if (paths != null) {
      groupBy(new ColumnSpec<>(paths));
    }

    return this;
  }

  public GroupByClauseBuilder groupBy(CriteriaSpec<?> spec) {
    if (spec != null) {
      this.accumulator.add(spec);
    }

    return this;
  }

  public List<Expression<?>> getGroupByClause(Root<?> root, CriteriaQuery<?> cq,
      CriteriaBuilder cb) {
    return this.accumulator.getColumns(root, cq, cb);
  }
}
