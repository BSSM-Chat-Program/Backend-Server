package bssmchat.domain.oauth2.application.service;

import bssmchat.domain.oauth2.application.service.factory.OAuth2FactoryService;
import bssmchat.global.properties.OAuth2Properties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OAuth2LinkService {
    private final OAuth2FactoryService oAuth2FactoryService;

    public String getOAuth2AuthorizationUrl(String organization) {
        OAuth2Properties oAuth2Properties = oAuth2FactoryService.getProperties(organization);
        return String.format("%s?%s", oAuth2Properties.getBaseUrl(), oAuth2FactoryService.getOAuth2QueryString(organization));
    }
}
