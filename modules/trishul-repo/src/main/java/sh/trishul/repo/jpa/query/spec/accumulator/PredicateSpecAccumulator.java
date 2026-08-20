package sh.trishul.repo.jpa.query.spec.accumulator;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import sh.trishul.repo.jpa.query.spec.criteria.AndSpec;
import sh.trishul.repo.jpa.query.spec.criteria.CriteriaSpec;
import sh.trishul.repo.jpa.query.spec.criteria.NotSpec;

public class PredicateSpecAccumulator {
  private final List<CriteriaSpec<Boolean>> aggregations;
  private boolean isNot;
  private Boolean isPredicate;

  public PredicateSpecAccumulator() {
    this(new ArrayList<>());
  }

  protected PredicateSpecAccumulator(List<CriteriaSpec<Boolean>> aggregations) {
    this.aggregations = aggregations;
    this.isNot = false;
    this.isPredicate = true;
  }

  public void add(CriteriaSpec<Boolean> spec) {
    if (this.isPredicate == null || !this.isPredicate) {
      this.isNot = false;
      this.isPredicate = true;
      return;
    }

    if (this.isNot) {
      spec = new NotSpec(spec);
    }

    spec = new AndSpec(spec);

    this.aggregations.add(spec);
  }

  public PredicateSpecAccumulator setIsNot(boolean isNot) {
    this.isNot = isNot;
    return this;
  }

  public PredicateSpecAccumulator setIsPredicate(Boolean isPredicate) {
    this.isPredicate = isPredicate;
    return this;
  }

  public Predicate[] getPredicates(Root<?> root, CriteriaQuery<?> query,
      CriteriaBuilder criteriaBuilder) {
    Predicate[] predicates = new Predicate[this.aggregations.size()];
    predicates = this.aggregations.stream()
        .map(spec -> spec.getExpression(root, query, criteriaBuilder)).toList().toArray(predicates);

    return predicates;
  }
}
