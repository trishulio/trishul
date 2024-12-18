package io.trishul.repo.jpa.query.clause.group.builder;

import io.trishul.model.base.pojo.BaseModel;
import io.trishul.repo.jpa.query.path.provider.PathProvider;
import io.trishul.repo.jpa.query.spec.accumulator.ColumnSpecAccumulator;
import io.trishul.repo.jpa.query.spec.criteria.ColumnSpec;
import io.trishul.repo.jpa.query.spec.criteria.CriteriaSpec;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

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

    public List<Expression<?>> getGroupByClause(
            Root<?> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
        return this.accumulator.getColumns(root, cq, cb);
    }
}
