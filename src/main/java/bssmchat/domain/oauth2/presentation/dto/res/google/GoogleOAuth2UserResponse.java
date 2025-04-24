package bssmchat.domain.oauth2.presentation.dto.res.google;

import bssmchat.domain.oauth2.presentation.dto.res.OAuth2UserResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class GoogleOAuth2UserResponse extends OAuth2UserResponse {
        @JsonProperty("sub")
        private String id;

        @JsonProperty("name")
        private String name;

        @JsonProperty("email")
        private String email;

        @JsonProperty("picture")
        private String profile;
}