package io.trishul.repo.jpa.query.spec.criteria;

import io.trishul.model.base.pojo.BaseModel;
import java.util.Collection;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Root;

public class InSpec<T> extends BaseModel implements CriteriaSpec<Boolean> {
    private final CriteriaSpec<T> spec;
    private final Collection<T> collection;

    public InSpec(CriteriaSpec<T> spec, Collection<T> collection) {
        this.spec = spec;
        this.collection = collection;
    }

    @Override
    public Expression<Boolean> getExpression(
            Root<?> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
        return this.spec.getExpression(root, cq, cb).in(this.collection);
    }
}
