package bssmchat.domain.oauth2.presentation.dto.res.google;

import bssmchat.domain.oauth2.presentation.dto.res.OAuth2TokenResponse;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GoogleOAuth2TokenResponse implements OAuth2TokenResponse {
       @JsonProperty("access_token")
       private String accessToken;

       @Override
       public String getAccessToken() {
              return accessToken;
       }
}
