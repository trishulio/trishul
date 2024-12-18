package io.trishul.repo.jpa.query.spec.criteria;

import io.trishul.model.base.pojo.BaseModel;
import io.trishul.repo.jpa.query.path.provider.PathProvider;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

public class SumSpec<T extends Number> extends BaseModel implements CriteriaSpec<T> {
    private final CriteriaSpec<T> path;

    public SumSpec(PathProvider provider) {
        this(provider.getPath());
    }

    public SumSpec(String[] paths) {
        this(new ColumnSpec<>(paths));
    }

    public SumSpec(CriteriaSpec<T> path) {
        this.path = path;
    }

    @Override
    public Expression<T> getExpression(Root<?> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
        Expression<T> x = this.path.getExpression(root, cq, cb);

        return cb.sum(x);
    }
}
