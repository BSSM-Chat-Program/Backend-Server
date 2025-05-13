package bssmchat.global.feign;

import bssmchat.global.feign.exception.FeignClientInvalidRequestException;
import bssmchat.global.feign.exception.FeignClientResponseException;
import bssmchat.global.feign.exception.FeignClientUnauthorizedException;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

@Slf4j
public class FeignClientErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String s, Response response) {
        if (response.status() == 401) {
            throw new FeignClientUnauthorizedException("Unauthorized access", HttpStatus.UNAUTHORIZED);
        }
        else if(response.status() >= 400 && response.status() < 500) {
            throw new FeignClientInvalidRequestException(
                    response.reason(),
                    HttpStatus.BAD_REQUEST
            );
        }
        else {
            throw new FeignClientResponseException(
                    "FeignClient Request Error Status : " + response.status(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }
}
