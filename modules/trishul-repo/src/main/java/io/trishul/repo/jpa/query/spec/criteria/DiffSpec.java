package io.trishul.repo.jpa.query.spec.criteria;

import io.trishul.model.base.pojo.BaseModel;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Root;

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
