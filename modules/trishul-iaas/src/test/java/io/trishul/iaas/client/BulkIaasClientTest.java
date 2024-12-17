package io.trishul.iaas.client;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.trishul.iaas.repository.IaasRepository;
import io.trishul.test.model.BaseDummyCrudEntity;
import io.trishul.test.model.DummyCrudEntity;
import io.trishul.test.model.UpdateDummyCrudEntity;

import io.trishul.model.executor.BlockingAsyncExecutor;

public class BulkIaasClientTest {
    private IaasRepository<Long, DummyCrudEntity, BaseDummyCrudEntity, UpdateDummyCrudEntity> bulkClient;
    private IaasClient<Long, DummyCrudEntity, BaseDummyCrudEntity, UpdateDummyCrudEntity> mIaasClient;

    @BeforeEach
    public void init() {
        mIaasClient = mock(IaasClient.class);

        bulkClient = new BulkIaasClient<>(new BlockingAsyncExecutor(), mIaasClient);
    }

    @Test
    public void testGet_ReturnsIaasPoliciesFromAwsPolicies() {
        doAnswer(inv -> new DummyCrudEntity(inv.getArgument(0, Long.class))).when(mIaasClient).get(anyLong());

        List<DummyCrudEntity> policies = bulkClient.get(Set.of(1L, 2L));

        List<DummyCrudEntity> expected = List.of(new DummyCrudEntity(1L), new DummyCrudEntity(2L));

        assertThat(policies).hasSameElementsAs(expected);
    }

    @Test
    public void testAdd_ReturnsAddedPolicies_WhenClientReturnsAddedPolicies() {
        doAnswer(inv -> inv.getArgument(0, DummyCrudEntity.class)).when(mIaasClient).add(any(BaseDummyCrudEntity.class));

        List<BaseDummyCrudEntity> additions = List.of(
            new DummyCrudEntity(1L, "value1", "excludeValue1", 1),
            new DummyCrudEntity(2L, "value2", "excludeValue2", 2)
        );

        List<DummyCrudEntity> policies = bulkClient.add(additions);

        List<DummyCrudEntity> expected = List.of(
            new DummyCrudEntity(1L, "value1", "excludeValue1", 1),
            new DummyCrudEntity(2L, "value2", "excludeValue2", 2)
        );

        assertEquals(expected, policies);
        verify(mIaasClient, times(2)).add(any());
    }

    @Test
    public void testPut_ReturnsPutedPolicies_WhenClientReturnsPutedPolicies() {
        doAnswer(inv -> inv.getArgument(0, DummyCrudEntity.class)).when(mIaasClient).put(any(UpdateDummyCrudEntity.class));

        List<UpdateDummyCrudEntity> putitions = List.of(
            new DummyCrudEntity(1L, "value1", "excludeValue1", 1),
            new DummyCrudEntity(2L, "value2", "excludeValue2", 2)
        );

        List<DummyCrudEntity> policies = bulkClient.put(putitions);

        List<DummyCrudEntity> expected = List.of(
            new DummyCrudEntity(1L, "value1", "excludeValue1", 1),
            new DummyCrudEntity(2L, "value2", "excludeValue2", 2)
        );

        assertEquals(expected, policies);
        verify(mIaasClient, times(2)).put(any());
    }

    @Test
    public void testDelete_CallsCountOfDeletedItems() {
        doReturn(true).when(mIaasClient).delete(1L);
        doReturn(false).when(mIaasClient).delete(2L);
        doReturn(true).when(mIaasClient).delete(3L);

        long count = bulkClient.delete(Set.of(1L, 2L, 3L));

        assertEquals(2, count);
        verify(mIaasClient, times(3)).delete(anyLong());
    }

    @Test
    public void testExists_ReturnsMaps() {
        doAnswer(inv -> {
            Long id = inv.getArgument(0, Long.class);
            return (id % 2 == 0) ? new DummyCrudEntity(id) : null;
       }).when(mIaasClient).get(anyLong());

        Map<Long, Boolean> exists = bulkClient.exists(Set.of(1L, 2L, 3L, 4L));

        Map<Long, Boolean> expected = Map.of(1L, false, 2L, true, 3L, false, 4L, true);

        assertEquals(expected, exists);
    }
}
