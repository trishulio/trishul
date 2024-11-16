package io.trishul.crud.aggregation.service;

import java.util.Arrays;
import java.util.List;
import java.util.SortedSet;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import io.trishul.crud.aggregation.repo.AggregationRepository;
import io.trishul.crud.aggregation.service.function.AggregationFunction;
import io.trishul.repo.jpa.query.clause.group.builder.GroupByClauseBuilder;
import io.trishul.repo.jpa.query.clause.select.builder.SelectClauseBuilder;
import io.trishul.repo.jpa.query.path.provider.PathProvider;
import io.trishul.repo.jpa.query.spec.criteria.NullSpec;
import static io.trishul.repo.jpa.repository.service.RepoService.pageRequest;

public class AggregationService {
    private final AggregationRepository aggrRepo;

    public AggregationService(AggregationRepository aggrRepo) {
        this.aggrRepo = aggrRepo;
    }

    public <T> Page<T> getAggregation(
        Class<T> clazz,
        Specification<T> spec,
        AggregationFunction aggrFn,
        PathProvider aggrField,
        PathProvider[] groupBy,
        SortedSet<String> sort,
        boolean orderAscending,
        int page,
        int size
    ) {
        final PageRequest pageable = pageRequest(sort, orderAscending, page, size);

        final SelectClauseBuilder selector = new SelectClauseBuilder();
        final GroupByClauseBuilder grouper = new GroupByClauseBuilder();

        Arrays.stream(groupBy).forEach(col -> {
            selector.select(col);
            grouper.groupBy(col);
        });

        selector.select(aggrFn.getAggregation(aggrField));

        List<T> content = this.aggrRepo.getAggregation(clazz, selector, grouper, spec, pageable);
        Long total = this.getResultCount(clazz, grouper, spec);

        return new PageImpl<>(content, pageable, total);
    }

    public <T> Long getResultCount(Class<T> clazz, GroupByClauseBuilder groupBy, Specification<T> spec) {
        /**
         * Wrapper for the AggregationRepository's getResultCount method. The wrapper
         * doesn't use pageable to get the total number of rows. To minimize the data
         * fetched from DB in the Repo layer, the selection uses a null-literal as a
         * value
         */
        SelectClauseBuilder selector = new SelectClauseBuilder().select(new NullSpec());
        return this.aggrRepo.getResultCount(clazz, selector, groupBy, spec, null);
    }
}
