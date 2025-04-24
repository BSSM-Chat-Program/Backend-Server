package bssmchat.global.util;

import bssmchat.domain.jwt.domain.entity.ChatJwt;
import bssmchat.domain.jwt.domain.repository.ChatJwtRepository;
import bssmchat.domain.jwt.infra.exception.InvalidJsonWebTokenException;
import bssmchat.global.properties.JwtProperties;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Cookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Date;

@Component
public class JwtUtil {

    private final SecretKey secretKey;
    private final JwtProperties jwtProperties;
    private final ChatJwtRepository chatJwtRepository;

    public JwtUtil(JwtProperties jwtProperties, ChatJwtRepository chatJwtRepository) {
        this.jwtProperties = jwtProperties;
        this.chatJwtRepository = chatJwtRepository;
        this.secretKey = new SecretKeySpec(
                jwtProperties.getSecret().getBytes(StandardCharsets.UTF_8),
                Jwts.SIG.HS256.key().build().getAlgorithm()
        );
    }

    public String getUsername(String token) throws JwtException {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("username", String.class);
    }

    public String createAccessToken(String username) {
        return createJwt(username, "access", jwtProperties.getAccessExpiration());
    }

    public String createRefreshToken(String username) {
        return createJwt(username, "refresh", jwtProperties.getRefreshExpiration());
    }

    public String getAccessToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return jwtVerify(authorizationHeader.substring(7), "access");
        }
        throw new InvalidJsonWebTokenException();
    }

    public ResponseCookie invalidRefreshCookie(String oAuth2Id) {
        chatJwtRepository.deleteById(oAuth2Id);
        return createRefreshCookie("", 0L);
    }

    public ResponseCookie createRefreshTokenCookie(String refreshToken, String oAuth2Id) {
        chatJwtRepository.save(ChatJwt.builder()
                        .refreshToken(refreshToken)
                        .userId(oAuth2Id)
                        .timeToLive(jwtProperties.getRefreshExpiration())
                .build());
        return createRefreshCookie(refreshToken, jwtProperties.getRefreshExpiration());
    }

    private ResponseCookie createRefreshCookie(String value, Long maxAge) {
        return ResponseCookie.from("refresh", value)
                .maxAge(maxAge)
                .path("/")
                .httpOnly(true)
                .secure(true)
                .sameSite("None")
                .build();
    }

    public String getRefreshToken(HttpServletRequest request) {
        if (request.getCookies() == null) {
            throw new InvalidJsonWebTokenException();
        }

        String refreshToken =  Arrays.stream(request.getCookies())
                .filter(cookie -> "refresh".equals(cookie.getName()))
                .map(Cookie::getValue)
                .findFirst()
                .orElse(null);

        return jwtVerify(refreshToken, "refresh");
    }

    private String createJwt(String username, String category, Long expiredMS) {
        return Jwts.builder()
                .claim("category", category)
                .claim("username", username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiredMS))
                .signWith(secretKey)
                .compact();
    }

    private String jwtVerify(String token, String category) {
        if(token == null) throw new InvalidJsonWebTokenException();
        try {
            String tokenType = Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("category", String.class);
            if(!tokenType.equals(category)) throw new InvalidJsonWebTokenException();
            return token;
        }
        catch (JwtException e) {
            throw new InvalidJsonWebTokenException();
        }
    }
}