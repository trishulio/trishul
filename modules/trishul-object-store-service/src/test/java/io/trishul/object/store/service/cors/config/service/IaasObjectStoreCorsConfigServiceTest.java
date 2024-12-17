package io.trishul.object.store.service.cors.config.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.amazonaws.services.s3.model.BucketCrossOriginConfiguration;

import io.trishul.crud.service.LockService;
import io.trishul.crud.service.UpdateService;
import io.trishul.iaas.repository.IaasRepository;
import io.trishul.object.store.configuration.cors.model.IaasObjectStoreCorsConfiguration;
import io.trishul.crud.service.SimpleUpdateService;
import io.trishul.test.util.MockUtilProvider;
import io.trishul.object.store.configuration.cors.model.IaasObjectStoreCorsConfigurationAccessor;

public class IaasObjectStoreCorsConfigServiceTest {
    private IaasObjectStoreCorsConfigService service;

    private UpdateService<String, IaasObjectStoreCorsConfiguration, IaasObjectStoreCorsConfiguration, IaasObjectStoreCorsConfiguration> mUpdateService;
    private IaasRepository<String, IaasObjectStoreCorsConfiguration, IaasObjectStoreCorsConfiguration, IaasObjectStoreCorsConfiguration> mIaasRepo;
    private LockService mLockService;

    @BeforeEach
    public void init() {
        mLockService = mock(LockService.class);
        mUpdateService = new SimpleUpdateService<>(new MockUtilProvider(), mLockService, IaasObjectStoreCorsConfiguration.class, IaasObjectStoreCorsConfiguration.class, IaasObjectStoreCorsConfiguration.class, Set.of("createdAt"));
        mIaasRepo = mock(IaasRepository.class);

        service = new IaasObjectStoreCorsConfigService(mUpdateService, mIaasRepo);
    }

    @Test
    public void testExists_ReturnsTrue_WhenAllAttachmentsExists() {
        doAnswer(inv -> Map.of(inv.getArgument(0, Set.class).iterator().next(), true)).when(mIaasRepo).exists(anySet());

        assertTrue(service.exists(Set.of("BUCKET_1")));
    }

    @Test
    public void testExists_ReturnsFalse_WhenAllAttachmentsDoesNotExists() {
        doAnswer(inv -> Map.of(inv.getArgument(0, Set.class).iterator().next(), false)).when(mIaasRepo).exists(anySet());

        assertFalse(service.exists(Set.of("BUCKET_1")));
    }

    @Test
    public void testExist_ReturnsTrue_WhenAllAttachmentsExists() {
        doAnswer(inv -> Map.of(inv.getArgument(0, Set.class).iterator().next(), true)).when(mIaasRepo).exists(anySet());

        assertTrue(service.exist("BUCKET_1"));
    }

    @Test
    public void testExist_ReturnsFalse_WhenAllAttachmentsDoesNotExist() {
        doAnswer(inv -> Map.of(inv.getArgument(0, Set.class).iterator().next(), false)).when(mIaasRepo).exists(anySet());

        assertFalse(service.exist("BUCKET_1"));
    }

    @Test
    public void testDelete_Set_CallsRepoDeleteWithIds() {
        doReturn(99L).when(mIaasRepo).delete(Set.of("BUCKET_1", "BUCKET_2"));
        long deleteCount = service.delete(Set.of("BUCKET_1", "BUCKET_2"));

        assertEquals(99L, deleteCount);
    }

    @Test
    public void testDelete_Id_CallsRepoDeleteWithIds() {
        doReturn(1L).when(mIaasRepo).delete(Set.of("BUCKET_1", "BUCKET_2"));
        long deleteCount = service.delete(Set.of("BUCKET_1", "BUCKET_2"));

        assertEquals(1L, deleteCount);
    }

    @Test
    public void testGet_ReturnsAttachmentFromRepo() {
        IaasObjectStoreCorsConfiguration expected = new IaasObjectStoreCorsConfiguration();
        doAnswer(inv -> {
            return List.of(expected);
        }).when(mIaasRepo).get(anySet());

        IaasObjectStoreCorsConfiguration actual = service.get("BUCKET_1");

        assertEquals(expected, actual);
    }

    @Test
    public void testGet_ReturnsNull_WhenNoAttachmentIsFound() {
        doReturn(new ArrayList<>()).when(mIaasRepo).get(anySet());

        IaasObjectStoreCorsConfiguration actual = service.get("BUCKET_1");

        assertNull(actual);
    }

    @Test
    public void testGetAll_ReturnsAttachmentFromRepo() {
        List<IaasObjectStoreCorsConfiguration> expected = List.of(new IaasObjectStoreCorsConfiguration());
        doAnswer(inv -> {
            return expected;
        }).when(mIaasRepo).get(anySet());

        List<IaasObjectStoreCorsConfiguration> actual = service.getAll(Set.of("BUCKET_1"));

        assertEquals(expected, actual);
    }

