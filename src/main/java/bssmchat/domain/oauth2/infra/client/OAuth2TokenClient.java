package bssmchat.domain.oauth2.infra.client;

import bssmchat.domain.oauth2.presentation.dto.res.OAuth2TokenResponse;

public interface OAuth2TokenClient {

    OAuth2TokenResponse getToken(
            String code,
            String clientId,
            String clientSecret,
            String redirectUri,
            String grantType
    );
}
