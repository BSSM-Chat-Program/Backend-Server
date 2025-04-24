package bssmchat.domain.oauth2.presentation.controller;

import bssmchat.domain.oauth2.application.service.OAuth2LinkService;
import bssmchat.domain.oauth2.application.service.OAuth2UserService;
import bssmchat.domain.oauth2.presentation.dto.res.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/oauth2")
public class GoogleOAuth2Controller {
    private final OAuth2LinkService oAuth2TokenService;
    private final OAuth2UserService oAuth2UserService;

    @GetMapping("/link/{organization}")
    public ResponseEntity<Void> getLink(@PathVariable String organization) {
        URI redrectUri = URI.create(oAuth2TokenService.getOAuth2AuthorizationUrl(organization));
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(redrectUri);

        return new ResponseEntity<>(httpHeaders, HttpStatus.TEMPORARY_REDIRECT);
    }

    @PostMapping("/login/{organization}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> login(@PathVariable String organization, @RequestBody String code) {
        TokenResponse tokenResponse = oAuth2UserService.loginUserByCode(code, organization);
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, tokenResponse.refreshTokenCookie())
                .body(tokenResponse.accessToken());
    }
}
