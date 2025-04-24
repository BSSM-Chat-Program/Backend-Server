package bssmchat.global.properties;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@Getter
@ConfigurationProperties(prefix = "oauth2.google")
public class GoogleOAuth2Properties implements OAuth2Properties{
    private final String serviceName;
    private final String baseUrl;
    private final String clientId;
    private final String clientSecret;
    private final String redirectUri;
    private final String responseType;
    private final List<String> scopes;
    private final String grantType;

    public GoogleOAuth2Properties(String serviceName, String baseUrl, String clientId, String clientSecret,
                            String redirectUri, String responseType,
                            List<String> scopes, String grantType) {
        this.serviceName = serviceName;
        this.baseUrl = baseUrl;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.redirectUri = redirectUri;
        this.responseType = responseType;
        this.scopes = scopes;
        this.grantType = grantType;
    }
}
