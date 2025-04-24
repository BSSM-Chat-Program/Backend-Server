package bssmchat.domain.oauth2.application.exception;

import bssmchat.global.exception.ChatBusinessException;
import org.springframework.http.HttpStatus;

public class UnsupportedOrganizationException extends ChatBusinessException {
    public UnsupportedOrganizationException(String organization) {
        super(organization + " 기관의 OAuth2 인증은 지원하지 않습니다.", HttpStatus.BAD_REQUEST);
    }
}
