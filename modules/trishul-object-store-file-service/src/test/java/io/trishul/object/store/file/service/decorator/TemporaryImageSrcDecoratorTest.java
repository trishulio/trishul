package io.company.brewcraft.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.Mockito.*;

import java.net.URI;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.controller.IaasObjectStoreFileController;
import io.company.brewcraft.dto.IaasObjectStoreFileDto;
import io.company.brewcraft.dto.ProductDto;
import io.company.brewcraft.model.BaseModel;
import io.company.brewcraft.model.DecoratedIaasObjectStoreFileAccessor;

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
        doAnswer(inv -> inv.getArgument(0, Set.class).stream().map(uri -> new IaasObjectStoreFileDto((URI) uri)).toList()).when(mController).getAll(anySet());

        List<DecoratedEntity> entities = List.of(new DecoratedEntity(URI.create("http://localhost/2")), new DecoratedEntity(URI.create("http://localhost/1")));

        decorator.decorate(entities);

        List<DecoratedEntity> expected = List.of(
            new DecoratedEntity(URI.create("http://localhost/2"), new IaasObjectStoreFileDto(URI.create("http://localhost/2"))),
            new DecoratedEntity(URI.create("http://localhost/1"), new IaasObjectStoreFileDto(URI.create("http://localhost/1")))
        );

        assertEquals(expected, entities);
    }

    @Test
    public void testDecorate_DoesNothing_WhenExceptionIsThrown() {
        doThrow(RuntimeException.class).when(mController).getAll(any());

        List<ProductDto> dtos = List.of(new ProductDto(1L), new ProductDto(2L));

        decorator.decorate(dtos);

        List<ProductDto> expected = List.of(new ProductDto(1L), new ProductDto(2L));
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
