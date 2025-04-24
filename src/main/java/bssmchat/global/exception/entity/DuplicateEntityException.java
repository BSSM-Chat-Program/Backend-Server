package bssmchat.global.exception.entity;

import bssmchat.global.exception.ChatBusinessException;
import org.springframework.http.HttpStatus;

public class DuplicateEntityException extends ChatBusinessException {
    public DuplicateEntityException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
