package com.webflux.reactivesecurity.service.impl;

import com.webflux.reactivesecurity.entity.UserEntity;
import com.webflux.reactivesecurity.entity.UserRole;
import com.webflux.reactivesecurity.repository.UserRepository;
import com.webflux.reactivesecurity.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public Mono<UserEntity> registerUser(UserEntity userEntity){
        return userRepository.save(
                userEntity.toBuilder()
                        .password(passwordEncoder.encode(userEntity.getPassword()))
                        .role(UserRole.USER)
                        .enabled(true)
                        .createAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .build()
        ).doOnSuccess(u -> {
            log.info("IN registerUser - user: {} created", u);
        });
    }

    @Override
    public Mono<UserEntity> getUserById(Long id){
        return userRepository.findById(id);
    }
    @Override
    public Mono<UserEntity> getUserByUsername(String username){
        return userRepository.findByUsername(username);
    }
}
