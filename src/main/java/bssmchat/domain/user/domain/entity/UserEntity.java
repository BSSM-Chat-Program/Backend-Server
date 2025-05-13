package bssmchat.domain.user.domain.entity;

import bssmchat.domain.authority.domain.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Builder;
import lombok.Getter;
import org.springframework.transaction.annotation.Transactional;

@Entity
@Table(name = "user_entity")
@Getter
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String oauth2Id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Email
    private String email;

    @Column(nullable = false)
    private Role role;

    @Builder
    public UserEntity(String oauth2Id, String name, String email, Role role) {
        this.oauth2Id = oauth2Id;
        this.name = name;
        this.email = email;
        this.role = role;
    }

    @Transactional
    public void updateUser(String name, String email) {
        this.email = email;
        this.name = name;
    }

    public UserEntity() {}
}
