package bssmchat.domain.user.domain.repository;

import bssmchat.domain.user.domain.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByName(String username);
    Optional<UserEntity> findByOauth2Id(String oAuth2Id);
}
