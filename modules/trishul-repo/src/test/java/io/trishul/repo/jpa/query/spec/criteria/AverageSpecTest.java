package io.trishul.repo.jpa.query.spec.criteria;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.trishul.repo.jpa.query.spec.criteria.CriteriaSpec;
import io.trishul.repo.jpa.query.spec.criteria.AverageSpec;

@SuppressWarnings("unchecked")
public class AverageSpecTest {
    private CriteriaSpec<Double> spec;

    private CriteriaSpec<Double> mDelegate;
    private Expression<Double> mExpr;

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
    public void testGetExpression_ReturnsAverageExpressionOnDelegatePath() {
        Predicate mAvgExpr = mock(Predicate.class);
        doReturn(mAvgExpr).when(mCb).avg(mExpr);

        spec = new AverageSpec<>(mDelegate);
        assertSame(mAvgExpr, spec.getExpression(mRoot, mCq, mCb));
    }
}
