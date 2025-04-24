package bssmchat.global.properties;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@ConfigurationProperties(prefix = "redis")
public class RedisProperties {
    private final int port;
    private final String host;
    private final String password;

    public RedisProperties(int port, String host, String password) {
        this.port = port;
        this.host = host;
        this.password = password;
    }
}
