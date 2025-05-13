package bssmchat.domain.oauth2.presentation.dto.res.google;

import bssmchat.domain.oauth2.presentation.dto.res.OAuth2UserResponse;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GoogleOAuth2UserResponse implements OAuth2UserResponse {
        @JsonProperty("sub")
        private String id;

        @JsonProperty("name")
        private String name;

        @JsonProperty("email")
        private String email;

        @Override
        public String getId() {
                return id;
        }

        @Override
        public String getName() {
                return name;
        }

        @Override
        public String getEmail() {
                return email;
        }
}