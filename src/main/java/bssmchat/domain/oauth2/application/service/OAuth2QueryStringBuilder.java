package bssmchat.domain.oauth2.application.service;

import bssmchat.global.properties.OAuth2Properties;

public interface OAuth2QueryStringBuilder {
    String buildQueryString(OAuth2Properties oAuth2Properties);
}
