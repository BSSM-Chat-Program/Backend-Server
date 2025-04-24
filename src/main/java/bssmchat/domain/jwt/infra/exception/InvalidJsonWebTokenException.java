package bssmchat.domain.jwt.infra.exception;

import bssmchat.global.exception.ChatBusinessException;
import org.springframework.http.HttpStatus;

public class InvalidJsonWebTokenException extends ChatBusinessException {
    public InvalidJsonWebTokenException() {
        super("This JsonWebToken is not verified.", HttpStatus.UNAUTHORIZED);
    }
}
