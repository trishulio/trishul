package io.trishul.repo.jpa.query.spec.criteria;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import io.trishul.model.base.pojo.BaseModel;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BetweenSpecTest {
    class MockComparable extends BaseModel implements Comparable<MockComparable> {
        private int value;

        public MockComparable(int value) {
            this.value = value;
        }

        @Override
        public int compareTo(MockComparable o) {
            return o.value - this.value;
        }
    }

    private CriteriaSpec<Boolean> spec;

    private CriteriaSpec<MockComparable> mDelegate;
    private Expression<MockComparable> mExpr;

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
    public void
            testGetExpression_ReturnsBetweenExpressionOnDelegatePath_WhenBothStartAndEndAreNotNull() {
        Predicate mBetweenExpr = mock(Predicate.class);
        doReturn(mBetweenExpr)
                .when(mCb)
                .between(mExpr, new MockComparable(1), new MockComparable(2));

        spec = new BetweenSpec<>(mDelegate, new MockComparable(1), new MockComparable(2));
        assertSame(mBetweenExpr, spec.getExpression(mRoot, mCq, mCb));
    }

    @Test
    public void testGetExpression_ReturnsGreaterThanExpressionOnDelegatePath_WhenEndIsNull() {
        Predicate mBetweenExpr = mock(Predicate.class);
        doReturn(mBetweenExpr).when(mCb).greaterThanOrEqualTo(mExpr, new MockComparable(1));

        spec = new BetweenSpec<>(mDelegate, new MockComparable(1), null);
        assertSame(mBetweenExpr, spec.getExpression(mRoot, mCq, mCb));
    }

    @Test
    public void testGetExpression_ReturnsLessThanExpressionOnDelegatePath_WhenStartIsNull() {
        Predicate mBetweenExpr = mock(Predicate.class);
        doReturn(mBetweenExpr).when(mCb).lessThanOrEqualTo(mExpr, new MockComparable(2));

        spec = new BetweenSpec<>(mDelegate, null, new MockComparable(2));
        assertSame(mBetweenExpr, spec.getExpression(mRoot, mCq, mCb));
    }

    @Test
    public void testGetExpression_ReturnsTrueExpressionOnDelegatePath_WhenBothStartAndEndNotNull() {
        Predicate mBetweenExpr = mock(Predicate.class);
        doReturn(mBetweenExpr).when(mCb).literal(true);

        spec = new BetweenSpec<>(mDelegate, null, null);
        assertSame(mBetweenExpr, spec.getExpression(mRoot, mCq, mCb));
    }
}
