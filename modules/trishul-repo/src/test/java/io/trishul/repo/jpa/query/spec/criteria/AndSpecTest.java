package io.company.brewcraft.service.impl;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.service.AndSpec;
import io.company.brewcraft.service.CriteriaSpec;

public class AndSpecTest {
    private CriteriaSpec<Boolean> spec;

    private CriteriaSpec<Boolean> mDelegate;
    private Expression<Boolean> mExpr;

    private CriteriaBuilder mCb;
    private CriteriaQuery<?> mCq;
    private Root<?> mRoot;

    @BeforeEach
    public void init() {
        mCb = mock(CriteriaBuilder.class);
        mCq = mock(CriteriaQuery.class);
        mRoot = mock(Root.class);

        mDelegate = mock(CriteriaSpec.class);
        mExpr = mock(Predicate.class);
        doReturn(mExpr).when(mDelegate).getExpression(mRoot, mCq, mCb);
    }

    @Test
    public void testGetExpression_ReturnsAndExpressionOnDelegatePath() {
        Predicate mAndExpr = mock(Predicate.class);
        doReturn(mAndExpr).when(mCb).and((Predicate) mExpr);

        spec = new AndSpec(mDelegate);
        assertSame(mAndExpr, spec.getExpression(mRoot, mCq, mCb));
    }
}
