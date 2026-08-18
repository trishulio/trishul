package sh.trishul.repo.jpa.query.spec.accumulator;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Selection;
import java.util.ArrayList;
import java.util.List;
import sh.trishul.model.base.pojo.BaseModel;
import sh.trishul.repo.jpa.query.spec.criteria.CriteriaSpec;

public class ColumnSpecAccumulator extends BaseModel {
  private final List<CriteriaSpec<?>> aggregations;

  public ColumnSpecAccumulator() {
    this.aggregations = new ArrayList<>();
  }

  public void add(CriteriaSpec<?> spec) {
    if (spec != null) {
      this.aggregations.add(spec);
    }
  }

  public <T extends Selection<?>> List<T> getColumns(Root<?> root, CriteriaQuery<?> cq,
      CriteriaBuilder cb) {
    @SuppressWarnings("unchecked")
    List<T> columns
        = this.aggregations.stream().map(spec -> (T) spec.getExpression(root, cq, cb)).toList();
    return columns;
  }
}
