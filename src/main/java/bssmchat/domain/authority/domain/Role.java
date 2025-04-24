package bssmchat.domain.authority.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role {

    ANONYMOUS("ROLE_ANONYMOUS"),
    USER("ROLE_USER");

    private final String value;
}
