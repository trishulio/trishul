package io.trishul.object.store.file.service.decorator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

import io.trishul.model.base.dto.BaseDto;
import io.trishul.model.base.pojo.BaseModel;
import io.trishul.object.store.file.model.accessor.DecoratedIaasObjectStoreFileAccessor;
import io.trishul.object.store.file.model.dto.IaasObjectStoreFileDto;
import io.trishul.object.store.file.service.controller.IaasObjectStoreFileController;
import java.net.URI;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TemporaryImageSrcDecoratorTest {
    private TemporaryImageSrcDecorator decorator;

    private IaasObjectStoreFileController mController;

    @BeforeEach
    public void init() {
        mController = mock(IaasObjectStoreFileController.class);

        decorator = new TemporaryImageSrcDecorator(mController);
    }

    @Test
    public void testDecorate_OverridesFileOnEntities_WhenUriIsNotNull() {
        doAnswer(
                        inv ->
                                inv.getArgument(0, Set.class).stream()
                                        .map(uri -> new IaasObjectStoreFileDto((URI) uri))
                                        .toList())
                .when(mController)
                .getAll(anySet());

        List<DecoratedEntity> entities =
                List.of(
                        new DecoratedEntity(URI.create("http://localhost/2")),
                        new DecoratedEntity(URI.create("http://localhost/1")));

        decorator.decorate(entities);

        List<DecoratedEntity> expected =
                List.of(
                        new DecoratedEntity(
                                URI.create("http://localhost/2"),
                                new IaasObjectStoreFileDto(URI.create("http://localhost/2"))),
                        new DecoratedEntity(
                                URI.create("http://localhost/1"),
                                new IaasObjectStoreFileDto(URI.create("http://localhost/1"))));

        assertEquals(expected, entities);
    }

    @Test
    public void testDecorate_DoesNothing_WhenExceptionIsThrown() {
        doThrow(RuntimeException.class).when(mController).getAll(any());

        List<DummyDto> dtos = List.of(new DummyDto(1L), new DummyDto(2L));

        decorator.decorate(dtos);

        List<DummyDto> expected = List.of(new DummyDto(1L), new DummyDto(2L));
        assertEquals(expected, dtos);
    }
}

class DecoratedEntity extends BaseModel implements DecoratedIaasObjectStoreFileAccessor {
    private URI imageSrc;
    private IaasObjectStoreFileDto objectStoreFile;

    public DecoratedEntity(URI imageSrc) {
        this.imageSrc = imageSrc;
    }

    public DecoratedEntity(URI imageSrc, IaasObjectStoreFileDto objectStoreFile) {
        this.imageSrc = imageSrc;
        this.objectStoreFile = objectStoreFile;
    }

    @Override
    public URI getImageSrc() {
        return imageSrc;
    }

    @Override
    public void setObjectStoreFile(IaasObjectStoreFileDto objectStoreFile) {
        this.objectStoreFile = objectStoreFile;
    }
}

class DummyDto extends BaseDto implements DecoratedIaasObjectStoreFileAccessor {
    private Long id;
    private URI imageSrc;
    private IaasObjectStoreFileDto objectStoreFile;

    public DummyDto(Long id, URI imageSrc) {
        this(id);
        this.imageSrc = imageSrc;
    }

    public DummyDto(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    public URI getImageSrc() {
        return this.imageSrc;
    }

    public void setObjectStoreFile(IaasObjectStoreFileDto objectStoreFile) {
        this.objectStoreFile = objectStoreFile;
    }
}
;