    @Test
    public void testGetByIds_ReturnAttachmentsFromRepo() {
        List<IaasObjectStoreCorsConfiguration> expected = List.of(new IaasObjectStoreCorsConfiguration());
        doAnswer(inv -> {
            return expected;
        }).when(mIaasRepo).get(anySet());

        List<IaasObjectStoreCorsConfiguration> actual = service.getByIds(Set.of(() -> "BUCKET_1"));

        assertEquals(expected, actual);
    }

    @Test
    public void testGetByAccessorIds_ReturnsAttachmentFromRepo() {
        List<IaasObjectStoreCorsConfiguration> expected = List.of(new IaasObjectStoreCorsConfiguration());
        doAnswer(inv -> {
            return expected;
        }).when(mIaasRepo).get(anySet());

        IaasObjectStoreCorsConfigurationAccessor accessor = new IaasObjectStoreCorsConfigurationAccessor() {
            @Override
            public void setIaasObjectStoreCorsConfiguration(IaasObjectStoreCorsConfiguration attachment) {
            }
            @Override
            public IaasObjectStoreCorsConfiguration getIaasObjectStoreCorsConfiguration() {
                return new IaasObjectStoreCorsConfiguration();
            }
        };

        List<IaasObjectStoreCorsConfiguration> actual = service.getByAccessorIds(Set.of(accessor));

        assertEquals(expected, actual);
    }

    @Test
    public void testAdd_ReturnsAddedRepoEntities_AfterSavingAddEntitiesFromUpdateService() {
        List<IaasObjectStoreCorsConfiguration> expected = List.of(
            new IaasObjectStoreCorsConfiguration("BUCKET_1", new BucketCrossOriginConfiguration()),
            new IaasObjectStoreCorsConfiguration("BUCKET_2", new BucketCrossOriginConfiguration())
        );

        doAnswer(inv -> expected).when(mIaasRepo).add(anyList());

        List<IaasObjectStoreCorsConfiguration> additions = List.of(
            new IaasObjectStoreCorsConfiguration("BUCKET_1", new BucketCrossOriginConfiguration()),
            new IaasObjectStoreCorsConfiguration("BUCKET_2", new BucketCrossOriginConfiguration())
        );

        List<IaasObjectStoreCorsConfiguration> configs = service.add(additions);

        assertEquals(expected, configs);
        verify(mIaasRepo, times(1)).add(additions);
        verify(mUpdateService).getAddEntities(additions);
    }

    @Test
    public void testAdd_DoesNothingReturnsNull_WhenArgIsNull() {
        assertNull(service.add(null));
    }

    @Test
    public void testPut_ReturnsPutRepoEntities_AfterSavingPutEntitiesFromUpdateService() {
        List<IaasObjectStoreCorsConfiguration> expected = List.of(
            new IaasObjectStoreCorsConfiguration("BUCKET_1", new BucketCrossOriginConfiguration()),
            new IaasObjectStoreCorsConfiguration("BUCKET_2", new BucketCrossOriginConfiguration())
        );

        doAnswer(inv -> expected).when(mIaasRepo).put(anyList());

        List<IaasObjectStoreCorsConfiguration> updates = List.of(
            new IaasObjectStoreCorsConfiguration("BUCKET_1", new BucketCrossOriginConfiguration()),
            new IaasObjectStoreCorsConfiguration("BUCKET_2", new BucketCrossOriginConfiguration())
        );

        List<IaasObjectStoreCorsConfiguration> configs = service.put(updates);

        assertEquals(expected, configs);
        verify(mIaasRepo, times(1)).put(updates);
        verify(mUpdateService).getPutEntities(null, updates);
    }

    @Test
    public void testPut_DoesNothingReturnsNull_WhenArgIsNull() {
        assertNull(service.put(null));
    }

    @Test
    public void testPatch_ReturnsPatchRepoEntities_AfterSavingPatchEntitiesFromUpdateService() {
        List<IaasObjectStoreCorsConfiguration> expected = List.of(
            new IaasObjectStoreCorsConfiguration("BUCKET_1", new BucketCrossOriginConfiguration()),
            new IaasObjectStoreCorsConfiguration("BUCKET_2", new BucketCrossOriginConfiguration())
        );

        doAnswer(inv -> expected).when(mIaasRepo).put(anyList());

        doAnswer(inv -> {
            return expected;
        }).when(mIaasRepo).get(anySet());

        List<IaasObjectStoreCorsConfiguration> updates = List.of(
            new IaasObjectStoreCorsConfiguration("BUCKET_1", new BucketCrossOriginConfiguration()),
            new IaasObjectStoreCorsConfiguration("BUCKET_2", new BucketCrossOriginConfiguration())
        );

        List<IaasObjectStoreCorsConfiguration> attachments = service.patch(updates);

        assertEquals(expected, attachments);
        verify(mIaasRepo, times(1)).put(updates);
        verify(mUpdateService).getPatchEntities(anyList(), eq(updates));
    }

    @Test
    public void testPatch_DoesNothingReturnsNull_WhenArgIsNull() {
        assertNull(service.patch(null));
    }
}
