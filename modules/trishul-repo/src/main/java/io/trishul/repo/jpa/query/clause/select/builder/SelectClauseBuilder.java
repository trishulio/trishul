package io.trishul.repo.jpa.query.clause.select.builder;

import io.trishul.model.base.pojo.BaseModel;
import io.trishul.repo.jpa.query.path.provider.PathProvider;
import io.trishul.repo.jpa.query.spec.accumulator.ColumnSpecAccumulator;
import io.trishul.repo.jpa.query.spec.criteria.ColumnSpec;
import io.trishul.repo.jpa.query.spec.criteria.CriteriaSpec;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Selection;
import java.util.List;

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
