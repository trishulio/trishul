package io.trishul.repo.jpa.query.resolver;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.query.QueryUtils;

import io.trishul.repo.jpa.query.clause.group.builder.GroupByClauseBuilder;
import io.trishul.repo.jpa.query.clause.select.builder.SelectClauseBuilder;

public class QueryResolver {
    @SuppressWarnings("unused")
    private static Logger log = LoggerFactory.getLogger(QueryResolver.class);

    private final EntityManager em;

    public QueryResolver(EntityManager em) {
        this.em = em;
    }

    public <R, T> TypedQuery<R> buildQuery(Class<T> entityClz, Class<R> returnClz, SelectClauseBuilder selector, GroupByClauseBuilder grouper, Specification<T> spec, Pageable pageable) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<R> cq = cb.createQuery(returnClz);

        Root<T> root = cq.from(entityClz);

        cq.where(spec.toPredicate(root, cq, cb));

        List<Selection<?>> selectAttrs = selector.getSelectClause(root, cq, cb);
        cq.multiselect(selectAttrs);

        if (grouper != null) {
            List<Expression<?>> groupByAttrs = grouper.getGroupByClause(root, cq, cb);
            cq.groupBy(groupByAttrs);
        }

        if (pageable != null) {
            List<Order> orders = QueryUtils.toOrders(pageable.getSort(), root, cb);
            cq.orderBy(orders);
        }

        TypedQuery<R> q = em.createQuery(cq);

        if (pageable != null) {
            q.setFirstResult((int) pageable.getOffset()).setMaxResults(pageable.getPageSize());
        }

        return q;
    }
}