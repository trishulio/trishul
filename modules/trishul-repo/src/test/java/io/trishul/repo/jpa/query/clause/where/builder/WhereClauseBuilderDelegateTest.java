package io.trishul.repo.jpa.query.clause.where.builder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import io.trishul.repo.jpa.query.spec.accumulator.PredicateSpecAccumulator;
import io.trishul.repo.jpa.query.spec.criteria.BetweenSpec;
import io.trishul.repo.jpa.query.spec.criteria.ColumnSpec;
import io.trishul.repo.jpa.query.spec.criteria.CriteriaSpec;
import io.trishul.repo.jpa.query.spec.criteria.InSpec;
import io.trishul.repo.jpa.query.spec.criteria.IsNullSpec;
import io.trishul.repo.jpa.query.spec.criteria.LikeSpec;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.data.jpa.domain.Specification;

public class WhereClauseBuilderDelegateTest {
    private WhereClauseBuilderDelegate builder;
    private PredicateSpecAccumulator mAccumulator;

    private ArgumentCaptor<CriteriaSpec<Boolean>> captor;

    @BeforeEach
    public void init() {
        mAccumulator = mock(PredicateSpecAccumulator.class);
        captor = ArgumentCaptor.forClass(CriteriaSpec.class);

        builder = new WhereClauseBuilderDelegate(mAccumulator);
    }

    @Test
    public void testIsNull_AddsIsNullSpecAndResetNotFlag() {
        builder.isNull(new String[] {"layer-1"});

        CriteriaSpec<Boolean> expected = new IsNullSpec(new ColumnSpec<>(new String[] {"layer-1"}));
        verify(mAccumulator).add(captor.capture());
        assertEquals(expected, captor.getValue());

        verify(mAccumulator).setIsNot(false);
    }

    @Test
    public void testIn_AddsInSpecAndResetNotFlag_WhenCollectionIsNotNull() {
        builder.in(new String[] {"layer-1"}, List.of("V1", "V2"));

        CriteriaSpec<Boolean> expected =
                new InSpec<>(new ColumnSpec<>(new String[] {"layer-1"}), List.of("V1", "V2"));
        verify(mAccumulator).add(captor.capture());
        assertEquals(expected, captor.getValue());

        verify(mAccumulator).setIsNot(false);
    }

    @Test
    public void testIn_AddsNothingAndResetNotFlag_WhenCollectionIsNull() {
        builder.in(new String[] {"layer-1"}, null);
        verify(mAccumulator).setIsNot(false);
        verifyNoMoreInteractions(mAccumulator);
    }

    @Test
    public void testLike_AddsLikeSpecAndResetFlag_WhenCollectionIsNotNull() {
        builder.like(new String[] {"layer-1"}, Set.of("V1", "V2"));

        verify(mAccumulator, times(2)).add(captor.capture());

        List<CriteriaSpec<Boolean>> expected =
                List.of(
                        new LikeSpec(new ColumnSpec<>(new String[] {"layer-1"}), "V1"),
                        new LikeSpec(new ColumnSpec<>(new String[] {"layer-1"}), "V2"));
        Assertions.assertThat(captor.getAllValues()).containsExactlyInAnyOrderElementsOf(expected);

        verify(mAccumulator).setIsNot(false);
        verifyNoMoreInteractions(mAccumulator);
    }

    @Test
    public void testLike_AddsNothingAndResetFlag_WhenCollectionIsNull() {
        builder.like(new String[] {"layer-1"}, null);
        verify(mAccumulator).setIsNot(false);
        verifyNoMoreInteractions(mAccumulator);
    }

    @Test
    public void testBetween_AddsBetweenSpecAndResetFlag_WhenStartAndEndAreNotNull() {
        builder.between(
                new String[] {"layer-1"},
                LocalDateTime.of(2000, 1, 1, 1, 1),
                LocalDateTime.of(2000, 1, 1, 1, 1));

        CriteriaSpec<Boolean> expected =
                new BetweenSpec<>(
                        new ColumnSpec<>(new String[] {"layer-1"}),
                        LocalDateTime.of(2000, 1, 1, 1, 1),
                        LocalDateTime.of(2000, 1, 1, 1, 1));

        verify(mAccumulator).add(captor.capture());
        assertEquals(expected, captor.getValue());

        verify(mAccumulator).setIsNot(false);
    }

    @Test
    public void testBetween_AddsNothingAndResetFlag_WhenStartAndEndAreNull() {
        builder.between(new String[] {"layer-1"}, null, null);
        verify(mAccumulator).setIsNot(false);
        verifyNoMoreInteractions(mAccumulator);
    }

    @Test
    public void testPredicate_callsSetIsPredicate() {
        builder.predicate(true);

        verify(mAccumulator).setIsPredicate(true);
    }

    @Test
    public void
            testBuild_ReturnSpecification_WithToPredicateFunctionThatGetsAllPredicatesAndCombinesThem() {
        Specification<Integer> spec = builder.build();

        CriteriaBuilder mCriteriaBuilder = mock(CriteriaBuilder.class);
        Root<Integer> mRoot = mock(Root.class);
        CriteriaQuery<?> mQuery = mock(CriteriaQuery.class);

        Predicate[] mPreds = new Predicate[] {mock(Predicate.class), mock(Predicate.class)};
        doReturn(mPreds).when(mAccumulator).getPredicates(mRoot, mQuery, mCriteriaBuilder);

        Predicate mCombined = mock(Predicate.class);
        doReturn(mCombined).when(mCriteriaBuilder).and(mPreds);

        Predicate ret = spec.toPredicate(mRoot, mQuery, mCriteriaBuilder);

        assertEquals(ret, mCombined);
    }
}
