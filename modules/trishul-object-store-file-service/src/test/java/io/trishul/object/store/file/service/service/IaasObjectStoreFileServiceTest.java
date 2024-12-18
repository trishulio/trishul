package io.trishul.object.store.file.service.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.Mockito.*;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.trishul.crud.service.UpdateService;
import io.trishul.object.store.file.model.IaasObjectStoreFile;
import io.trishul.object.store.file.model.BaseIaasObjectStoreFile;
import io.trishul.object.store.file.model.UpdateIaasObjectStoreFile;
import io.trishul.iaas.repository.IaasRepository;
import io.trishul.test.util.MockUtilProvider;
import io.trishul.crud.service.SimpleUpdateService;
import io.trishul.object.store.file.model.accessor.IaasObjectStoreFileAccessor;
import io.trishul.crud.service.LockService;

public class IaasObjectStoreFileServiceTest {
    private IaasObjectStoreFileService service;

    private UpdateService<URI, IaasObjectStoreFile, BaseIaasObjectStoreFile, UpdateIaasObjectStoreFile> mUpdateService;
    private IaasRepository<URI, IaasObjectStoreFile, BaseIaasObjectStoreFile, UpdateIaasObjectStoreFile> mIaasRepo;
    private LockService mLockService;

    @BeforeEach
    public void init() {
        mLockService = mock(LockService.class);
        mUpdateService = spy(new SimpleUpdateService<>(new MockUtilProvider(), mLockService, BaseIaasObjectStoreFile.class, UpdateIaasObjectStoreFile.class, IaasObjectStoreFile.class, Set.of("createdAt")));
        mIaasRepo = mock(IaasRepository.class);

        service = new IaasObjectStoreFileService(mUpdateService, mIaasRepo);
    }

    @Test
    public void testExists_ReturnsTrue_WhenAllAttachmentsExists() {
        doAnswer(inv -> Map.of(inv.getArgument(0, Set.class).iterator().next(), true)).when(mIaasRepo).exists(anySet());

        assertTrue(service.exists(Set.of(URI.create("URI"))));
    }

    @Test
    public void testExists_ReturnsFalse_WhenAllAttachmentsDoesNotExists() {
        doAnswer(inv -> Map.of(inv.getArgument(0, Set.class).iterator().next(), false)).when(mIaasRepo).exists(anySet());

        assertFalse(service.exists(Set.of(URI.create("URI"))));
    }

    @Test
    public void testExist_ReturnsTrue_WhenAllAttachmentsExists() {
        doAnswer(inv -> Map.of(inv.getArgument(0, Set.class).iterator().next(), true)).when(mIaasRepo).exists(anySet());

        assertTrue(service.exist(URI.create("URI")));
    }

    @Test
    public void testExist_ReturnsFalse_WhenAllAttachmentsDoesNotExist() {
        doAnswer(inv -> Map.of(inv.getArgument(0, Set.class).iterator().next(), false)).when(mIaasRepo).exists(anySet());

        assertFalse(service.exist(URI.create("URI")));
    }

    @Test
    public void testDelete_Set_CallsRepoDeleteWithIds() {
        doReturn(9L).when(mIaasRepo).delete(Set.of(URI.create("URI_1"), URI.create("URI_2")));
        long deleteCount = service.delete(Set.of(URI.create("URI_1"), URI.create("URI_2")));

        assertEquals(9, deleteCount);
    }

    @Test
    public void testDelete_Id_CallsRepoDeleteWithIds() {
        doReturn(1L).when(mIaasRepo).delete(Set.of(URI.create("URI")));

        long deleteCount = service.delete(URI.create("URI"));

        assertEquals(1, deleteCount);
    }

    @Test
    public void testGet_ReturnsAttachmentFromRepo() {
        doAnswer(inv -> List.of(new IaasObjectStoreFile((URI) inv.getArgument(0, Set.class).iterator().next()))).when(mIaasRepo).get(anySet());

        IaasObjectStoreFile attachment = service.get(URI.create("URI"));

        assertEquals(new IaasObjectStoreFile(URI.create("URI")), attachment);
    }

    @Test
    public void testGet_ReturnsNull_WhenRepoReturnsEmptyList() {
        doAnswer(inv -> List.of()).when(mIaasRepo).get(anySet());

        assertNull(service.get(URI.create("URI")));
    }

    @Test
    public void testGetAll_ReturnsAttachmentFromRepo() {
        doAnswer(inv -> List.of(new IaasObjectStoreFile((URI) inv.getArgument(0, Set.class).iterator().next()))).when(mIaasRepo).get(anySet());

        List<IaasObjectStoreFile> attachments = service.getAll(Set.of(URI.create("URI")));

        List<IaasObjectStoreFile> expected = List.of(new IaasObjectStoreFile(URI.create("URI")));
        assertEquals(expected, attachments);
    }

