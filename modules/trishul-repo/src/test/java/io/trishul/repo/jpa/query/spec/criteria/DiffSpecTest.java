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
import io.company.brewcraft.service.DiffSpec;

@SuppressWarnings("unchecked")
public class DiffSpecTest {
    private CriteriaSpec<?> spec;

    private CriteriaSpec<Double> mDelegateX;
    private Expression<Double> mExprX;

    private CriteriaSpec<Double> mDelegateY;
    private Expression<Double> mExprY;

    private CriteriaBuilder mCb;
    private CriteriaQuery<?> mCq;
    private Root<?> mRoot;

    @BeforeEach
    public void init() {
        mCb = mock(CriteriaBuilder.class);
        mCq = mock(CriteriaQuery.class);
        mRoot = mock(Root.class);

        mDelegateX = mock(CriteriaSpec.class);
        mExprX = mock(Expression.class);
        doReturn(mExprX).when(mDelegateX).getExpression(mRoot, mCq, mCb);

        mDelegateY = mock(CriteriaSpec.class);
        mExprY = mock(Expression.class);
        doReturn(mExprY).when(mDelegateY).getExpression(mRoot, mCq, mCb);
    }

    @Test
    public void testGetExpression_ReturnsDiffExpressionOnDelegatePath() {
        Expression<Double> mDiffExpr = mock(Expression.class);
        doReturn(mDiffExpr).when(mCb).diff(mExprX, mExprY);

        spec = new DiffSpec<>(mDelegateX, mDelegateY);
        assertSame(mDiffExpr, spec.getExpression(mRoot, mCq, mCb));
    }
}
