package io.company.brewcraft.service.impl;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.service.CriteriaSpec;
import io.company.brewcraft.service.CountSpec;

public class CountSpecTest {
    private CriteriaSpec<?> spec;

    private CriteriaSpec<?> mDelegate;
    private Expression<?> mExpr;

    private CriteriaBuilder mCb;
    private CriteriaQuery<?> mCq;
    private Root<?> mRoot;

    @BeforeEach
    public void init() {
        mCb = mock(CriteriaBuilder.class);
        mCq = mock(CriteriaQuery.class);
        mRoot = mock(Root.class);

        mDelegate = mock(CriteriaSpec.class);
        mExpr = mock(Expression.class);
        doReturn(mExpr).when(mDelegate).getExpression(mRoot, mCq, mCb);
    }

    @Test
    public void testGetExpression_ReturnsCountExpressionOnDelegatePath() {
        Expression<Double> mCountExpr = mock(Expression.class);
        doReturn(mCountExpr).when(mCb).count(mExpr);

        spec = new CountSpec<>(mDelegate);
        assertSame(mCountExpr, spec.getExpression(mRoot, mCq, mCb));
    }
}
