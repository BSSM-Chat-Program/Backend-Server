package bssmchat.domain.jwt.infra.filter;

import bssmchat.domain.jwt.infra.exception.InvalidJsonWebTokenException;
import bssmchat.domain.user.domain.service.ChatUserDetailsService;
import bssmchat.domain.user.presentation.dto.ChatUserDetails;
import bssmchat.global.exception.ErrorResponse;
import bssmchat.global.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.JwtException;
import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
public class ChatAuthenticationFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final List<String> excludedUrls;
    private final ChatUserDetailsService chatUserDetailsService;
    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(@Nonnull HttpServletRequest request, @Nonnull HttpServletResponse response, @Nonnull FilterChain filterChain) throws ServletException, IOException {
        try {
            System.out.println("서블릿 패스 : " + request.getServletPath());

            String accessToken = jwtUtil.getAccessToken(request);

            String username = jwtUtil.getUsername(accessToken);

            ChatUserDetails chatUserDetails = chatUserDetailsService.loadUserByUsername(username);

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(chatUserDetails, null, chatUserDetails.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            filterChain.doFilter(request, response);
        }
        catch(JwtException e) {
            handleJwtException(response, e);
        }
        catch(InvalidJsonWebTokenException e) {
            handleInvalidJsonWebTokenException(response, e);
        }
    }

    private void handleJwtException(HttpServletResponse response, JwtException jwtException) throws IOException {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message(jwtException.getMessage())
                .build();
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }

    private void handleInvalidJsonWebTokenException(HttpServletResponse response, InvalidJsonWebTokenException invalidJsonWebTokenException) throws IOException {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message(invalidJsonWebTokenException.getMessage())
                .build();
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }

    @Override
    protected boolean shouldNotFilter(@NonNull HttpServletRequest request) {
        return excludedUrls.stream()
                .anyMatch(pattern ->
                        new AntPathMatcher().match(pattern, request.getServletPath()));
    }
}
