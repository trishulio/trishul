package io.trishul.iaas.client;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.trishul.iaas.repository.IaasRepository;
import io.trishul.model.base.entity.DummyCrudEntity;
import io.trishul.test.model.BaseDummyCrudEntity;
import io.trishul.test.model.UpdateDummyCrudEntity;

public class SequentialExecutorTest {
    private IaasRepository<Long, DummyCrudEntity, BaseDummyCrudEntity, UpdateDummyCrudEntity> seqClient;
    private IaasClient<Long, DummyCrudEntity, BaseDummyCrudEntity, UpdateDummyCrudEntity> mIaasClient;

    @BeforeEach
    public void init() {
        mIaasClient = mock(IaasClient.class);
        seqClient = new SequentialExecutor<>(mIaasClient);
    }

    @Test
    public void testGet_ReturnsResultListFromSequentialClientGet() {
        doAnswer(inv -> new DummyCrudEntity(inv.getArgument(0, Long.class))).when(mIaasClient).get(anyLong());

        List<DummyCrudEntity> entities = seqClient.get(Set.of(1L, 2L));

        List<DummyCrudEntity> expected = List.of(new DummyCrudEntity(1L), new DummyCrudEntity(2L));

        assertThat(entities).hasSameElementsAs(expected);
    }

    @Test
    public void testAdd_ReturnsResultListFromSequentialClientAdd() {
        doAnswer(inv -> inv.getArgument(0)).when(mIaasClient).add(any());

        List<DummyCrudEntity> entities = seqClient.add(List.of(new DummyCrudEntity(1L), new DummyCrudEntity(2L)));

        List<DummyCrudEntity> expected = List.of(new DummyCrudEntity(1L), new DummyCrudEntity(2L));

        assertEquals(expected, entities);
    }

    @Test
    public void testPut_ReturnsResultListFromSequentialClientPut() {
        doAnswer(inv -> inv.getArgument(0)).when(mIaasClient).put(any());

        List<DummyCrudEntity> entities = seqClient.put(List.of(new DummyCrudEntity(1L), new DummyCrudEntity(2L)));

        List<DummyCrudEntity> expected = List.of(new DummyCrudEntity(1L), new DummyCrudEntity(2L));

        assertEquals(expected, entities);
    }

    @Test
    public void testDelete_ReturnsCountOfDeleteTrue_FromClientDelete() {
        doAnswer(inv -> inv.getArgument(0, Long.class) % 2 == 0).when(mIaasClient).delete(anyLong());

        assertEquals(3, seqClient.delete(Set.of(2L, 4L, 6L, 7L)));
        assertEquals(0, seqClient.delete(Set.of(7L)));
    }

    @Test
    public void testExists_ReturnsMapOfIdBoolean_FromClientExist() {
        doAnswer(inv -> {
            Long id = inv.getArgument(0, Long.class);

            return id % 2 == 0 ? new DummyCrudEntity(id) : null;
        }).when(mIaasClient).get(anyLong());

        Map<Long, Boolean> expected = Map.of(1L, false, 2L, true, 3L, false);
        assertEquals(expected, seqClient.exists(Set.of(1L, 2L, 3L)));
    }
}
