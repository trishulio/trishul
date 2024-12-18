package io.trishul.repo.aggregation.repo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import io.trishul.model.base.pojo.BaseModel;
import io.trishul.repo.jpa.query.clause.group.builder.GroupByClauseBuilder;
import io.trishul.repo.jpa.query.clause.select.builder.SelectClauseBuilder;
import io.trishul.repo.jpa.query.resolver.QueryResolver;
import jakarta.persistence.TypedQuery;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

@SuppressWarnings("unchecked")
public class AggregationRepositoryTest {
    class TestEntity extends BaseModel {
        private Long id;

        public TestEntity(Long id) {
            this.id = id;
        }
    }

    private QueryResolver mResolver;
    private AggregationRepository repo;

    @BeforeEach
    public void init() {
        mResolver = mock(QueryResolver.class);

        repo = new AggregationRepository(mResolver);
    }

    @Test
    public void testGetAggregation_ReturnsListOfResultsFromTypedQuery() {
        SelectClauseBuilder mSelector = mock(SelectClauseBuilder.class);
        GroupByClauseBuilder mGroupBy = mock(GroupByClauseBuilder.class);
        Specification<TestEntity> mSpec = mock(Specification.class);

        TypedQuery<Object> mTq = mock(TypedQuery.class);
        doReturn(mTq)
                .when(mResolver)
                .buildQuery(
                        TestEntity.class,
                        Object.class,
                        mSelector,
                        mGroupBy,
                        mSpec,
                        PageRequest.of(1, 10));
        doReturn(List.of(new TestEntity(1L))).when(mTq).getResultList();

        List<Object> content =
                repo.getAggregation(
                        TestEntity.class,
                        Object.class,
                        mSelector,
                        mGroupBy,
                        mSpec,
                        PageRequest.of(1, 10));

        assertEquals(List.of(new TestEntity(1L)), content);
    }

    @Test
    public void
            testGetAggregation_UsesSameClassForReturnClassAndReturnsListOFResultsFromTypedQuery_WhenOverloadedMethodIsUsed() {
        SelectClauseBuilder mSelector = mock(SelectClauseBuilder.class);
        GroupByClauseBuilder mGroupBy = mock(GroupByClauseBuilder.class);
        Specification<TestEntity> mSpec = mock(Specification.class);

        TypedQuery<TestEntity> mTq = mock(TypedQuery.class);
        doReturn(mTq)
                .when(mResolver)
                .buildQuery(
                        TestEntity.class,
                        TestEntity.class,
                        mSelector,
                        mGroupBy,
                        mSpec,
                        PageRequest.of(1, 10));
        doReturn(List.of(new TestEntity(1L))).when(mTq).getResultList();

        List<TestEntity> content =
                repo.getAggregation(
                        TestEntity.class, mSelector, mGroupBy, mSpec, PageRequest.of(1, 10));

        assertEquals(List.of(new TestEntity(1L)), content);
    }

    @Test
    public void testGetSingleAggregation_ReturnsSingleResultFromTypedQuery() {
        SelectClauseBuilder mSelector = mock(SelectClauseBuilder.class);
        GroupByClauseBuilder mGroupBy = mock(GroupByClauseBuilder.class);
        Specification<TestEntity> mSpec = mock(Specification.class);

        TypedQuery<TestEntity> mTq = mock(TypedQuery.class);
        doReturn(mTq)
                .when(mResolver)
                .buildQuery(
                        TestEntity.class,
                        TestEntity.class,
                        mSelector,
                        mGroupBy,
                        mSpec,
                        PageRequest.of(1, 10));
        doReturn(new TestEntity(1L)).when(mTq).getSingleResult();

        TestEntity content =
                repo.getSingleAggregation(
                        TestEntity.class,
                        TestEntity.class,
                        mSelector,
                        mGroupBy,
                        mSpec,
                        PageRequest.of(1, 10));

        assertEquals(new TestEntity(1L), content);
    }

    @Test
    public void testResultCount_ReturnsCountOfStream() {
        SelectClauseBuilder mSelector = mock(SelectClauseBuilder.class);
        GroupByClauseBuilder mGroupBy = mock(GroupByClauseBuilder.class);
        Specification<TestEntity> mSpec = mock(Specification.class);

        TypedQuery<TestEntity> mTq = mock(TypedQuery.class);
        doReturn(mTq)
                .when(mResolver)
                .buildQuery(
                        TestEntity.class,
                        Object.class,
                        mSelector,
                        mGroupBy,
                        mSpec,
                        PageRequest.of(1, 10));

        Stream<TestEntity> mStream = mock(Stream.class);
        doReturn(99L).when(mStream).count();
        doReturn(mStream).when(mTq).getResultStream();

        Long count =
                repo.getResultCount(
                        TestEntity.class, mSelector, mGroupBy, mSpec, PageRequest.of(1, 10));
        assertEquals(99L, count);
    }
}
