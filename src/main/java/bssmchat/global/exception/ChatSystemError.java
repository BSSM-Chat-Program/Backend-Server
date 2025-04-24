package bssmchat.global.exception;

import org.springframework.http.HttpStatus;

public class ChatSystemError extends ChatException {
    public ChatSystemError(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
