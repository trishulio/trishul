package sh.trishul.object.store.service.cors.config.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.amazonaws.services.s3.model.PublicAccessBlockConfiguration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sh.trishul.crud.service.CrudEntityMergerService;
import sh.trishul.crud.service.EntityMergerService;
import sh.trishul.crud.service.LockService;
import sh.trishul.iaas.repository.IaasRepository;
import sh.trishul.model.base.pojo.DeleteResult;
import sh.trishul.object.store.configuration.access.model.IaasObjectStoreAccessConfig;
import sh.trishul.object.store.configuration.access.model.IaasObjectStoreAccessConfigAccessor;
import sh.trishul.test.util.MockUtilProvider;

class IaasObjectStoreAccessConfigServiceTest {
  private IaasObjectStoreAccessConfigService service;

  private EntityMergerService<String, IaasObjectStoreAccessConfig, IaasObjectStoreAccessConfig, IaasObjectStoreAccessConfig> mMergerService;
  private IaasRepository<String, IaasObjectStoreAccessConfig, IaasObjectStoreAccessConfig, IaasObjectStoreAccessConfig> mIaasRepo;
  private LockService mLockService;

  @BeforeEach
  void init() {
    mLockService = mock(LockService.class);
    mMergerService = spy(new CrudEntityMergerService<>(new MockUtilProvider(), mLockService,
        IaasObjectStoreAccessConfig.class, IaasObjectStoreAccessConfig.class,
        IaasObjectStoreAccessConfig.class, Set.of("createdAt")));
    mIaasRepo = mock(IaasRepository.class);

    service = new IaasObjectStoreAccessConfigService(mMergerService, mIaasRepo);
  }

  @Test
  void testExists_ReturnsTrue_WhenAllObjectStoreIdsExists() {
    doAnswer(inv -> Map.of(inv.getArgument(0, Set.class).iterator().next(), true)).when(mIaasRepo)
        .exists(anySet());

    assertTrue(service.exists(Set.of("BUCKET_1")));
  }

  @Test
  void testExists_ReturnsFalse_WhenAllObjectStoreIdsDoesNotExists() {
    doAnswer(inv -> Map.of(inv.getArgument(0, Set.class).iterator().next(), false)).when(mIaasRepo)
        .exists(anySet());

    assertFalse(service.exists(Set.of("BUCKET_1")));
  }

  @Test
  void testExist_ReturnsTrue_WhenAllObjectStoreIdsExists() {
    doAnswer(inv -> Map.of(inv.getArgument(0, Set.class).iterator().next(), true)).when(mIaasRepo)
        .exists(anySet());

    assertTrue(service.exist("BUCKET_1"));
  }

  @Test
  void testExist_ReturnsFalse_WhenAllObjectStoreIdsDoesNotExists() {
    doAnswer(inv -> Map.of(inv.getArgument(0, Set.class).iterator().next(), false)).when(mIaasRepo)
        .exists(anySet());

    assertFalse(service.exist("BUCKET_1"));
  }

  @Test
  void testDelete_Set_CallsRepoDeleteWithIds() {
    doReturn(99L).when(mIaasRepo).delete(Set.of("BUCKET_1", "BUCKET_2"));
    DeleteResult deleteCount = service.delete(Set.of("BUCKET_1", "BUCKET_2"));

    assertEquals(new DeleteResult(99L), deleteCount);
  }

  @Test
  void testDelete_Id_CallsRepoDeleteWithIds() {
    doReturn(1L).when(mIaasRepo).delete(Set.of("BUCKET_1"));
    DeleteResult deleteCount = service.delete("BUCKET_1");

    assertEquals(new DeleteResult(1L), deleteCount);
  }

  @Test
  void testGet_ReturnsObjectStoreConfigFromRepo() {
    IaasObjectStoreAccessConfig expected = new IaasObjectStoreAccessConfig();
    doAnswer(inv -> {
      return List.of(expected);
    }).when(mIaasRepo).get(anySet());

    IaasObjectStoreAccessConfig actual = service.get("BUCKET_1");

    assertEquals(expected, actual);
  }

  @Test
  void testGet_ReturnsNull_WhenNoObjectStoreConfigIsFound() {
    doReturn(new ArrayList<>()).when(mIaasRepo).get(anySet());

    IaasObjectStoreAccessConfig actual = service.get("BUCKET_1");

    assertNull(actual);
  }

  @Test
  void testGetAll_ReturnsObjectStoreConfigFromRepo() {
    List<IaasObjectStoreAccessConfig> expected = List.of(new IaasObjectStoreAccessConfig());
    doAnswer(inv -> {
      return expected;
    }).when(mIaasRepo).get(anySet());

    List<IaasObjectStoreAccessConfig> actual = service.getAll(Set.of("BUCKET_1"));

    assertEquals(expected, actual);
  }

