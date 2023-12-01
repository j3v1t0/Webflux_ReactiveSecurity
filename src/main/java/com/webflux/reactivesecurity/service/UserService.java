package com.webflux.reactivesecurity.service;

import com.webflux.reactivesecurity.entity.UserEntity;
import reactor.core.publisher.Mono;

public interface UserService {

    Mono<UserEntity> registerUser(UserEntity user);

    Mono<UserEntity> getUserById(Long id);

    Mono<UserEntity> getUserByUsername(String username);
}
