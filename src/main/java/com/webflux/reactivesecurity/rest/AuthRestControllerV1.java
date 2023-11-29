package com.webflux.reactivesecurity.rest;

import com.webflux.reactivesecurity.dto.AuthRequestDto;
import com.webflux.reactivesecurity.dto.AuthResponseDto;
import com.webflux.reactivesecurity.dto.UserDto;
import com.webflux.reactivesecurity.entity.UserEntity;
import com.webflux.reactivesecurity.mapper.UserMapper;
import com.webflux.reactivesecurity.repository.UserRepository;
import com.webflux.reactivesecurity.security.CustomPrincipal;
import com.webflux.reactivesecurity.security.SecurityService;
import com.webflux.reactivesecurity.service.UserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthRestControllerV1 {
    private final SecurityService securityService;
    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping("/register")
    public Mono<UserDto> register(@RequestBody UserDto userDto){
        UserEntity userEntity = userMapper.map(userDto);
        return userService.registerUser(userEntity)
                .map(userMapper::map);
    }

    @PostMapping("/login")
    public Mono<AuthResponseDto> login(@RequestBody AuthRequestDto authRequestDto){
        return securityService.authenticate(authRequestDto.getUsername(), authRequestDto.getPassword())
                .flatMap(tokenDetails -> Mono.just(
                        AuthResponseDto.builder()
                                .userId(tokenDetails.getUserId())
                                .token(tokenDetails.getToken())
                                .issuedAt(tokenDetails.getIssuedAt())
                                .expiresAt(tokenDetails.getExpiresAt())
                                .build()
                ));
    }

    @GetMapping("/info")
    public Mono<UserDto> getUserInfo(Authentication authentication){
        CustomPrincipal customPrincipal = (CustomPrincipal) authentication.getPrincipal();

        return userService.getUserById(customPrincipal.getId())
                .map(userMapper::map);
    }
}
