package bssmchat.domain.jwt.domain.repository;

import bssmchat.domain.jwt.domain.entity.ChatJwt;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatJwtRepository extends CrudRepository<ChatJwt, String> {
}
