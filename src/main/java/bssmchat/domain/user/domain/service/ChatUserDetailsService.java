package bssmchat.domain.user.domain.service;

import bssmchat.domain.user.domain.entity.UserEntity;
import bssmchat.domain.user.domain.repository.UserRepository;
import bssmchat.domain.user.presentation.dto.ChatUserDetails;
import bssmchat.global.exception.entity.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ChatUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public ChatUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByName(username)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        return new ChatUserDetails(userEntity);
    }
}
