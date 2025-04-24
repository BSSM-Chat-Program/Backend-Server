package bssmchat.domain.oauth2.application.service.impl;


import bssmchat.domain.oauth2.application.service.OAuth2QueryStringBuilder;
import bssmchat.global.properties.OAuth2Properties;
import org.springframework.stereotype.Component;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Component
public class GoogleOAuth2QueryStringBuilder implements OAuth2QueryStringBuilder {

    @Override
    public String buildQueryString(OAuth2Properties oAuth2Properties) {
        return String.join("&",
                "client_id=" + encodeValue(oAuth2Properties.getClientId()),
                "redirect_uri=" + encodeValue(oAuth2Properties.getRedirectUri()),
                "response_type=" + oAuth2Properties.getResponseType(),
                "scope=" + encodeValue(String.join(" ", oAuth2Properties.getScopes()))
        );
    }

    private String encodeValue(String value) {
        return URLEncoder.encode(value, StandardCharsets.UTF_8);
    }
}
