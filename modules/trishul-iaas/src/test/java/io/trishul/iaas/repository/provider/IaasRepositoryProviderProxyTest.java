package io.company.brewcraft.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.model.BaseDummyCrudEntity;
import io.company.brewcraft.model.DummyCrudEntity;
import io.company.brewcraft.model.IaasRepositoryProvider;
import io.company.brewcraft.model.UpdateDummyCrudEntity;

public class IaasRepositoryProviderProxyTest {
    private IaasRepository<Long, DummyCrudEntity, BaseDummyCrudEntity, UpdateDummyCrudEntity> proxy;
    private IaasRepository<Long, DummyCrudEntity, BaseDummyCrudEntity, UpdateDummyCrudEntity> mDelegate;
    private IaasRepositoryProvider<Long, DummyCrudEntity, BaseDummyCrudEntity, UpdateDummyCrudEntity> mProvider;

    @BeforeEach
    public void init() {
        mProvider = mock(IaasRepositoryProvider.class);

        mDelegate = mock(IaasRepository.class);
        doReturn(mDelegate).when(mProvider).getIaasRepository();

        proxy = new IaasRepositoryProviderProxy<>(mProvider);
    }

    @Test
    public void testGet_ReturnsValueFromDelegate() {
        doAnswer(inv -> inv.getArgument(0, Set.class).stream().map(id -> new DummyCrudEntity((Long) id)).toList()).when(mDelegate).get(anySet());

        List<DummyCrudEntity> entities = proxy.get(Set.of(1L, 2L));

        List<DummyCrudEntity> expected = List.of(new DummyCrudEntity(1L), new DummyCrudEntity(2L));

        assertThat(entities).hasSameElementsAs(expected);
    }

    @Test
    public void testAdd_ReturnsValueFromDelegate() {
        doAnswer(inv -> inv.getArgument(0, List.class)).when(mDelegate).add(anyList());

        List<DummyCrudEntity> entities = proxy.add(List.of(new DummyCrudEntity(1L), new DummyCrudEntity(2L)));

        List<DummyCrudEntity> expected = List.of(new DummyCrudEntity(1L), new DummyCrudEntity(2L));

        assertEquals(expected, entities);
    }

    @Test
    public void testPut_ReturnsValueFromDelegate() {
        doAnswer(inv -> inv.getArgument(0, List.class)).when(mDelegate).put(anyList());

        List<DummyCrudEntity> entities = proxy.put(List.of(new DummyCrudEntity(1L), new DummyCrudEntity(2L)));

        List<DummyCrudEntity> expected = List.of(new DummyCrudEntity(1L), new DummyCrudEntity(2L));

        assertEquals(expected, entities);
    }

    @Test
    public void testDelete_ReturnsValueFromDelegate() {
        doReturn(99L).when(mDelegate).delete(Set.of(1L, 2L));

        long count = proxy.delete(Set.of(1L, 2L));
        assertEquals(99L, count);
    }

    @Test
    public void testExists_ReturnsValueFromDelegate() {
        doReturn(Map.of(1L, false, 2L, true)).when(mDelegate).exists(Set.of(1L, 2L));

        assertEquals(Map.of(1L, false, 2L, true), proxy.exists(Set.of(1L, 2L)));
    }
}
