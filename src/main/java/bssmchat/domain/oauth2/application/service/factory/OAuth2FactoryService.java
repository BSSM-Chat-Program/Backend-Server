package bssmchat.domain.oauth2.application.service.factory;

import bssmchat.domain.oauth2.application.exception.UnsupportedOrganizationException;
import bssmchat.domain.oauth2.application.service.OAuth2QueryStringBuilder;
import bssmchat.domain.oauth2.infra.client.OAuth2TokenClient;
import bssmchat.domain.oauth2.infra.client.OAuth2UserClient;
import bssmchat.global.properties.OAuth2Properties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OAuth2FactoryService {

    private final Map<String, OAuth2Properties> oAuth2Properties;
    private final Map<String, OAuth2TokenClient> oAuth2TokenClients;
    private final Map<String, OAuth2UserClient> oAuth2UserClients;
    private final Map<String, OAuth2QueryStringBuilder> oAuth2QueryStringBuilders;

    public String getOAuth2QueryString(String organization) {
        OAuth2QueryStringBuilder builder = getOrThrow(
                oAuth2QueryStringBuilders, organization
        );
        OAuth2Properties properties = getProperties(organization);
        return builder.buildQueryString(properties);
    }

    public OAuth2Properties getProperties(String organization) {
        return getBeanNameOrThrow(
                oAuth2Properties, organization
        );
    }

    public OAuth2TokenClient getTokenClient(String organization) {
        return getClientByBeanName(
                oAuth2TokenClients, organization, "OAuth2TokenClient"
        );
    }

    public OAuth2UserClient getUserClient(String organization) {
        return getClientByBeanName(
                oAuth2UserClients, organization, "OAuth2UserClient"
        );
    }

    private String upperFirstLetter(String sentence) {
        return sentence.substring(0, 1).toUpperCase() + sentence.substring(1);
    }

    private <T> T getOrThrow(Map<String, T> map, String organization) {
        String key = organization + "OAuth2QueryStringBuilder";
        return Optional.ofNullable(map.get(key))
                .orElseThrow(() -> new UnsupportedOrganizationException(organization));
    }

    private <T> T getClientByBeanName(Map<String, T> map, String organization, String resourceName) {
        String key = "bssmchat.domain.oauth2.infra.client." + organization + "." + upperFirstLetter(organization) + resourceName;
        return Optional.ofNullable(map.get(key))
                .orElseThrow(() -> new UnsupportedOrganizationException(organization));
    }

    private <T> T getBeanNameOrThrow(Map<String, T> map, String organization) {
        String key = "oauth2." + organization + "-bssmchat.global.properties." + upperFirstLetter(organization) + "OAuth2Properties";
        return Optional.ofNullable(map.get(key))
                .orElseThrow(() -> new UnsupportedOrganizationException(organization));
    }
}
