package io.trishul.repo.jpa.query.spec.criteria;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class IsNullSpecTest {
    private CriteriaSpec<Boolean> spec;

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
    public void testGetExpression_ReturnsIsNullExpressionOnDelegatePath() {
        Predicate mNullExpr = mock(Predicate.class);
        doReturn(mNullExpr).when(mCb).isNull(mExpr);

        spec = new IsNullSpec(mDelegate);
        assertSame(mNullExpr, spec.getExpression(mRoot, mCq, mCb));
    }
}
