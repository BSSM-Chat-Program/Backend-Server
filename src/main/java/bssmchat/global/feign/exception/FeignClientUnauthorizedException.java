package bssmchat.global.feign.exception;

import bssmchat.global.exception.ChatBusinessException;
import org.springframework.http.HttpStatus;

public class FeignClientUnauthorizedException extends ChatBusinessException {
    public FeignClientUnauthorizedException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
