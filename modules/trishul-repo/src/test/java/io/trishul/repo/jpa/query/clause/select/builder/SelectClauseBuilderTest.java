package io.trishul.repo.jpa.query.clause.select.builder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import io.trishul.repo.jpa.query.path.provider.PathProvider;
import io.trishul.repo.jpa.query.spec.accumulator.ColumnSpecAccumulator;
import io.trishul.repo.jpa.query.spec.criteria.ColumnSpec;
import io.trishul.repo.jpa.query.spec.criteria.CriteriaSpec;

public class SelectClauseBuilderTest {
    private SelectClauseBuilder selector;
    private ColumnSpecAccumulator mAccumulator;

    @BeforeEach
    public void init() {
        mAccumulator = mock(ColumnSpecAccumulator.class);
        selector = new SelectClauseBuilder(mAccumulator);
    }

    @Test
    public void testSelect_PathProvider_AddsAColumnSpecWithPath_WhenProviderIsNotNull() {
        ArgumentCaptor<ColumnSpec<?>> captor = ArgumentCaptor.forClass(ColumnSpec.class);
        doNothing().when(mAccumulator).add(captor.capture());

        PathProvider mProvider = mock(PathProvider.class);
        doReturn(new String[] {"PATH_1", "PATH_2"}).when(mProvider).getPath();

        selector.select(mProvider);

        assertEquals(new ColumnSpec<>(new String[] {"PATH_1", "PATH_2"}), captor.getValue());
    }

    @Test
    public void testSelect_PathProvider_DoesNothign_WhenProviderIsNull() {
        ArgumentCaptor<ColumnSpec<?>> captor = ArgumentCaptor.forClass(ColumnSpec.class);
        doNothing().when(mAccumulator).add(captor.capture());

        selector.select((PathProvider) null);

        assertEquals(List.of(), captor.getAllValues());
    }

    @Test
    public void testSelect_StringArray_AddsAColumnSpecWithPath_WhenProviderIsNotNull() {
        ArgumentCaptor<ColumnSpec<?>> captor = ArgumentCaptor.forClass(ColumnSpec.class);
        doNothing().when(mAccumulator).add(captor.capture());

        selector.select(new String[] {"PATH_1", "PATH_2"});

        assertEquals(new ColumnSpec<>(new String[] {"PATH_1", "PATH_2"}), captor.getValue());
    }

    @Test
    public void testSelect_StringArray_DoesNothing_WhenStringArrayIsNull() {
        ArgumentCaptor<ColumnSpec<?>> captor = ArgumentCaptor.forClass(ColumnSpec.class);
        doNothing().when(mAccumulator).add(captor.capture());

        selector.select((String[]) null);

        assertEquals(List.of(), captor.getAllValues());
    }

    @Test
    public void testSelect_CriteriaSpec_AddsTheSpec_WhenSpecIsNotNull() {
        ArgumentCaptor<ColumnSpec<?>> captor = ArgumentCaptor.forClass(ColumnSpec.class);
        doNothing().when(mAccumulator).add(captor.capture());

        CriteriaSpec<?> mSpec = mock(CriteriaSpec.class);
        selector.select(mSpec);

        assertEquals(mSpec, captor.getValue());
    }

    @Test
    public void testSelect_CriteriaSpec_DoesNothing_WhenSpecIsNull() {
        ArgumentCaptor<ColumnSpec<?>> captor = ArgumentCaptor.forClass(ColumnSpec.class);
        doNothing().when(mAccumulator).add(captor.capture());

        selector.select((CriteriaSpec<?>) null);

        assertEquals(List.of(), captor.getAllValues());
    }

    @Test
    public void testGetSelectClause_ReturnsListOfSelectionFromAccumulator() {
        Root<?> mRoot = mock(Root.class);
        CriteriaBuilder mCb = mock(CriteriaBuilder.class);
        CriteriaQuery<?> mCq = mock(CriteriaQuery.class);

        List<Expression<?>> mExprs = List.of(mock(Expression.class));
        doReturn(mExprs).when(mAccumulator).getColumns(mRoot, mCq, mCb);

        assertEquals(mExprs, selector.getSelectClause(mRoot, mCq, mCb));
    }
}