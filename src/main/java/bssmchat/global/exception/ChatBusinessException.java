package bssmchat.global.exception;

import org.springframework.http.HttpStatus;

public class ChatBusinessException extends ChatException {
    public ChatBusinessException(String message, HttpStatus status) {
        super(message, status);
    }
}
