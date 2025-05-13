package bssmchat.domain.oauth2.infra.client;

import bssmchat.domain.oauth2.presentation.dto.res.OAuth2UserResponse;

public interface OAuth2UserClient {
    OAuth2UserResponse getUserInfo(String accessToken);
}
