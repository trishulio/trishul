package io.trishul.repo.autoconfiguration;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.trishul.repo.jpa.query.resolver.QueryResolver;

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

        // TODO: Move to separate module
        AggregationRepository aggrRepo = repoConf.aggrRepo(mResolver);

        assertSame(AggregationRepository.class, aggrRepo.getClass());
    }
}
