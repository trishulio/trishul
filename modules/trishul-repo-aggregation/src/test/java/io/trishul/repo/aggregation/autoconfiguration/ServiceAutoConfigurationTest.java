package io.trishul.repo.aggregation.autoconfiguration;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.trishul.repo.jpa.query.resolver.QueryResolver;
import io.trishul.repo.aggregation.repo.AggregationRepository;

public class ServiceAutoConfigurationTest {
    private ServiceAutoConfiguration config;

    @BeforeEach
    public void init() {
        config = new ServiceAutoConfiguration();
    }
    
    @Test
    public void testAggrRepo_ReturnsInstanceOfAggregationRepository() {
        QueryResolver mResolver = mock(QueryResolver.class);

        AggregationRepository aggrRepo = config.aggregationRepository(mResolver);

        assertSame(AggregationRepository.class, aggrRepo.getClass());
    }
}
