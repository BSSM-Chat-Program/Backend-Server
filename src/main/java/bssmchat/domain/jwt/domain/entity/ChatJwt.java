package bssmchat.domain.jwt.domain.entity;

import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

@RedisHash(value = "refreshToken")
@Builder
@Getter
public class ChatJwt {
    @Id
    private String refreshToken;

    @Column(unique = true, nullable = false)
    private String userId;

    @TimeToLive
    private Long timeToLive;
}
