package bssmchat.global.exception.handler;

import bssmchat.global.exception.ChatBusinessException;
import bssmchat.global.exception.ChatSystemError;
import bssmchat.global.exception.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleAuthenticationException(AuthenticationException authenticationException) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(HttpStatus.UNAUTHORIZED.getReasonPhrase())
                .message(authenticationException.getMessage())
                .build();
            return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException accessDeniedException) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(HttpStatus.FORBIDDEN.getReasonPhrase())
                .message(accessDeniedException.getMessage())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message(exception.getMessage())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ChatBusinessException.class)
    public ResponseEntity<ErrorResponse> handleChatBusinessException(ChatBusinessException chatException) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(chatException.getHttpStatus().getReasonPhrase())
                .message(chatException.getMessage())
                .build();
        return new ResponseEntity<>(errorResponse, chatException.getHttpStatus());
    }

    @ExceptionHandler(ChatSystemError.class)
    public ResponseEntity<ErrorResponse> handleChatSystemError(ChatSystemError chatSystemError) {
        loggingError(chatSystemError);
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(chatSystemError.getHttpStatus().getReasonPhrase())
                .message(chatSystemError.getMessage())
                .build();
        return new ResponseEntity<>(errorResponse, chatSystemError.getHttpStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception exception) {
        loggingError(exception);
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .message(exception.getMessage())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private void loggingError(Exception exception) {
        log.error("Exception occurred: [{}] - Message: [{}]", exception.getClass().getName(), exception.getMessage());
    }
}
