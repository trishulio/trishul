package io.trishul.repo.jpa.query.spec.criteria;

import io.trishul.model.base.pojo.BaseModel;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

public class DiffSpec<T extends Number> extends BaseModel implements CriteriaSpec<T> {
    private final CriteriaSpec<T> pathX, pathY;

    public DiffSpec(CriteriaSpec<T> pathX, CriteriaSpec<T> pathY) {
        this.pathX = pathX;
        this.pathY = pathY;
    }

    @Override
    public Expression<T> getExpression(Root<?> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
        Expression<T> x = this.pathX.getExpression(root, cq, cb);
        Expression<T> y = this.pathY.getExpression(root, cq, cb);

        return cb.diff(x, y);
    }
}
