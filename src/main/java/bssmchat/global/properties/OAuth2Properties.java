package bssmchat.global.properties;

import java.util.List;

public interface OAuth2Properties {
    String getServiceName();
    String getBaseUrl();
    String getClientId();
    String getClientSecret();
    String getRedirectUri();
    String getResponseType();
    List<String> getScopes();
    String getGrantType();
}
