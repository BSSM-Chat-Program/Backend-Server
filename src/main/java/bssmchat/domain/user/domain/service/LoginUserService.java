package bssmchat.domain.user.domain.service;

import bssmchat.domain.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginUserService {
    private final UserRepository userRepository;
}
