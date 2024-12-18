package io.trishul.repo.autoconfiguration;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;

import io.trishul.repo.jpa.query.resolver.QueryResolver;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
}
