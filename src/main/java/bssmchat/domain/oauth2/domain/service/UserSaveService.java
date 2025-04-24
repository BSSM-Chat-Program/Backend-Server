package bssmchat.domain.oauth2.domain.service;

import bssmchat.domain.authority.domain.Role;
import bssmchat.domain.oauth2.presentation.dto.res.OAuth2UserResponse;
import bssmchat.domain.user.domain.entity.UserEntity;
import bssmchat.domain.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Component
public class UserSaveService {
    private final UserRepository userRepository;

    public void saveUser(String oAuth2Id, OAuth2UserResponse oAuth2UserResponse) {
        Optional<UserEntity> userEntity = userRepository.findByOauth2Id(oAuth2Id);
        if(userEntity.isEmpty()) {
            log.info("신규 유저가 가입했습니다.");
            userRepository.save(UserEntity.builder()
                    .oauth2Id(oAuth2Id)
                    .email(oAuth2UserResponse.getEmail())
                    .name(oAuth2UserResponse.getName())
                    .role(Role.USER)
                    .build());
        }
        else {
            UserEntity user = userEntity.get();
            user.updateUser(oAuth2UserResponse.getName(), oAuth2UserResponse.getEmail());
        }
    }

}
