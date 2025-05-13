package bssmchat.global.feign.exception;

import bssmchat.global.exception.ChatBusinessException;
import org.springframework.http.HttpStatus;

public class FeignClientInvalidRequestException extends ChatBusinessException {
    public FeignClientInvalidRequestException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
