package io.trishul.iaas.access.service.role.policy.attachment.service;

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
import io.trishul.crud.service.CrudEntityMergerService;
import io.trishul.crud.service.EntityMergerService;
import io.trishul.crud.service.LockService;
import io.trishul.iaas.access.role.attachment.policy.BaseIaasRolePolicyAttachment;
import io.trishul.iaas.access.role.attachment.policy.IaasRolePolicyAttachment;
import io.trishul.iaas.access.role.attachment.policy.IaasRolePolicyAttachmentAccessor;
import io.trishul.iaas.access.role.attachment.policy.IaasRolePolicyAttachmentId;
import io.trishul.iaas.access.role.attachment.policy.UpdateIaasRolePolicyAttachment;
import io.trishul.iaas.repository.IaasRepository;
import io.trishul.test.util.MockUtilProvider;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class IaasRolePolicyAttachmentServiceTest {
  private IaasRolePolicyAttachmentService service;
  private LockService mLockService;
  private EntityMergerService<IaasRolePolicyAttachmentId, IaasRolePolicyAttachment, BaseIaasRolePolicyAttachment<?>, UpdateIaasRolePolicyAttachment<?>> mMergerService;
  private IaasRepository<IaasRolePolicyAttachmentId, IaasRolePolicyAttachment, BaseIaasRolePolicyAttachment<?>, UpdateIaasRolePolicyAttachment<?>> mIaasRepo;

  @BeforeEach
  void init() {
    mLockService = mock(LockService.class);
    mMergerService = spy(new CrudEntityMergerService<>(new MockUtilProvider(), mLockService,
        BaseIaasRolePolicyAttachment.class, UpdateIaasRolePolicyAttachment.class,
        IaasRolePolicyAttachment.class, Set.of()));

    mIaasRepo = mock(IaasRepository.class);

    service = new IaasRolePolicyAttachmentService(mMergerService, mIaasRepo);
  }

  @Test
  void testExists_ReturnsTrue_WhenAllAttachmentsExist() {
    doAnswer(inv -> Map.of(inv.getArgument(0, Set.class).iterator().next(), true)).when(mIaasRepo)
        .exists(anySet());

    IaasRolePolicyAttachmentId id = new IaasRolePolicyAttachmentId("ROLE", "POLICY");
    assertTrue(service.exists(Set.of(id)));
  }

  @Test
  void testExists_ReturnsFalse_WhenAttachmentDoesNotExist() {
    doAnswer(inv -> Map.of(inv.getArgument(0, Set.class).iterator().next(), false)).when(mIaasRepo)
        .exists(anySet());

    IaasRolePolicyAttachmentId id = new IaasRolePolicyAttachmentId("ROLE", "POLICY");
    assertFalse(service.exists(Set.of(id)));
  }

  @Test
  void testExist_ReturnsTrue_WhenAttachmentExists() {
    doAnswer(inv -> Map.of(inv.getArgument(0, Set.class).iterator().next(), true)).when(mIaasRepo)
        .exists(anySet());

    IaasRolePolicyAttachmentId id = new IaasRolePolicyAttachmentId("ROLE", "POLICY");
    assertTrue(service.exist(id));
  }

  @Test
  void testExist_ReturnsFalse_WhenAttachmentDoesNotExist() {
    doAnswer(inv -> Map.of(inv.getArgument(0, Set.class).iterator().next(), false)).when(mIaasRepo)
        .exists(anySet());

    IaasRolePolicyAttachmentId id = new IaasRolePolicyAttachmentId("ROLE", "POLICY");
    assertFalse(service.exist(id));
  }

  @Test
  void testDelete_Set_CallsRepoDeleteWithIds() {
    IaasRolePolicyAttachmentId id1 = new IaasRolePolicyAttachmentId("ROLE_1", "POLICY_1");
    IaasRolePolicyAttachmentId id2 = new IaasRolePolicyAttachmentId("ROLE_2", "POLICY_2");
    doReturn(2L).when(mIaasRepo).delete(Set.of(id1, id2));

    long deleteCount = service.delete(Set.of(id1, id2));

    assertEquals(2, deleteCount);
  }

  @Test
  void testDelete_Id_CallsRepoDeleteWithId() {
    IaasRolePolicyAttachmentId id = new IaasRolePolicyAttachmentId("ROLE", "POLICY");
    doReturn(1L).when(mIaasRepo).delete(Set.of(id));

    long deleteCount = service.delete(id);

    assertEquals(1, deleteCount);
  }

  @Test
  void testGet_ReturnsAttachmentFromRepo() {
    IaasRolePolicyAttachmentId id = new IaasRolePolicyAttachmentId("ROLE", "POLICY");
    IaasRolePolicyAttachment attachment = new IaasRolePolicyAttachment(id);
    doReturn(List.of(attachment)).when(mIaasRepo).get(Set.of(id));

    IaasRolePolicyAttachment result = service.get(id);

    assertEquals(attachment, result);
  }

  @Test
  void testGet_ReturnsNull_WhenRepoReturnsEmptyList() {
    IaasRolePolicyAttachmentId id = new IaasRolePolicyAttachmentId("ROLE", "POLICY");
    doReturn(List.of()).when(mIaasRepo).get(Set.of(id));

    assertNull(service.get(id));
  }

  @Test
  void testGet_ReturnsNull_WhenRepoReturnsMultipleResults() {
    IaasRolePolicyAttachmentId id = new IaasRolePolicyAttachmentId("ROLE", "POLICY");
    IaasRolePolicyAttachment attachment1 = new IaasRolePolicyAttachment(id);
    IaasRolePolicyAttachment attachment2 = new IaasRolePolicyAttachment(id);
    doReturn(List.of(attachment1, attachment2)).when(mIaasRepo).get(Set.of(id));

    assertNull(service.get(id));
  }

  @Test
  void testGetAll_ReturnsAttachmentsFromRepo() {
    IaasRolePolicyAttachmentId id = new IaasRolePolicyAttachmentId("ROLE", "POLICY");
    IaasRolePolicyAttachment attachment = new IaasRolePolicyAttachment(id);
    doReturn(List.of(attachment)).when(mIaasRepo).get(Set.of(id));

    List<IaasRolePolicyAttachment> result = service.getAll(Set.of(id));

    assertEquals(List.of(attachment), result);
  }

  @Test
  void testGetByIds_ReturnsAttachmentsFromRepo() {
    IaasRolePolicyAttachmentId id = new IaasRolePolicyAttachmentId("ROLE", "POLICY");
    IaasRolePolicyAttachment attachment = new IaasRolePolicyAttachment(id);
    doReturn(List.of(attachment)).when(mIaasRepo).get(Set.of(id));

    List<IaasRolePolicyAttachment> result = service.getByIds(List.of(() -> id));

    assertEquals(List.of(attachment), result);
  }

  @Test
  void testGetByAccessorIds_ReturnsAttachmentsFromRepo() {
    IaasRolePolicyAttachmentId id = new IaasRolePolicyAttachmentId("ROLE", "POLICY");
    IaasRolePolicyAttachment attachment = new IaasRolePolicyAttachment(id);
    doReturn(List.of(attachment)).when(mIaasRepo).get(Set.of(id));

    class DummyAccessor implements IaasRolePolicyAttachmentAccessor<DummyAccessor> {
      @Override
      public DummyAccessor setIaasRolePolicyAttachment(IaasRolePolicyAttachment attachment) {
        return this;
      }

      @Override
      public IaasRolePolicyAttachment getIaasRolePolicyAttachment() {
        return new IaasRolePolicyAttachment(id);
      }
    }

    List<IaasRolePolicyAttachment> result = service.getByAccessorIds(List.of(new DummyAccessor()));

    assertEquals(List.of(attachment), result);
  }

  @Test
  void testAdd_ReturnsAddedEntities() {
    doAnswer(inv -> inv.getArgument(0, List.class)).when(mIaasRepo).add(anyList());

    IaasRolePolicyAttachmentId id1 = new IaasRolePolicyAttachmentId("ROLE_1", "POLICY_1");
    IaasRolePolicyAttachmentId id2 = new IaasRolePolicyAttachmentId("ROLE_2", "POLICY_2");
    List<BaseIaasRolePolicyAttachment<?>> additions =
        List.of(new IaasRolePolicyAttachment(id1), new IaasRolePolicyAttachment(id2));

    List<IaasRolePolicyAttachment> result = service.add(additions);

    List<IaasRolePolicyAttachment> expected =
        List.of(new IaasRolePolicyAttachment(id1), new IaasRolePolicyAttachment(id2));
    assertEquals(expected, result);
    verify(mIaasRepo, times(1)).add(anyList());
    verify(mMergerService).getAddEntities(additions);
  }

  @Test
  void testAdd_ReturnsNull_WhenArgIsNull() {
    assertNull(service.add(null));
  }

  @Test
  void testPut_ReturnsPutEntities() {
    doAnswer(inv -> inv.getArgument(0, List.class)).when(mIaasRepo).put(anyList());

    IaasRolePolicyAttachmentId id1 = new IaasRolePolicyAttachmentId("ROLE_1", "POLICY_1");
    IaasRolePolicyAttachmentId id2 = new IaasRolePolicyAttachmentId("ROLE_2", "POLICY_2");
    List<UpdateIaasRolePolicyAttachment<?>> updates =
        List.of(new IaasRolePolicyAttachment(id1), new IaasRolePolicyAttachment(id2));

    List<IaasRolePolicyAttachment> result = service.put(updates);

    List<IaasRolePolicyAttachment> expected =
        List.of(new IaasRolePolicyAttachment(id1), new IaasRolePolicyAttachment(id2));
    assertEquals(expected, result);
    verify(mIaasRepo, times(1)).put(anyList());
    verify(mMergerService).getPutEntities(null, updates);
  }

  @Test
  void testPut_ReturnsNull_WhenArgIsNull() {
    assertNull(service.put(null));
  }

  @Test
  void testPatch_ReturnsPatchedEntities() {
    IaasRolePolicyAttachmentId id1 = new IaasRolePolicyAttachmentId("ROLE_1", "POLICY_1");
    IaasRolePolicyAttachmentId id2 = new IaasRolePolicyAttachmentId("ROLE_2", "POLICY_2");

    doAnswer(inv -> {
      Iterator<IaasRolePolicyAttachmentId> it = inv.getArgument(0, Set.class).iterator();
      return List.of(new IaasRolePolicyAttachment(it.next()),
          new IaasRolePolicyAttachment(it.next()));
    }).when(mIaasRepo).get(anySet());
    doAnswer(inv -> inv.getArgument(0, List.class)).when(mIaasRepo).put(anyList());

    List<UpdateIaasRolePolicyAttachment<?>> updates =
        List.of(new IaasRolePolicyAttachment(id1), new IaasRolePolicyAttachment(id2));

    List<IaasRolePolicyAttachment> result = service.patch(updates);

    assertEquals(2, result.size());
    verify(mIaasRepo, times(1)).put(anyList());
    verify(mMergerService).getPatchEntities(anyList(), eq(updates));
  }

  @Test
  void testPatch_ReturnsNull_WhenArgIsNull() {
    assertNull(service.patch(null));
  }
}
