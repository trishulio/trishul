package io.trishul.iaas.tenant.idp.management.service;

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
import io.trishul.crud.service.LockService;
import io.trishul.crud.service.CrudEntityMergerService;
import io.trishul.crud.service.EntityMergerService;
import io.trishul.iaas.access.role.model.IaasRole;
import io.trishul.iaas.idp.tenant.model.BaseIaasIdpTenant;
import io.trishul.iaas.idp.tenant.model.IaasIdpTenant;
import io.trishul.iaas.idp.tenant.model.IaasIdpTenantAccessor;
import io.trishul.iaas.idp.tenant.model.UpdateIaasIdpTenant;
import io.trishul.iaas.repository.IaasRepository;
import io.trishul.test.util.MockUtilProvider;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class IaasIdpTenantServiceTest {
  private IaasIdpTenantService service;

  private EntityMergerService<String, IaasIdpTenant, BaseIaasIdpTenant<?>, UpdateIaasIdpTenant<?>> mMergerService;
  private IaasRepository<String, IaasIdpTenant, BaseIaasIdpTenant<?>, UpdateIaasIdpTenant<?>> mIaasRepo;
  private LockService mLockService;

  @BeforeEach
  public void init() {
    mLockService = mock(LockService.class);
    mMergerService = spy(
        new CrudEntityMergerService<>(new MockUtilProvider(), mLockService, BaseIaasIdpTenant.class,
            UpdateIaasIdpTenant.class, IaasIdpTenant.class, Set.of("createdAt")));
    mIaasRepo = mock(IaasRepository.class);

    service = new IaasIdpTenantService(mMergerService, mIaasRepo);
  }

  @Test
  public void testExists_ReturnsTrue_WhenAllAttachmentsExists() {
    doAnswer(inv -> Map.of(inv.getArgument(0, Set.class).iterator().next(), true)).when(mIaasRepo)
        .exists(anySet());

    assertTrue(service.exists(Set.of("TENANT")));
  }

  @Test
  public void testExists_ReturnsFalse_WhenAllAttachmentsDoesNotExists() {
    doAnswer(inv -> Map.of(inv.getArgument(0, Set.class).iterator().next(), false)).when(mIaasRepo)
        .exists(anySet());

    assertFalse(service.exists(Set.of("TENANT")));
  }

  @Test
  public void testExist_ReturnsTrue_WhenAllAttachmentsExists() {
    doAnswer(inv -> Map.of(inv.getArgument(0, Set.class).iterator().next(), true)).when(mIaasRepo)
        .exists(anySet());

    assertTrue(service.exist("TENANT"));
  }

  @Test
  public void testExist_ReturnsFalse_WhenAllAttachmentsDoesNotExist() {
    doAnswer(inv -> Map.of(inv.getArgument(0, Set.class).iterator().next(), false)).when(mIaasRepo)
        .exists(anySet());

    assertFalse(service.exist("TENANT"));
  }

  @Test
  public void testDelete_Set_CallsRepoDeleteWithIds() {
    doReturn(9L).when(mIaasRepo).delete(Set.of("TENANT_1", "TENANT_2"));
    long deleteCount = service.delete(Set.of("TENANT_1", "TENANT_2"));

    assertEquals(9, deleteCount);
  }

  @Test
  public void testDelete_Id_CallsRepoDeleteWithIds() {
    doReturn(1L).when(mIaasRepo).delete(Set.of("TENANT"));

    long deleteCount = service.delete("TENANT");

    assertEquals(1, deleteCount);
  }

  @Test
  public void testGet_ReturnsAttachmentFromRepo() {
    doAnswer(
        inv -> List.of(new IaasIdpTenant((String) inv.getArgument(0, Set.class).iterator().next())))
            .when(mIaasRepo).get(anySet());

    IaasIdpTenant attachment = service.get("TENANT");

    assertEquals(new IaasIdpTenant("TENANT"), attachment);
  }

  @Test
  public void testGet_ReturnsNull_WhenRepoReturnsEmptyList() {
    doAnswer(inv -> List.of()).when(mIaasRepo).get(anySet());

    assertNull(service.get("TENANT"));
  }

  @Test
  public void testGetAll_ReturnsAttachmentFromRepo() {
    doAnswer(
        inv -> List.of(new IaasIdpTenant((String) inv.getArgument(0, Set.class).iterator().next())))
            .when(mIaasRepo).get(anySet());

    List<IaasIdpTenant> attachments = service.getAll(Set.of("TENANT"));

    List<IaasIdpTenant> expected = List.of(new IaasIdpTenant("TENANT"));
    assertEquals(expected, attachments);
  }

  @Test
  public void testGetByIds_ReturnAttachmentsFromRepo() {
    doAnswer(
        inv -> List.of(new IaasIdpTenant((String) inv.getArgument(0, Set.class).iterator().next())))
            .when(mIaasRepo).get(anySet());

    List<IaasIdpTenant> attachments = service.getByIds(Set.of(() -> "TENANT"));

    List<IaasIdpTenant> expected = List.of(new IaasIdpTenant("TENANT"));

    assertEquals(expected, attachments);
  }

  @Test
  public void testGetByAccessorIds_ReturnsAttachmentFromRepo() {
    doAnswer(
        inv -> List.of(new IaasIdpTenant((String) inv.getArgument(0, Set.class).iterator().next())))
            .when(mIaasRepo).get(anySet());

    class DummyIaasIdpTenantAccessor implements IaasIdpTenantAccessor<DummyIaasIdpTenantAccessor> {
      @Override
      public DummyIaasIdpTenantAccessor setIdpTenant(IaasIdpTenant attachment) {
        return this;
      }

      @Override
      public IaasIdpTenant getIdpTenant() {
        return new IaasIdpTenant("TENANT");
      }
    }
    IaasIdpTenantAccessor<?> accessor = new DummyIaasIdpTenantAccessor();
    List<IaasIdpTenant> attachments = service.getByAccessorIds(Set.of(accessor));

    List<IaasIdpTenant> expected = List.of(new IaasIdpTenant("TENANT"));
    assertEquals(expected, attachments);
  }

  @Test
  public void testAdd_ReturnsAddedRepoEntities_AfterSavingAddEntitiesFromMergerService() {
    doAnswer(inv -> inv.getArgument(0, List.class)).when(mIaasRepo).add(anyList());

    List<BaseIaasIdpTenant<?>> additions = List.of(
        new IaasIdpTenant().setId("TENANT_1").setIaasRole(new IaasRole("ROLE_1"))
            .setDescription("DESCRIPTION_1").setCreatedAt(LocalDateTime.of(2000, 1, 1, 0, 0))
            .setLastUpdated(LocalDateTime.of(2001, 1, 1, 0, 0)),
        new IaasIdpTenant().setId("TENANT_2").setIaasRole(new IaasRole("ROLE_2"))
            .setDescription("DESCRIPTION_2").setCreatedAt(LocalDateTime.of(2000, 2, 1, 0, 0))
            .setLastUpdated(LocalDateTime.of(2001, 2, 1, 0, 0)));

    List<IaasIdpTenant> attachments = service.add(additions);

    List<IaasIdpTenant> expected = List.of(
        new IaasIdpTenant().setId("TENANT_1").setIaasRole(new IaasRole("ROLE_1"))
            .setDescription("DESCRIPTION_1"),
        new IaasIdpTenant().setId("TENANT_2").setIaasRole(new IaasRole("ROLE_2"))
            .setDescription("DESCRIPTION_2"));

    assertEquals(expected, attachments);
    verify(mIaasRepo, times(1)).add(attachments);
    verify(mMergerService).getAddEntities(additions);
  }

  @Test
  public void testAdd_ReturnsNull_WhenArgIsNull() {
    assertNull(service.add(null));
  }

  @Test
  public void testPut_ReturnsPutRepoEntities_AfterSavingPutEntitiesFromMergerService() {
    doAnswer(inv -> inv.getArgument(0, List.class)).when(mIaasRepo).put(anyList());

    List<UpdateIaasIdpTenant<?>> updates = List.of(
        new IaasIdpTenant().setId("TENANT_1").setIaasRole(new IaasRole("ROLE_1"))
            .setDescription("DESCRIPTION_1").setCreatedAt(LocalDateTime.of(2000, 1, 1, 0, 0))
            .setLastUpdated(LocalDateTime.of(2001, 1, 1, 0, 0)),
        new IaasIdpTenant().setId("TENANT_2").setIaasRole(new IaasRole("ROLE_2"))
            .setDescription("DESCRIPTION_2").setCreatedAt(LocalDateTime.of(2000, 2, 1, 0, 0))
            .setLastUpdated(LocalDateTime.of(2001, 2, 1, 0, 0)));

    List<IaasIdpTenant> attachments = service.put(updates);

    List<IaasIdpTenant> expected = List.of(
        new IaasIdpTenant().setId("TENANT_1").setIaasRole(new IaasRole("ROLE_1"))
            .setDescription("DESCRIPTION_1"),
        new IaasIdpTenant().setId("TENANT_2").setIaasRole(new IaasRole("ROLE_2"))
            .setDescription("DESCRIPTION_2"));

    assertEquals(expected, attachments);
    verify(mIaasRepo, times(1)).put(attachments);
    verify(mMergerService).getPutEntities(null, updates);
  }

  @Test
  public void testPut_ReturnsNull_WhenArgIsNull() {
    assertNull(service.put(null));
  }

  @Test
  public void testPatch_ReturnsPatchRepoEntities_AfterSavingPatchEntitiesFromMergerService() {
    doAnswer(inv -> inv.getArgument(0, List.class)).when(mIaasRepo).put(anyList());

    doAnswer(inv -> {
      Iterator<String> it = inv.getArgument(0, Set.class).iterator();
      String id2 = it.next();
      String id1 = it.next();

      return List.of(
          new IaasIdpTenant().setId(id1).setIaasRole(new IaasRole("ROLE_1"))
              .setDescription("DESCRIPTION_1").setCreatedAt(LocalDateTime.of(2000, 1, 1, 0, 0))
              .setLastUpdated(LocalDateTime.of(2001, 1, 1, 0, 0)),
          new IaasIdpTenant().setId(id2).setIaasRole(new IaasRole("ROLE_2"))
              .setDescription("DESCRIPTION_2").setCreatedAt(LocalDateTime.of(2000, 2, 1, 0, 0))
              .setLastUpdated(LocalDateTime.of(2001, 2, 1, 0, 0)));
    }).when(mIaasRepo).get(anySet());

    List<UpdateIaasIdpTenant<?>> updates = List.of(
        new IaasIdpTenant().setId("TENANT_1").setCreatedAt(LocalDateTime.of(2000, 1, 1, 0, 0))
            .setLastUpdated(LocalDateTime.of(2001, 1, 1, 0, 0)),
        new IaasIdpTenant().setId("TENANT_2").setIaasRole(new IaasRole().setId("ROLE_2_UPDATED"))
            .setDescription("DESCRIPTION_2_UPDATED")
            .setCreatedAt(LocalDateTime.of(2000, 2, 1, 0, 0))
            .setLastUpdated(LocalDateTime.of(2001, 2, 1, 0, 0)));

    List<IaasIdpTenant> attachments = service.patch(updates);

    List<IaasIdpTenant> expected = List.of(
        new IaasIdpTenant().setId("TENANT_1").setIaasRole(new IaasRole("ROLE_1"))
            .setDescription("DESCRIPTION_1").setLastUpdated(LocalDateTime.of(2001, 1, 1, 0, 0)),
        new IaasIdpTenant().setId("TENANT_2").setIaasRole(new IaasRole().setId("ROLE_2_UPDATED"))
            .setDescription("DESCRIPTION_2_UPDATED")
            .setLastUpdated(LocalDateTime.of(2001, 2, 1, 0, 0)));

    assertEquals(expected, attachments);
    verify(mIaasRepo, times(1)).put(attachments);
    verify(mMergerService).getPatchEntities(anyList(), eq(updates));
  }

  @Test
  public void testPatch_ReturnsNull_WhenArgIsNull() {
    assertNull(service.patch(null));
  }
}
