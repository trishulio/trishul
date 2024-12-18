package io.trishul.repo.jpa.query.clause.select.builder;

import io.trishul.model.base.pojo.BaseModel;
import io.trishul.repo.jpa.query.path.provider.PathProvider;
import io.trishul.repo.jpa.query.spec.accumulator.ColumnSpecAccumulator;
import io.trishul.repo.jpa.query.spec.criteria.ColumnSpec;
import io.trishul.repo.jpa.query.spec.criteria.CriteriaSpec;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;

public class SelectClauseBuilder extends BaseModel {
    private final ColumnSpecAccumulator accumulator;

    public SelectClauseBuilder() {
        this(new ColumnSpecAccumulator());
    }

    protected SelectClauseBuilder(ColumnSpecAccumulator accumulator) {
        this.accumulator = accumulator;
    }

    public SelectClauseBuilder select(PathProvider provider) {
        if (provider != null) {
            select(provider.getPath());
        }

        return this;
    }

    public SelectClauseBuilder select(String... paths) {
        if (paths != null) {
            select(new ColumnSpec<>(paths));
        }

        return this;
    }

    public SelectClauseBuilder select(CriteriaSpec<?> spec) {
        if (spec != null) {
            this.accumulator.add(spec);
        }

        return this;
    }

    public List<Selection<?>> getSelectClause(
            Root<?> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
        return this.accumulator.getColumns(root, cq, cb);
    }
}
