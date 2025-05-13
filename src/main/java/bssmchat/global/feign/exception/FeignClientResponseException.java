package bssmchat.global.feign.exception;

import bssmchat.global.exception.ChatSystemError;
import org.springframework.http.HttpStatus;

public class FeignClientResponseException extends ChatSystemError {
  public FeignClientResponseException(String message, HttpStatus httpStatus) {
    super(message, httpStatus);
  }
}
