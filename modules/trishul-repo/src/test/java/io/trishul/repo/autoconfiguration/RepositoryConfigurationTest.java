package io.company.brewcraft.configuration;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.repository.AggregationRepository;
import io.company.brewcraft.repository.QueryResolver;

public class RepositoryConfigurationTest {
    private RepositoryConfiguration repoConf;

    @BeforeEach
    public void init() {
        repoConf = new RepositoryConfiguration();
    }

    @Test
    public void testQueryResolver_ReturnsInstanceOfQueryResolver() {
        EntityManager mEm = mock(EntityManager.class);
        QueryResolver queryResolver = repoConf.queryResolver(mEm);

        assertSame(QueryResolver.class, queryResolver.getClass());
    }

    @Test
    public void testAggrRepo_ReturnsInstanceOfAggregationRepository() {
        QueryResolver mResolver = mock(QueryResolver.class);

        AggregationRepository aggrRepo = repoConf.aggrRepo(mResolver);

        assertSame(AggregationRepository.class, aggrRepo.getClass());
    }
}
