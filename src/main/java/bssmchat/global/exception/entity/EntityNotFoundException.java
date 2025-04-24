package bssmchat.global.exception.entity;

import bssmchat.global.exception.ChatBusinessException;
import org.springframework.http.HttpStatus;

public class EntityNotFoundException extends ChatBusinessException {
    public EntityNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
