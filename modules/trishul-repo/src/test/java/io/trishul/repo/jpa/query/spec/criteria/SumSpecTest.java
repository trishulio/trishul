package io.company.brewcraft.service.impl;

import static org.junit.Assert.assertSame;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.service.ColumnSpec;
import io.company.brewcraft.service.CriteriaSpec;
import io.company.brewcraft.service.SumSpec;

public class SumSpecTest {
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
    public void testGetExpression_ReturnsSumExpressionOnDelegatePath() {
        Expression<Number> mSumExpr = mock(Expression.class);
        doReturn(mSumExpr).when(mCb).sum(mExpr);

        spec = new SumSpec<>(mDelegate);
        assertSame(mSumExpr, spec.getExpression(mRoot, mCq, mCb));
    }

    @Test
    public void testConstructor_PathProvider_AddsSelectColumnWithPathFromProvider() {
        spec = new SumSpec<>(() -> new String[] { "PATH_1", "PATH_2" });

        SumSpec<Number> expected = new SumSpec<>(new ColumnSpec<>(new String[] { "PATH_1", "PATH_2" }));
        assertEquals(expected, spec);
    }

    @Test
    public void testConstructor_String_AddsSelectColumnWithPathValues() {
        spec = new SumSpec<>(new String[] { "PATH_1", "PATH_2" });

        SumSpec<Number> expected = new SumSpec<>(new ColumnSpec<>(new String[] { "PATH_1", "PATH_2" }));
        assertEquals(expected, spec);
    }
}
