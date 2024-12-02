package io.trishul.object.store.file.service.model.decorator;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.trishul.model.base.dto.BaseDto;
import io.trishul.object.store.file.decorator.EntityDecorator;

public class DtoDecorator implements EntityDecorator<BaseDto> {
    private static final Logger logger = LoggerFactory.getLogger(DtoDecorator.class);

    private final TemporaryImageSrcDecorator imageSrcDecorator;

    public DtoDecorator(TemporaryImageSrcDecorator imageSrcDecorator) {
        this.imageSrcDecorator = imageSrcDecorator;
    }

    @Override
    public void decorate(List<R> entities) {
        this.imageSrcDecorator.decorate(entities);
    }

}
