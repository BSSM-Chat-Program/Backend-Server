package bssmchat.domain.oauth2.presentation.dto.res;

public record TokenResponse(
    String accessToken,
    String refreshTokenCookie
) {}