  @Test
  void testGetByIds_ReturnObjectStoreConfigsFromRepo() {
    List<IaasObjectStoreAccessConfig> expected = List.of(new IaasObjectStoreAccessConfig());
    doAnswer(inv -> {
      return expected;
    }).when(mIaasRepo).get(anySet());

    List<IaasObjectStoreAccessConfig> actual = service.getByIds(Set.of(() -> "BUCKET_1"));

    assertEquals(expected, actual);
  }

  @Test
  void testGetByAccessorIds_ReturnsObjectStoreConfigFromRepo() {
    List<IaasObjectStoreAccessConfig> expected = List.of(new IaasObjectStoreAccessConfig());
    doAnswer(inv -> {
      return expected;
    }).when(mIaasRepo).get(anySet());

    class DummyIaasObjectStoreAccessConfigAccessor
        implements IaasObjectStoreAccessConfigAccessor<DummyIaasObjectStoreAccessConfigAccessor> {
      @Override
      public DummyIaasObjectStoreAccessConfigAccessor setIaasObjectStoreAccessConfig(
          IaasObjectStoreAccessConfig attachment) {
        return this;
      }

      @Override
      public IaasObjectStoreAccessConfig getIaasObjectStoreAccessConfig() {
        return new IaasObjectStoreAccessConfig();
      }
    }

    IaasObjectStoreAccessConfigAccessor<?> accessor
        = new DummyIaasObjectStoreAccessConfigAccessor();

    List<IaasObjectStoreAccessConfig> actual = service.getByAccessorIds(Set.of(accessor));

    assertEquals(expected, actual);
  }

  @Test
  void testAdd_ReturnsAddedRepoEntities_AfterSavingAddEntitiesFromMergerService() {
    List<IaasObjectStoreAccessConfig> expected
        = List.of(new IaasObjectStoreAccessConfig("BUCKET_1", new PublicAccessBlockConfiguration()),
            new IaasObjectStoreAccessConfig("BUCKET_2", new PublicAccessBlockConfiguration()));

    doAnswer(inv -> expected).when(mIaasRepo).add(anyList());

    List<IaasObjectStoreAccessConfig> additions
        = List.of(new IaasObjectStoreAccessConfig("BUCKET_1", new PublicAccessBlockConfiguration()),
            new IaasObjectStoreAccessConfig("BUCKET_2", new PublicAccessBlockConfiguration()));

    List<IaasObjectStoreAccessConfig> configs = service.add(additions);

    assertEquals(expected, configs);
    verify(mIaasRepo, times(1)).add(additions);
    verify(mMergerService).getAddEntities(additions);
  }

  @Test
  void testAdd_DoesNothingReturnsNull_WhenArgIsNull() {
    assertNull(service.add(null));
  }

  @Test
  void testPut_ReturnsPutRepoEntities_AfterSavingPutEntitiesFromMergerService() {
    List<IaasObjectStoreAccessConfig> expected
        = List.of(new IaasObjectStoreAccessConfig("BUCKET_1", new PublicAccessBlockConfiguration()),
            new IaasObjectStoreAccessConfig("BUCKET_2", new PublicAccessBlockConfiguration()));

    doAnswer(inv -> expected).when(mIaasRepo).put(anyList());

    List<IaasObjectStoreAccessConfig> updates
        = List.of(new IaasObjectStoreAccessConfig("BUCKET_1", new PublicAccessBlockConfiguration()),
            new IaasObjectStoreAccessConfig("BUCKET_2", new PublicAccessBlockConfiguration()));

    List<IaasObjectStoreAccessConfig> configs = service.put(updates);

    assertEquals(expected, configs);
    verify(mIaasRepo, times(1)).put(updates);
    verify(mMergerService).getPutEntities(null, updates);
  }

  @Test
  void testPut_DoesNothingReturnsNull_WhenArgIsNull() {
    assertNull(service.put(null));
  }

  @Test
  void testPatch_ReturnsPatchRepoEntities_AfterSavingPatchEntitiesFromMergerService() {
    List<IaasObjectStoreAccessConfig> expected
        = List.of(new IaasObjectStoreAccessConfig("BUCKET_1", new PublicAccessBlockConfiguration()),
            new IaasObjectStoreAccessConfig("BUCKET_2", new PublicAccessBlockConfiguration()));

    doAnswer(inv -> expected).when(mIaasRepo).put(anyList());

    doAnswer(inv -> {
      return expected;
    }).when(mIaasRepo).get(anySet());

    List<IaasObjectStoreAccessConfig> updates
        = List.of(new IaasObjectStoreAccessConfig("BUCKET_1", new PublicAccessBlockConfiguration()),
            new IaasObjectStoreAccessConfig("BUCKET_2", new PublicAccessBlockConfiguration()));

    List<IaasObjectStoreAccessConfig> attachments = service.patch(updates);

    assertEquals(expected, attachments);
    verify(mIaasRepo, times(1)).put(updates);
    verify(mMergerService).getPatchEntities(anyList(), eq(updates));
  }

  @Test
  void testPatch_DoesNothingReturnsNull_WhenArgIsNull() {
    assertNull(service.patch(null));
  }
}
