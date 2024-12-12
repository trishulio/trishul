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

import io.company.brewcraft.service.CriteriaSpec;
import io.company.brewcraft.service.LikeSpec;

public class LikeSpecTest {
    private CriteriaSpec<Boolean> spec;

    private CriteriaSpec<String> mDelegate;
    private Expression<String> mExpr;

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
    public void testGetExpression_ReturnsLikeOnLowerCaseExpressionOnDelegatePath() {
        Expression<String> mLowerExpr = mock(Expression.class);
        doReturn(mLowerExpr).when(mCb).lower(mExpr);

        Predicate mLikeExpr = mock(Predicate.class);
        doReturn(mLikeExpr).when(mCb).like(mLowerExpr, "%val1%");

        spec = new LikeSpec(mDelegate, "VAL1");
        assertSame(mLikeExpr, spec.getExpression(mRoot, mCq, mCb));
    }
}