    @Test
    public void testGetByIds_ReturnAttachmentsFromRepo() {
        doAnswer(inv -> List.of(new IaasObjectStoreFile((URI) inv.getArgument(0, Set.class).iterator().next()))).when(mIaasRepo).get(anySet());

        List<IaasObjectStoreFile> attachments = service.getByIds(Set.of(() -> URI.create("URI")));

        List<IaasObjectStoreFile> expected = List.of(new IaasObjectStoreFile(URI.create("URI")));

        assertEquals(expected, attachments);
    }

    @Test
    public void testGetByAccessorIds_ReturnsAttachmentFromRepo() {
        doAnswer(inv -> List.of(new IaasObjectStoreFile((URI) inv.getArgument(0, Set.class).iterator().next()))).when(mIaasRepo).get(anySet());

        IaasObjectStoreFileAccessor accessor = new IaasObjectStoreFileAccessor() {
            @Override
            public void setObjectStoreFile(IaasObjectStoreFile attachment) {
            }
            @Override
            public IaasObjectStoreFile getObjectStoreFile() {
                return new IaasObjectStoreFile(URI.create("URI"));
            }
        };
        List<IaasObjectStoreFile> attachments = service.getByAccessorIds(Set.of(accessor));

        List<IaasObjectStoreFile> expected = List.of(new IaasObjectStoreFile(URI.create("URI")));
        assertEquals(expected, attachments);
    }

    @Test
    public void testAdd_ReturnsAddedRepoEntities_AfterSavingAddEntitiesFromUpdateService() throws MalformedURLException {
        doAnswer(inv -> inv.getArgument(0, List.class)).when(mIaasRepo).add(anyList());

        List<BaseIaasObjectStoreFile> additions = List.of(
            new IaasObjectStoreFile(URI.create("URI_1"), LocalDateTime.of(2000, 1, 1, 0, 0), new URL("http://localhost/1")),
            new IaasObjectStoreFile(URI.create("URI_2"), LocalDateTime.of(2000, 2, 1, 0, 0), new URL("http://localhost/2"))
        );

        List<IaasObjectStoreFile> attachments = service.add(additions);

        List<IaasObjectStoreFile> expected = List.of(
            new IaasObjectStoreFile(URI.create("URI_1"), LocalDateTime.of(2000, 1, 1, 0, 0), new URL("http://localhost/1")),
            new IaasObjectStoreFile(URI.create("URI_2"), LocalDateTime.of(2000, 2, 1, 0, 0), new URL("http://localhost/2"))
        );

        assertEquals(expected, attachments);
        verify(mIaasRepo, times(1)).add(attachments);
        verify(mUpdateService).getAddEntities(additions);
    }

    @Test
    public void testAdd_DoesNothingReturnsNull_WhenArgIsNull() {
        assertNull(service.add(null));
    }

    @Test
    public void testPut_ReturnsPutRepoEntities_AfterSavingPutEntitiesFromUpdateService() throws MalformedURLException {
        doAnswer(inv -> inv.getArgument(0, List.class)).when(mIaasRepo).put(anyList());

        List<UpdateIaasObjectStoreFile> updates = List.of(
            new IaasObjectStoreFile(URI.create("URI_1"), LocalDateTime.of(2000, 1, 1, 0, 0), new URL("http://localhost/1")),
            new IaasObjectStoreFile(URI.create("URI_2"), LocalDateTime.of(2000, 2, 1, 0, 0), new URL("http://localhost/2"))
        );

        List<IaasObjectStoreFile> attachments = service.put(updates);

        List<IaasObjectStoreFile> expected = List.of(
            new IaasObjectStoreFile(URI.create("URI_1"), LocalDateTime.of(2000, 1, 1, 0, 0), new URL("http://localhost/1")),
            new IaasObjectStoreFile(URI.create("URI_2"), LocalDateTime.of(2000, 2, 1, 0, 0), new URL("http://localhost/2"))
        );

        assertEquals(expected, attachments);
        verify(mIaasRepo, times(1)).put(attachments);
        verify(mUpdateService).getPutEntities(null, updates);
    }

    @Test
    public void testPut_DoesNothingReturnsNull_WhenArgIsNull() {
        assertNull(service.put(null));
    }

    @Test
    public void testPatch_ThrowsNotSupportedException() {
        assertThrows(UnsupportedOperationException.class, () -> service.patch(List.of()));
    }

    @Test
    public void testPatch_DoesNothingReturnsNull_WhenArgIsNull() {
        assertNull(service.patch(null));
    }
}
