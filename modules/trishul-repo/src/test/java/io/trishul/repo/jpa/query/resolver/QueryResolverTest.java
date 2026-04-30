package io.trishul.repo.jpa.query.resolver;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import io.trishul.repo.jpa.query.clause.group.builder.GroupByClauseBuilder;
import io.trishul.repo.jpa.query.clause.select.builder.SelectClauseBuilder;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Selection;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;

class QueryResolverTest {
  private QueryResolver qResolver;

  private EntityManager mEm;
  private CriteriaBuilder mCb;
  private CriteriaQuery<Object> mCq;
  private Root<TestEntity> mRoot;
  private TypedQuery<Object> mTq;

  // Simple test entity class for testing
  public static class TestEntity {
    private Long id;

    public Long getId() {
      return id;
    }

    public void setId(Long id) {
      this.id = id;
    }
  }

  @BeforeEach
  @SuppressWarnings("unchecked")
  void init() {
    mEm = mock(EntityManager.class);

    mCb = mock(CriteriaBuilder.class);
    doReturn(mCb).when(mEm).getCriteriaBuilder();

    mCq = mock(CriteriaQuery.class);
    doReturn(mCq).when(mCb).createQuery(Object.class);

    mRoot = mock(Root.class);
    doReturn(mRoot).when(mCq).from(TestEntity.class);

    mTq = mock(TypedQuery.class);
    doReturn(mTq).when(mEm).createQuery(mCq);

    // For Sorting assertions
    doReturn(TestEntity.class).when(mRoot).getJavaType();
    Path<Object> mPath = mock(Path.class);
    doReturn(mPath).when(mRoot).get("id");

    // For pagination
    doReturn(mTq).when(mTq).setFirstResult(any(Integer.class));
    doReturn(mTq).when(mTq).setMaxResults(any(Integer.class));

    // For orderBy
    doReturn(mCq).when(mCq).orderBy(any(List.class));

    qResolver = new QueryResolver(mEm);
  }

  @Test
  @SuppressWarnings("unchecked")
  void testBuildQuery_ReturnsTypedQueryWithAllClauses_WhenAllClausesAreNotNull() {
    Specification<TestEntity> mSpec = mock(Specification.class);
    Predicate mPred = mock(Predicate.class);
    doReturn(mPred).when(mSpec).toPredicate(mRoot, mCq, mCb);

    SelectClauseBuilder mSelector = mock(SelectClauseBuilder.class);
    List<Selection<?>> mSelection = List.of(mock(Selection.class));
    doReturn(mSelection).when(mSelector).getSelectClause(mRoot, mCq, mCb);

    GroupByClauseBuilder mGroupBySelector = mock(GroupByClauseBuilder.class);
    List<Expression<?>> mGroupSelection = List.of(mock(Expression.class));
    doReturn(mGroupSelection).when(mGroupBySelector).getGroupByClause(mRoot, mCq, mCb);

    TypedQuery<Object> q = this.qResolver.buildQuery(TestEntity.class, Object.class, mSelector,
        mGroupBySelector, mSpec, PageRequest.of(10, 99, Direction.DESC, "id"));

    assertSame(mTq, q);

    InOrder order = inOrder(mCq, mTq);
    order.verify(mCq, times(1)).where(mPred);
    order.verify(mCq, times(1)).multiselect(mSelection);
    order.verify(mCq, times(1)).groupBy(mGroupSelection);
    order.verify(mCq, times(1)).orderBy(any(List.class));
    order.verify(mTq).setFirstResult(990);
    order.verify(mTq).setMaxResults(99);
  }

  @Test
  @SuppressWarnings("unchecked")
  void testBuildQuery_ReturnsTypedQueryWithoutGroupByClause_WhenGroupByClauseIsNull() {
    Specification<TestEntity> mSpec = mock(Specification.class);
    Predicate mPred = mock(Predicate.class);
    doReturn(mPred).when(mSpec).toPredicate(mRoot, mCq, mCb);

    SelectClauseBuilder mSelector = mock(SelectClauseBuilder.class);
    List<Selection<?>> mSelection = List.of(mock(Selection.class));
    doReturn(mSelection).when(mSelector).getSelectClause(mRoot, mCq, mCb);

    TypedQuery<Object> q = this.qResolver.buildQuery(TestEntity.class, Object.class, mSelector,
        null, mSpec, PageRequest.of(10, 99, Direction.DESC, "id"));

    assertSame(mTq, q);

    verify(mCq, times(1)).where(mPred);
    verify(mCq, times(1)).multiselect(mSelection);
    verify(mCq, times(0)).groupBy(any(List.class));
    verify(mCq, times(1)).orderBy(any(List.class));
    verify(mTq).setFirstResult(990);
    verify(mTq).setMaxResults(99);
  }

  @Test
  @SuppressWarnings("unchecked")
  void testBuildQuery_ReturnsTypedQueryWithoutPagination_WhenPageableClauseIsNull() {
    Specification<TestEntity> mSpec = mock(Specification.class);
    Predicate mPred = mock(Predicate.class);
    doReturn(mPred).when(mSpec).toPredicate(mRoot, mCq, mCb);

    SelectClauseBuilder mSelector = mock(SelectClauseBuilder.class);
    List<Selection<?>> mSelection = List.of(mock(Selection.class));
    doReturn(mSelection).when(mSelector).getSelectClause(mRoot, mCq, mCb);

    GroupByClauseBuilder mGroupBySelector = mock(GroupByClauseBuilder.class);
    List<Expression<?>> mGroupSelection = List.of(mock(Expression.class));
    doReturn(mGroupSelection).when(mGroupBySelector).getGroupByClause(mRoot, mCq, mCb);

    TypedQuery<Object> q = this.qResolver.buildQuery(TestEntity.class, Object.class, mSelector,
        mGroupBySelector, mSpec, null);

    assertSame(mTq, q);

    verify(mCq, times(1)).where(mPred);
    verify(mCq, times(1)).multiselect(mSelection);
    verify(mCq, times(1)).groupBy(mGroupSelection);
    verify(mCq, times(0)).orderBy(any(List.class));
    verify(mTq, times(0)).setFirstResult(any(Integer.class));
    verify(mTq, times(0)).setMaxResults(any(Integer.class));
  }
}
