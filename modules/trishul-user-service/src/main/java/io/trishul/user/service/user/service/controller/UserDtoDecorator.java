package io.trishul.user.service.user.service.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserDtoDecorator implements EntityDecorator<UserDto> {
    private static final Logger logger = LoggerFactory.getLogger(UserDtoDecorator.class);

    private TemporaryImageSrcDecorator imageSrcDecorator;

    public UserDtoDecorator(TemporaryImageSrcDecorator imageSrcDecorator) {
        this.imageSrcDecorator = imageSrcDecorator;
    }

    @Override
    public <R extends UserDto> void decorate(List<R> entities) {
        this.imageSrcDecorator.decorate(entities);
    }
}
