package bssmchat.global.feign.exception;

import bssmchat.global.exception.ChatSystemError;
import org.springframework.http.HttpStatus;

public class FeignClientRequestException extends ChatSystemError {
  public FeignClientRequestException(String message, HttpStatus httpStatus) {
    super(message, httpStatus);
  }
}
