package bssmchat.domain.oauth2.infra.client.google;

import bssmchat.domain.oauth2.infra.client.OAuth2TokenClient;
import bssmchat.domain.oauth2.presentation.dto.res.google.GoogleOAuth2TokenResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name="GoogleOAuth2TokenInfo",
        url="https://oauth2.googleapis.com"
)
public interface GoogleOAuth2TokenClient extends OAuth2TokenClient {

    @PostMapping(value = "/token")
    GoogleOAuth2TokenResponse getToken(
            @RequestParam("code") String code,
            @RequestParam("client_id") String clientId,
            @RequestParam("client_secret") String clientSecret,
            @RequestParam("redirect_uri") String redirectUri,
            @RequestParam("grant_type") String grantType
    );
}
