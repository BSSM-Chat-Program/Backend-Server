package bssmchat.domain.oauth2.application.service;

import bssmchat.domain.oauth2.application.service.factory.OAuth2FactoryService;
import bssmchat.domain.oauth2.domain.service.UserSaveService;
import bssmchat.domain.oauth2.infra.client.OAuth2TokenClient;
import bssmchat.domain.oauth2.infra.client.OAuth2UserClient;
import bssmchat.domain.oauth2.presentation.dto.res.OAuth2TokenResponse;
import bssmchat.domain.oauth2.presentation.dto.res.OAuth2UserResponse;
import bssmchat.domain.oauth2.presentation.dto.res.TokenResponse;
import bssmchat.global.properties.OAuth2Properties;
import bssmchat.global.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OAuth2UserService {
    private final OAuth2FactoryService oAuth2FactoryService;
    private final UserSaveService userSaveService;
    private final JwtUtil jwtUtil;

    public TokenResponse loginUserByCode(String code, String organization) {
        OAuth2UserClient oAuth2UserClient = oAuth2FactoryService.getUserClient(organization);
        OAuth2TokenClient oAuth2TokenClient = oAuth2FactoryService.getTokenClient(organization);
        OAuth2Properties oAuth2Properties = oAuth2FactoryService.getProperties(organization);
        OAuth2TokenResponse oAuth2TokenResponse = oAuth2TokenClient.getToken(
                code,
                oAuth2Properties.getClientId(),
                oAuth2Properties.getClientSecret(),
                oAuth2Properties.getRedirectUri(),
                oAuth2Properties.getGrantType()
        );

        String oAuth2AccessToken = oAuth2TokenResponse.getAccessToken();

        OAuth2UserResponse oAuth2UserResponse = oAuth2UserClient.getUserInfo(oAuth2AccessToken);
        String oAuth2Id = oAuth2Properties.getServiceName() + " " + oAuth2UserResponse.getId();

        userSaveService.saveUser(oAuth2Id, oAuth2UserResponse);

        String accessToken = jwtUtil.createAccessToken(oAuth2UserResponse.getName());
        String refreshToken = jwtUtil.createRefreshToken(oAuth2UserResponse.getName());

        return new TokenResponse(
                accessToken,
                jwtUtil.createRefreshTokenCookie(refreshToken, oAuth2Id).toString()
        );
    }
}
