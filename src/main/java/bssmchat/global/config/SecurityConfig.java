package bssmchat.global.config;

import bssmchat.domain.jwt.infra.filter.ChatAuthenticationFilter;
import bssmchat.domain.user.domain.service.ChatUserDetailsService;
import bssmchat.global.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;

@Configuration
@EnableWebSecurity(debug = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final List<String> excludedUrls = List.of("/reissue", "/oauth2/link/**", "/oauth2/login/**", "/static/**", "/resources/**", "/public/**");
    private final JwtUtil jwtUtil;
    private final ObjectMapper objectMapper;
    private final ChatUserDetailsService chatUserDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(excludedUrls.toArray(new String[0])).permitAll()
                        .anyRequest().authenticated()
                )
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(new ChatAuthenticationFilter(jwtUtil, excludedUrls, chatUserDetailsService, objectMapper), UsernamePasswordAuthenticationFilter.class);
                //.addFilterBefore(new ChatLogoutFilter(), LogoutFilter.class);

        return http.build();
    }
}
