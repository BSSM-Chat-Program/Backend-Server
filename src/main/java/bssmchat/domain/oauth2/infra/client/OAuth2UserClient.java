package bssmchat.domain.oauth2.infra.client;

import bssmchat.domain.oauth2.presentation.dto.res.google.GoogleOAuth2UserResponse;

public interface OAuth2UserClient {
    GoogleOAuth2UserResponse getUserInfo(String accessToken);
}
