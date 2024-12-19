package io.trishul.repo.jpa.query.clause.group.builder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

import io.trishul.repo.jpa.query.path.provider.PathProvider;
import io.trishul.repo.jpa.query.spec.accumulator.ColumnSpecAccumulator;
import io.trishul.repo.jpa.query.spec.criteria.ColumnSpec;
import io.trishul.repo.jpa.query.spec.criteria.CriteriaSpec;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Root;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

public class GroupByClauseBuilderTest {
    private GroupByClauseBuilder grouper;
    private ColumnSpecAccumulator mAccumulator;

    @BeforeEach
    public void init() {
        mAccumulator = mock(ColumnSpecAccumulator.class);
        grouper = new GroupByClauseBuilder(mAccumulator);
    }

    @Test
    public void testGroupBy_PathProvider_AddsAColumnSpecWithPath_WhenProviderIsNotNull() {
        ArgumentCaptor<ColumnSpec<?>> captor = ArgumentCaptor.forClass(ColumnSpec.class);

        PathProvider mProvider = mock(PathProvider.class);
        doReturn(new String[] {"PATH_1", "PATH_2"}).when(mProvider).getPath();

        grouper.groupBy(mProvider);

        verify(mAccumulator).add(captor.capture());
        assertEquals(new ColumnSpec<>(new String[] {"PATH_1", "PATH_2"}), captor.getValue());
    }

    @Test
    public void testGroupBy_PathProvider_DoesNothign_WhenProviderIsNull() {
        grouper.groupBy((PathProvider) null);
        verifyNoInteractions(mAccumulator);
    }

    @Test
    public void testGroupBy_StringArray_AddsAColumnSpecWithPath_WhenProviderIsNotNull() {
        ArgumentCaptor<ColumnSpec<?>> captor = ArgumentCaptor.forClass(ColumnSpec.class);

        grouper.groupBy(new String[] {"PATH_1", "PATH_2"});

        verify(mAccumulator).add(captor.capture());
        assertEquals(new ColumnSpec<>(new String[] {"PATH_1", "PATH_2"}), captor.getValue());
    }

    @Test
    public void testGroupBy_StringArray_DoesNothing_WhenStringArrayIsNull() {
        grouper.groupBy((String[]) null);
        verifyNoInteractions(mAccumulator);
    }

    @Test
    public void testGroupBy_CriteriaSpec_AddsTheSpec_WhenSpecIsNotNull() {
        ArgumentCaptor<CriteriaSpec<?>> captor = ArgumentCaptor.forClass(CriteriaSpec.class);

        CriteriaSpec<?> mSpec = mock(CriteriaSpec.class);
        grouper.groupBy(mSpec);

        verify(mAccumulator).add(captor.capture());
        assertEquals(mSpec, captor.getValue());
    }

    @Test
    public void testGroupBy_CriteriaSpec_DoesNothing_WhenSpecIsNull() {
        grouper.groupBy((CriteriaSpec<?>) null);
        verifyNoInteractions(mAccumulator);
    }

    @Test
    public void testGetSelectClause_ReturnsListOfSelectionFromAccumulator() {
        Root<?> mRoot = mock(Root.class);
        CriteriaBuilder mCb = mock(CriteriaBuilder.class);
        CriteriaQuery<?> mCq = mock(CriteriaQuery.class);

        List<Expression<?>> mExprs = List.of(mock(Expression.class));
        doReturn(mExprs).when(mAccumulator).getColumns(mRoot, mCq, mCb);

        assertEquals(mExprs, grouper.getGroupByClause(mRoot, mCq, mCb));
    }
}
