package bssmchat.global.feign;

import bssmchat.global.feign.exception.FeignClientRequestException;
import bssmchat.global.feign.exception.FeignClientUnauthorizedException;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatus;

public class FeignClientErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String s, Response response) {
        if (response.status() == 401) {
            return new FeignClientUnauthorizedException("Unauthorized access", HttpStatus.UNAUTHORIZED);
        }
        else {
            return new FeignClientRequestException(
                    "FeignClient Request Error Status : " + response.status(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }
}
