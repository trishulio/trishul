package io.company.brewcraft.service.impl;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.service.CriteriaSpec;
import io.company.brewcraft.service.MinSpec;

public class MinSpecTest {
    private CriteriaSpec<?> spec;

    private CriteriaSpec<Number> mDelegate;
    private Expression<Number> mExpr;

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
    public void testGetExpression_ReturnsMinExpressionOnDelegatePath() {
        Expression<Number> mMinExpr = mock(Expression.class);
        doReturn(mMinExpr).when(mCb).min(mExpr);

        spec = new MinSpec<>(mDelegate);
        assertSame(mMinExpr, spec.getExpression(mRoot, mCq, mCb));
    }
}
