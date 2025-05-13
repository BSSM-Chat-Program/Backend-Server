package bssmchat.domain.oauth2.infra.client.google;

import bssmchat.domain.oauth2.infra.client.OAuth2UserClient;
import bssmchat.domain.oauth2.presentation.dto.res.google.GoogleOAuth2UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(
        name = "GoogleOAuth2UserInfo",
        url = "https://www.googleapis.com"
)
public interface GoogleOAuth2UserClient extends OAuth2UserClient {
    @GetMapping(value = "/oauth2/v3/userinfo", consumes = MediaType.APPLICATION_JSON_VALUE)
    GoogleOAuth2UserResponse getUserInfo(@RequestHeader("Authorization") String accessToken);
}